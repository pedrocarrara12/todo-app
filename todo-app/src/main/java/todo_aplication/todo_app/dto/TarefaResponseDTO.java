package todo_aplication.todo_app.dto;

import todo_aplication.todo_app.entidades.Tarefa;
import todo_aplication.todo_app.enums.Categoria;
import todo_aplication.todo_app.enums.Prioridade;
import todo_aplication.todo_app.enums.StatusTarefa;

import java.time.LocalDate;

public record TarefaResponseDTO(
        Long id,
        String titulo,
        String descricao,
        StatusTarefa status,
        Prioridade prioridade,
        Categoria categoria,
        LocalDate prazo,
        String responsavel
) {
    public TarefaResponseDTO(Tarefa tarefa) {
        this(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getStatus(),
                tarefa.getPrioridade(),
                tarefa.getCategoria(),
                tarefa.getPrazo(),
                tarefa.getResponsavel()
        );
    }


}
