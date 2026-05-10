# Todo App Front-end

Front-end simples e responsivo para consumir a API Spring Boot de tarefas.

## Tecnologias

- HTML, CSS e JavaScript modular
- Fetch API
- Servidor Node.js nativo para servir arquivos estáticos e fazer proxy para o back-end

## Como executar

1. Inicie o back-end Spring Boot em `http://localhost:8080`.
2. Na pasta `todo-app-frontend`, execute:

```bash
npm run dev
```

Se o `npm` não estiver disponível, execute diretamente:

```bash
node server.js
```

3. Acesse `http://localhost:5173`.

## Contrato da API usado

Base interna do front:

```js
const API_URL = "/api/tarefas";
```

O servidor local redireciona `/api` para `http://localhost:8080`, evitando problema de CORS durante o desenvolvimento.

Endpoints consumidos:

- `GET /tarefas`
- `POST /tarefas`
- `PUT /tarefas/{id}`
- `DELETE /tarefas/{id}`
- `GET /tarefas/titulo/{titulo}`
- `GET /tarefas/responsavel/{responsavel}`
- `GET /tarefas/status/{status}`
- `GET /tarefas/prioridade/{prioridade}`

Payload enviado em criação e edição:

```json
{
  "titulo": "Estudar Spring Boot",
  "statusTarefa": "PENDENTE",
  "prioridade": "MEDIA",
  "categoria": "ESTUDOS",
  "descricao": "Revisar controllers e DTOs",
  "prazo": "2026-05-15",
  "responsavel": "Pedro"
}
```

## Observações sobre o back-end

- A entidade possui `dataCriacao` e `dataAtualizacao`, mas o `TarefaResponseDTO` atual não retorna esses campos.
- O endpoint `GET /tarefas/status/{status}` pode precisar de `@PathVariable("status") StatusTarefa statusTarefa` caso o Spring não consiga mapear o nome do parâmetro automaticamente.
- Se o front for servido sem o proxy local, adicione CORS no back-end para liberar `http://localhost:5173`.
