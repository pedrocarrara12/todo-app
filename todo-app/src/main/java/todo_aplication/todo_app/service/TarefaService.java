package todo_aplication.todo_app.service;

import org.springframework.stereotype.Service;
import todo_aplication.todo_app.dto.TarefaResponseDTO;
import todo_aplication.todo_app.entidades.Tarefa;
import todo_aplication.todo_app.dto.TarefaDTO;
import todo_aplication.todo_app.enums.Prioridade;
import todo_aplication.todo_app.enums.StatusTarefa;
import todo_aplication.todo_app.exceptions.ResponsavelException;
import todo_aplication.todo_app.exceptions.TarefaNaoEncontradaException;
import todo_aplication.todo_app.repository.TarefaRepository;

import java.util.List;
@Service
public class TarefaService {



    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public TarefaResponseDTO criarTarefa(TarefaDTO tarefaDTO) {
        validaDTO(tarefaDTO);
            Tarefa tarefa = new Tarefa(tarefaDTO.titulo(),tarefaDTO.statusTarefa(),tarefaDTO.prioridade(),
                    tarefaDTO.categoria(),tarefaDTO.descricao(),tarefaDTO.prazo(),tarefaDTO.responsavel());
         Tarefa tarefaSalva = tarefaRepository.save(tarefa);
         return new TarefaResponseDTO(tarefaSalva);


    }


    private boolean validaDTO(TarefaDTO tarefaDTO) {
        if (tarefaDTO.responsavel() == null || tarefaDTO.responsavel().isBlank()) {
            throw new ResponsavelException("Erro, o responsavel não pode ser nulo ou vazio");

        }
        return true;
    }
    public void deletarTarefa(Long id) {
        Tarefa tarefa = buscarTarefaEntidadePorId(id);
        tarefaRepository.deleteById(id);
    }
    private Tarefa buscarTarefaEntidadePorId(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaNaoEncontradaException("A tarefa não foi encontrada"));
    }
    public TarefaResponseDTO buscarTarefaPorId(Long id) {
        Tarefa tarefa = buscarTarefaEntidadePorId(id);
        return new TarefaResponseDTO(tarefa);
    }
    public TarefaResponseDTO atualizarTarefa(Long id, TarefaDTO tarefaDTO) {
       Tarefa tarefa = buscarTarefaEntidadePorId(id);
       tarefa.setTitulo(tarefaDTO.titulo());
       tarefa.setStatus(tarefaDTO.statusTarefa());
       tarefa.setPrioridade(tarefaDTO.prioridade());
       tarefa.setCategoria(tarefaDTO.categoria());
       tarefa.setDescricao(tarefaDTO.descricao());
       tarefa.setPrazo(tarefaDTO.prazo());
       tarefa.setResponsavel(tarefaDTO.responsavel());
       tarefaRepository.save(tarefa);
       TarefaResponseDTO tarefaResponseDTO = new TarefaResponseDTO(tarefa);
        return tarefaResponseDTO;
    }
    public List<Tarefa> buscarTarefas() {
        return tarefaRepository.findAll();
    }
    public void deletarTodasTarefas() {
        tarefaRepository.deleteAll();
    }
    public List<Tarefa> buscarTarefasPorPrioridade(Prioridade prioridade) {
        if (prioridade == null) {
            throw new IllegalArgumentException("A prioridade é obrigatória");
        }

        return tarefaRepository.findByPrioridade(prioridade);
    }
    public List<Tarefa> buscarTarefasPorStatus(StatusTarefa statusTarefa) {
        if (statusTarefa == null) {
            throw new IllegalArgumentException("O status da tarefa é obrigatório");
        }

        return tarefaRepository.findByStatus(statusTarefa);
    }
    public List<Tarefa> buscarTarefasPorResponsavel(String responsavel) {
        if (responsavel == null || responsavel.isBlank()) {
            throw new IllegalArgumentException("O responsável é obrigatório");
        }

        return tarefaRepository.findByResponsavelContainingIgnoreCase(responsavel);
    }
    public List<Tarefa> buscarTarefasPorTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título da tarefa é obrigatório");
        }

        return tarefaRepository.findByTituloContainingIgnoreCase(titulo);
    }




    }