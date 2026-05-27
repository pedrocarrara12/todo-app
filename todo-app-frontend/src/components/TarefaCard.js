const statusClass = {
  PENDENTE: "badge--muted",
  CANCELADA: "badge--danger",
  EM_ANDAMENTO: "badge--info",
  CONCLUIDA: "badge--success",
};

const prioridadeClass = {
  BAIXA: "priority--low",
  MEDIA: "priority--medium",
  ALTA: "priority--high",
  MUITO_ALTA: "priority--urgent",
};

const escapeHtml = (value = "") =>
  String(value).replace(/[&<>"']/g, (char) => {
    const entities = {
      "&": "&amp;",
      "<": "&lt;",
      ">": "&gt;",
      '"': "&quot;",
      "'": "&#39;",
    };

    return entities[char];
  });

const formatText = (value) => value?.replaceAll("_", " ") || "Não informado";

const formatDate = (value) => {
  if (!value) return "Não informado";
  const [year, month, day] = value.split("-");
  return `${day}/${month}/${year}`;
};

const formatDateTime = (value) => {
  if (!value) return "Não informado";
  return new Intl.DateTimeFormat("pt-BR", {
    dateStyle: "short",
    timeStyle: "short",
  }).format(new Date(value));
};

export const TarefaCard = (tarefa) => `
  <article class="task-card" data-id="${tarefa.id}">
    <div class="task-card__header">
      <div>
        <span class="task-card__id">#${tarefa.id}</span>
        <h3>${escapeHtml(tarefa.titulo)}</h3>
      </div>
      <span class="badge ${statusClass[tarefa.status] || "badge--muted"}">${escapeHtml(formatText(tarefa.status))}</span>
    </div>

    <p class="task-card__description">${escapeHtml(tarefa.descricao || "Sem descrição cadastrada.")}</p>

    <dl class="task-card__details">
      <div>
        <dt>Prioridade</dt>
        <dd class="${prioridadeClass[tarefa.prioridade] || ""}">${escapeHtml(formatText(tarefa.prioridade))}</dd>
      </div>
      <div>
        <dt>Categoria</dt>
        <dd>${escapeHtml(formatText(tarefa.categoria))}</dd>
      </div>
      <div>
        <dt>Prazo</dt>
        <dd>${formatDate(tarefa.prazo)}</dd>
      </div>
      <div>
        <dt>Responsável</dt>
        <dd>${escapeHtml(tarefa.responsavel || "Não informado")}</dd>
      </div>
      <div>
        <dt>Criação</dt>
        <dd>${formatDateTime(tarefa.dataCriacao)}</dd>
      </div>
      <div>
        <dt>Atualização</dt>
        <dd>${formatDateTime(tarefa.dataAtualizacao)}</dd>
      </div>
    </dl>

    <div class="task-card__actions">
      <button class="button button--ghost" type="button" data-action="editar" data-id="${tarefa.id}">
        Editar
      </button>
      <button class="button button--danger" type="button" data-action="excluir" data-id="${tarefa.id}">
        Excluir
      </button>
    </div>
  </article>
`;
