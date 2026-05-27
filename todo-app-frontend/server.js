import { createServer } from "node:http";
import { readFile } from "node:fs/promises";
import { extname, join, normalize } from "node:path";
import { fileURLToPath } from "node:url";

const PORT = Number(process.env.PORT || 5173);
const BACKEND_URL = process.env.BACKEND_URL || "http://localhost:8080";
const ROOT_DIR = fileURLToPath(new URL(".", import.meta.url));

const mimeTypes = {
  ".html": "text/html; charset=utf-8",
  ".js": "text/javascript; charset=utf-8",
  ".css": "text/css; charset=utf-8",
  ".json": "application/json; charset=utf-8",
  ".svg": "image/svg+xml",
  ".png": "image/png",
  ".jpg": "image/jpeg",
  ".jpeg": "image/jpeg",
};

const send = (response, statusCode, body, headers = {}) => {
  response.writeHead(statusCode, headers);
  response.end(body);
};

const proxyApiRequest = async (request, response) => {
  const targetUrl = new URL(request.url.replace(/^\/api/, ""), BACKEND_URL);
  const body = ["POST", "PUT", "PATCH"].includes(request.method)
    ? await new Promise((resolve) => {
        const chunks = [];
        request.on("data", (chunk) => chunks.push(chunk));
        request.on("end", () => resolve(Buffer.concat(chunks)));
      })
    : undefined;

  try {
    const apiResponse = await fetch(targetUrl, {
      method: request.method,
      headers: {
        "Content-Type": request.headers["content-type"] || "application/json",
      },
      body,
    });

    const payload = Buffer.from(await apiResponse.arrayBuffer());
    send(response, apiResponse.status, payload, {
      "Content-Type": apiResponse.headers.get("content-type") || "application/json; charset=utf-8",
    });
  } catch {
    send(
      response,
      502,
      JSON.stringify({ message: `Não foi possível conectar ao back-end em ${BACKEND_URL}.` }),
      { "Content-Type": "application/json; charset=utf-8" },
    );
  }
};

const serveStaticFile = async (request, response) => {
  const url = new URL(request.url, `http://localhost:${PORT}`);
  const requestedPath = url.pathname === "/" ? "/index.html" : url.pathname;
  const filePath = normalize(join(ROOT_DIR, requestedPath));

  if (!filePath.startsWith(ROOT_DIR)) {
    send(response, 403, "Acesso negado.");
    return;
  }

  try {
    const file = await readFile(filePath);
    send(response, 200, file, {
      "Content-Type": mimeTypes[extname(filePath)] || "application/octet-stream",
    });
  } catch {
    const index = await readFile(join(ROOT_DIR, "index.html"));
    send(response, 200, index, { "Content-Type": mimeTypes[".html"] });
  }
};

createServer((request, response) => {
  if (request.url.startsWith("/api")) {
    proxyApiRequest(request, response);
    return;
  }

  serveStaticFile(request, response);
}).listen(PORT, () => {
  console.log(`Front-end disponível em http://localhost:${PORT}`);
  console.log(`Proxy da API apontando para ${BACKEND_URL}`);
});
