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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        tarefaService.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> buscarTarefaPorId(@PathVariable Long id) {
        TarefaResponseDTO tarefaResponseDTO = tarefaService.buscarTarefaPorId(id);
        return ResponseEntity.ok(tarefaResponseDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> atualizarTarefa (@PathVariable Long id,
                                                              @RequestBody TarefaDTO tarefaDTO){
       TarefaResponseDTO tarefaResponseDTO = tarefaService.atualizarTarefa(id,tarefaDTO);
        return ResponseEntity.ok(tarefaResponseDTO);
    }
    @DeleteMapping("/todos")
    public ResponseEntity<Void> deletarTodasTarefas() {
        tarefaService.deletarTodasTarefas();
        return ResponseEntity.noContent().build();
    }






}
