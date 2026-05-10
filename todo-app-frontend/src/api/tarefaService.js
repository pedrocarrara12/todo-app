export const API_URL = "/api/tarefas";

export const STATUS_TAREFA = ["PENDENTE", "CANCELADA", "EM_ANDAMENTO", "CONCLUIDA"];
export const PRIORIDADES = ["BAIXA", "MEDIA", "ALTA", "MUITO_ALTA"];
export const CATEGORIAS = [
  "TRABALHO",
  "ESTUDOS",
  "PESSOAL",
  "SAUDE",
  "FINANCAS",
  "LAZER",
  "COMPRAS",
  "OUTROS",
];

const normalizeTarefaPayload = (tarefa) => ({
  titulo: tarefa.titulo?.trim(),
  statusTarefa: tarefa.statusTarefa,
  prioridade: tarefa.prioridade,
  categoria: tarefa.categoria,
  descricao: tarefa.descricao?.trim() || null,
  prazo: tarefa.prazo || null,
  responsavel: tarefa.responsavel?.trim(),
});

const request = async (path = "", options = {}) => {
  const response = await fetch(`${API_URL}${path}`, {
    headers: {
      "Content-Type": "application/json",
      ...options.headers,
    },
    ...options,
  });

  if (!response.ok) {
    const contentType = response.headers.get("content-type") || "";
    const errorBody = contentType.includes("application/json")
      ? await response.json()
      : await response.text();
    const error = new Error("Erro na comunicação com a API.");
    error.response = { data: errorBody, status: response.status };
    throw error;
  }

  if (response.status === 204) return null;
  return response.json();
};

export const tarefaService = {
  async listar() {
    return request();
  },

  async criar(tarefa) {
    return request("", {
      method: "POST",
      body: JSON.stringify(normalizeTarefaPayload(tarefa)),
    });
  },

  async atualizar(id, tarefa) {
    return request(`/${id}`, {
      method: "PUT",
      body: JSON.stringify(normalizeTarefaPayload(tarefa)),
    });
  },

  async excluir(id) {
    await request(`/${id}`, { method: "DELETE" });
  },

  async buscarPorTitulo(titulo) {
    return request(`/titulo/${encodeURIComponent(titulo)}`);
  },

  async buscarPorResponsavel(responsavel) {
    return request(`/responsavel/${encodeURIComponent(responsavel)}`);
  },

  async filtrarPorStatus(status) {
    return request(`/status/${encodeURIComponent(status)}`);
  },

  async filtrarPorPrioridade(prioridade) {
    return request(`/prioridade/${encodeURIComponent(prioridade)}`);
  },
};
