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

        @NotBlank(message = "O título é obrigatório")
        @Size(min = 3, max = 100)
        String titulo,

        StatusTarefa statusTarefa,

        @NotNull(message = "A prioridade é obrigatória")
        Prioridade prioridade,

        @NotNull(message = "A categoria é obrigatória")
        Categoria categoria,

        @Size(max = 1000)
        String descricao,

        @FutureOrPresent(message = "O prazo não pode ser uma data passada")
        LocalDate prazo,

        String responsavel
) {}