package todo.project.todotracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import todo.project.todotracker.models.tasks.AdditionalValue;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdditionalValueRepository extends JpaRepository<AdditionalValue, Long> {

    Optional<AdditionalValue> findByTaskAndField(Long taskId, Long fieldId);

}
