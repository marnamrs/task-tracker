package todo.project.todotracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import todo.project.todotracker.models.tasks.AdditionalValue;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdditionalValueRepository extends JpaRepository<AdditionalValue, Long> {

    /** Find the value of a Task for an additional/optional field
     * @param taskId id of the Task the value belongs to
     * @param fieldId id of the AdditionalField the value belongs to
     * @return Optional<AdditionalValue> matching the given task and field ids, if such value exists
     * @see todo.project.todotracker.models.tasks.AdditionalField
     */
    Optional<AdditionalValue> findByTaskAndField(Long taskId, Long fieldId);

}
