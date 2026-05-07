package todo_aplication.todo_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import todo_aplication.todo_app.dto.TarefaDTO;
import todo_aplication.todo_app.dto.TarefaResponseDTO;
import todo_aplication.todo_app.entidades.Tarefa;
import todo_aplication.todo_app.service.TarefaService;

import java.util.List;

@RestController
@RequestMapping("/tarefas")

public class TarefaController {
    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    public List<TarefaResponseDTO> listarTarefas() {
        List<Tarefa> tarefaEntity = tarefaService.buscarTarefas();
         List<TarefaResponseDTO> tarefaResponseDTOList = tarefaEntity.stream().map(
                TarefaResponseDTO::new).toList();
         return tarefaResponseDTOList;
    }
    @PostMapping
    public ResponseEntity<TarefaResponseDTO> criarTarefa(@RequestBody TarefaDTO tarefaDTO) {
       TarefaResponseDTO tarefaResponseDTO  = tarefaService.criarTarefa(tarefaDTO);
       return ResponseEntity.status(HttpStatus.CREATED).body(tarefaResponseDTO);
    }




}
