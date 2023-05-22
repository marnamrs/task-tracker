package todo.project.todotracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import todo.project.todotracker.models.tasks.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {
    List<Task> findAll();
    List<Task> findByTitleContainingIgnoreCase(String query);
    List<Task> findByUser(String username);
    List<Task> findByIsComplete(boolean isComplete);


}
