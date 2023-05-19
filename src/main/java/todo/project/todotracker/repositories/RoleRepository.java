package todo.project.todotracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import todo.project.todotracker.models.users.Role;
import todo.project.todotracker.utils.RoleType;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    //Optional<Role> findByRoleType(String type);
    Optional<Role> findByRoleType(RoleType type);

}
