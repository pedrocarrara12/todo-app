package todo_aplication.todo_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import todo_aplication.todo_app.entidades.Tarefa;
import todo_aplication.todo_app.enums.Prioridade;
import todo_aplication.todo_app.enums.StatusTarefa;

import java.util.List;
@Repository
public interface TarefaRepository extends JpaRepository<Tarefa,Long> {

    List<Tarefa> findByStatus(StatusTarefa statusTarefa);
    List<Tarefa> findByTituloContainingIgnoreCase(String titulo);
    List<Tarefa> findByResponsavelContainingIgnoreCase(String responsavel);
    List<Tarefa> findByPrioridade(Prioridade prioridade);
}
