import { tarefaService } from "../api/tarefaService.js";
import { FiltrosTarefa } from "../components/FiltrosTarefa.js";
import { Navbar } from "../components/Navbar.js";
import { TarefaCard } from "../components/TarefaCard.js";
import { TarefaForm } from "../components/TarefaForm.js";

let tarefas = [];
let tarefaEmEdicao = null;

const selectors = {
  form: "#tarefaForm",
  list: "#tarefasList",
  alert: "#alert",
  count: "#tarefasCount",
  formTitle: "#formTitle",
  cancelButton: "[data-action='cancelar-edicao']",
};

const getFieldValue = (form, name) => new FormData(form).get(name);

const showAlert = (message, type = "success") => {
  const alert = document.querySelector(selectors.alert);
  alert.textContent = message;
  alert.className = `alert alert--${type}`;
  window.setTimeout(() => {
    alert.textContent = "";
    alert.className = "alert";
  }, 4500);
};

const getApiError = (error) => {
  const data = error?.response?.data;
  if (typeof data === "string") return data;
  if (data?.message) return data.message;
  return "Não foi possível completar a operação. Verifique se o back-end está rodando.";
};

const renderList = (items) => {
  const list = document.querySelector(selectors.list);
  const count = document.querySelector(selectors.count);

  count.textContent = `${items.length} ${items.length === 1 ? "tarefa" : "tarefas"}`;
  list.innerHTML =
    items.length > 0
      ? items.map(TarefaCard).join("")
      : `<div class="empty-state">Nenhuma tarefa encontrada.</div>`;
};

const loadTarefas = async () => {
  tarefas = await tarefaService.listar();
  renderList(tarefas);
};

const resetForm = () => {
  document.querySelector(selectors.form).reset();
  document.querySelector("#tarefaId").value = "";
  document.querySelector(selectors.formTitle).textContent = "Nova tarefa";
  document.querySelector(selectors.cancelButton).classList.add("hidden");
  tarefaEmEdicao = null;
};

const fillForm = (tarefa) => {
  tarefaEmEdicao = tarefa;
  document.querySelector("#tarefaId").value = tarefa.id;
  document.querySelector("#titulo").value = tarefa.titulo || "";
  document.querySelector("#statusTarefa").value = tarefa.status || "PENDENTE";
  document.querySelector("#prioridade").value = tarefa.prioridade || "MEDIA";
  document.querySelector("#categoria").value = tarefa.categoria || "OUTROS";
  document.querySelector("#prazo").value = tarefa.prazo || "";
  document.querySelector("#responsavel").value = tarefa.responsavel || "";
  document.querySelector("#descricao").value = tarefa.descricao || "";
  document.querySelector(selectors.formTitle).textContent = `Editar tarefa #${tarefa.id}`;
  document.querySelector(selectors.cancelButton).classList.remove("hidden");
  document.querySelector(selectors.form).scrollIntoView({ behavior: "smooth", block: "start" });
};

const handleSubmit = async (event) => {
  event.preventDefault();
  const form = event.currentTarget;
  const tarefa = {
    titulo: getFieldValue(form, "titulo"),
    statusTarefa: getFieldValue(form, "statusTarefa"),
    prioridade: getFieldValue(form, "prioridade"),
    categoria: getFieldValue(form, "categoria"),
    descricao: getFieldValue(form, "descricao"),
    prazo: getFieldValue(form, "prazo"),
    responsavel: getFieldValue(form, "responsavel"),
  };

  try {
    if (tarefaEmEdicao) {
      await tarefaService.atualizar(tarefaEmEdicao.id, tarefa);
      showAlert("Tarefa atualizada com sucesso.");
    } else {
      await tarefaService.criar(tarefa);
      showAlert("Tarefa criada com sucesso.");
    }

    resetForm();
    await loadTarefas();
  } catch (error) {
    showAlert(getApiError(error), "error");
  }
};

const handleListClick = async (event) => {
  const button = event.target.closest("button[data-action]");
  if (!button) return;

  const id = Number(button.dataset.id);
  const action = button.dataset.action;

  if (action === "editar") {
    const tarefa = tarefas.find((item) => item.id === id);
    if (tarefa) fillForm(tarefa);
    return;
  }

  if (action === "excluir") {
    const confirmar = window.confirm("Deseja excluir esta tarefa?");
    if (!confirmar) return;

    try {
      await tarefaService.excluir(id);
      showAlert("Tarefa excluída com sucesso.");
      await loadTarefas();
    } catch (error) {
      showAlert(getApiError(error), "error");
    }
  }
};

const applyFilters = async () => {
  const titulo = document.querySelector("#filtroTitulo").value.trim();
  const responsavel = document.querySelector("#filtroResponsavel").value.trim();
  const status = document.querySelector("#filtroStatus").value;
  const prioridade = document.querySelector("#filtroPrioridade").value;

  try {
    if (titulo) {
      renderList(await tarefaService.buscarPorTitulo(titulo));
      return;
    }

    if (responsavel) {
      renderList(await tarefaService.buscarPorResponsavel(responsavel));
      return;
    }

    if (status) {
      renderList(await tarefaService.filtrarPorStatus(status));
      return;
    }

    if (prioridade) {
      renderList(await tarefaService.filtrarPorPrioridade(prioridade));
      return;
    }

    await loadTarefas();
  } catch (error) {
    showAlert(getApiError(error), "error");
  }
};

const bindEvents = () => {
  document.querySelector(selectors.form).addEventListener("submit", handleSubmit);
  document.querySelector(selectors.list).addEventListener("click", handleListClick);
  document.querySelector(selectors.cancelButton).addEventListener("click", resetForm);

  ["#filtroTitulo", "#filtroResponsavel", "#filtroStatus", "#filtroPrioridade"].forEach((selector) => {
    document.querySelector(selector).addEventListener("input", applyFilters);
    document.querySelector(selector).addEventListener("change", applyFilters);
  });

  document.querySelector("[data-action='limpar-filtros']").addEventListener("click", async () => {
    document.querySelector("#filtroTitulo").value = "";
    document.querySelector("#filtroResponsavel").value = "";
    document.querySelector("#filtroStatus").value = "";
    document.querySelector("#filtroPrioridade").value = "";
    await loadTarefas();
  });
};

export const renderTarefasPage = async (root) => {
  root.innerHTML = `
    ${Navbar()}
    <main class="page-shell">
      <div id="alert" class="alert" role="status" aria-live="polite"></div>
      <div class="layout">
        <div class="layout__form">
          ${TarefaForm()}
        </div>
        <div class="layout__content">
          ${FiltrosTarefa()}
          <section class="tasks-section">
            <div class="section-title">
              <div>
                <span class="eyebrow">Tarefas</span>
                <h2>Lista cadastrada</h2>
              </div>
              <strong id="tarefasCount" class="count">0 tarefas</strong>
            </div>
            <div id="tarefasList" class="tasks-grid">
              <div class="empty-state">Carregando tarefas...</div>
            </div>
          </section>
        </div>
      </div>
    </main>
  `;

  bindEvents();

  try {
    await loadTarefas();
  } catch (error) {
    showAlert(getApiError(error), "error");
    renderList([]);
  }
};
