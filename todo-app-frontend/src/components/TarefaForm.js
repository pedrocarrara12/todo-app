import { CATEGORIAS, PRIORIDADES, STATUS_TAREFA } from "../api/tarefaService.js";

const options = (items) =>
  items.map((item) => `<option value="${item}">${item.replaceAll("_", " ")}</option>`).join("");

export const TarefaForm = () => `
  <section class="panel form-panel" aria-label="Formulário de tarefa">
    <div class="section-title">
      <div>
        <span class="eyebrow">Cadastro</span>
        <h2 id="formTitle">Nova tarefa</h2>
      </div>
      <button class="button button--ghost hidden" type="button" data-action="cancelar-edicao">
        Cancelar edição
      </button>
    </div>

    <form id="tarefaForm" class="task-form">
      <input type="hidden" id="tarefaId" name="id" />

      <div class="field field--wide">
        <label for="titulo">Título</label>
        <input id="titulo" name="titulo" type="text" minlength="3" maxlength="100" required />
      </div>

      <div class="field">
        <label for="statusTarefa">Status</label>
        <select id="statusTarefa" name="statusTarefa" required>
          ${options(STATUS_TAREFA)}
        </select>
      </div>

      <div class="field">
        <label for="prioridade">Prioridade</label>
        <select id="prioridade" name="prioridade" required>
          ${options(PRIORIDADES)}
        </select>
      </div>

      <div class="field">
        <label for="categoria">Categoria</label>
        <select id="categoria" name="categoria" required>
          ${options(CATEGORIAS)}
        </select>
      </div>

      <div class="field">
        <label for="prazo">Prazo</label>
        <input id="prazo" name="prazo" type="date" />
      </div>

      <div class="field field--wide">
        <label for="responsavel">Responsável</label>
        <input id="responsavel" name="responsavel" type="text" required />
      </div>

      <div class="field field--wide">
        <label for="descricao">Descrição</label>
        <textarea id="descricao" name="descricao" maxlength="1000" rows="4"></textarea>
      </div>

      <button class="button button--primary" type="submit">Salvar tarefa</button>
    </form>
  </section>
`;
