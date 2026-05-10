package todo_aplication.todo_app.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import todo_aplication.todo_app.dto.TarefaDTO;
import todo_aplication.todo_app.dto.TarefaResponseDTO;
import todo_aplication.todo_app.enums.Prioridade;
import todo_aplication.todo_app.enums.StatusTarefa;
import todo_aplication.todo_app.service.TarefaService;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
@CrossOrigin(origins = "http://localhost:5173")
public class TarefaController {
    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    public List<TarefaResponseDTO> listarTarefas() {
        return tarefaService.buscarTarefas();
    }

    @PostMapping
    public ResponseEntity<TarefaResponseDTO> criarTarefa(@Valid @RequestBody TarefaDTO tarefaDTO) {
        TarefaResponseDTO tarefaResponseDTO = tarefaService.criarTarefa(tarefaDTO);
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
    public ResponseEntity<TarefaResponseDTO> atualizarTarefa(
            @PathVariable Long id,
            @Valid @RequestBody TarefaDTO tarefaDTO
    ) {
        TarefaResponseDTO tarefaResponseDTO = tarefaService.atualizarTarefa(id, tarefaDTO);
        return ResponseEntity.ok(tarefaResponseDTO);
    }

    @DeleteMapping("/todos")
    public ResponseEntity<Void> deletarTodasTarefas() {
        tarefaService.deletarTodasTarefas();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/prioridade/{prioridade}")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefaPorPrioridade(
            @PathVariable Prioridade prioridade
    ) {
        List<TarefaResponseDTO> tarefaResponseDTOList = tarefaService.buscarTarefasPorPrioridade(prioridade);
        return ResponseEntity.ok(tarefaResponseDTOList);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TarefaResponseDTO>> buscaTarefaPorStatus(
            @PathVariable("status") StatusTarefa statusTarefa
    ) {
        List<TarefaResponseDTO> tarefaResponseDTOList = tarefaService.buscarTarefasPorStatus(statusTarefa);
        return ResponseEntity.ok(tarefaResponseDTOList);
    }

    @GetMapping("/responsavel/{responsavel}")
    public ResponseEntity<List<TarefaResponseDTO>> buscaTarefaPorResponsavel(
            @PathVariable String responsavel
    ) {
        List<TarefaResponseDTO> tarefaResponseDTOList =
                tarefaService.buscarTarefasPorResponsavel(responsavel);

        return ResponseEntity.ok(tarefaResponseDTOList);
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<TarefaResponseDTO>> buscaTarefaPorTitulo(
            @PathVariable String titulo
    ) {
        List<TarefaResponseDTO> tarefaResponseDTOList =
                tarefaService.buscarTarefasPorTitulo(titulo);

        return ResponseEntity.ok(tarefaResponseDTOList);
    }
}
