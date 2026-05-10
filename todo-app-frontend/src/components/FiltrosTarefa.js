import { PRIORIDADES, STATUS_TAREFA } from "../api/tarefaService.js";

const optionList = (items, placeholder) => `
  <option value="">${placeholder}</option>
  ${items.map((item) => `<option value="${item}">${item.replaceAll("_", " ")}</option>`).join("")}
`;

export const FiltrosTarefa = () => `
  <section class="panel filters" aria-label="Filtros de tarefas">
    <div class="field">
      <label for="filtroTitulo">Título</label>
      <input id="filtroTitulo" name="titulo" type="search" placeholder="Buscar por título" />
    </div>
    <div class="field">
      <label for="filtroResponsavel">Responsável</label>
      <input id="filtroResponsavel" name="responsavel" type="search" placeholder="Buscar responsável" />
    </div>
    <div class="field">
      <label for="filtroStatus">Status</label>
      <select id="filtroStatus" name="status">
        ${optionList(STATUS_TAREFA, "Todos")}
      </select>
    </div>
    <div class="field">
      <label for="filtroPrioridade">Prioridade</label>
      <select id="filtroPrioridade" name="prioridade">
        ${optionList(PRIORIDADES, "Todas")}
      </select>
    </div>
    <div class="filters__actions">
      <button class="button button--ghost" type="button" data-action="limpar-filtros">Limpar</button>
    </div>
  </section>
`;
