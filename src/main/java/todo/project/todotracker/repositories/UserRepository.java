package todo.project.todotracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import todo.project.todotracker.models.users.Role;
import todo.project.todotracker.models.users.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    Optional<User> findByUsername(String username);

//    User findByUsername(String username);

    List<User> findByRole(Role role);
    Optional<User> findById(Long id);

}
