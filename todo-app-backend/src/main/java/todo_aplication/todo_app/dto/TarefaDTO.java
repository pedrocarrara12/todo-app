package todo_aplication.todo_app.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import todo_aplication.todo_app.enums.Categoria;
import todo_aplication.todo_app.enums.Prioridade;
import todo_aplication.todo_app.enums.StatusTarefa;

import java.time.LocalDate;

public record TarefaDTO(

        @NotBlank(message = "O titulo e obrigatorio")
        @Size(min = 3, max = 100)
        String titulo,

        @NotNull(message = "O status e obrigatorio")
        StatusTarefa statusTarefa,

        @NotNull(message = "A prioridade e obrigatoria")
        Prioridade prioridade,

        @NotNull(message = "A categoria e obrigatoria")
        Categoria categoria,

        @Size(max = 1000)
        String descricao,

        @FutureOrPresent(message = "O prazo nao pode ser uma data passada")
        LocalDate prazo,

        @NotBlank(message = "O responsavel e obrigatorio")
        String responsavel
) {
}
