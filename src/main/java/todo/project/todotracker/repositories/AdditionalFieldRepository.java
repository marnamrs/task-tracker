package todo.project.todotracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import todo.project.todotracker.models.tasks.AdditionalField;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdditionalFieldRepository extends JpaRepository<AdditionalField, Long> {

    List<AdditionalField> findByUser(String username);

}
