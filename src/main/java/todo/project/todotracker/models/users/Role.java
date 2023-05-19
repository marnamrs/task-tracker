package todo.project.todotracker.models.users;

import jakarta.persistence.*;
import lombok.*;
import todo.project.todotracker.utils.RoleType;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    /**
     * The primary key for the Role table
     * Ids are automatically generated as sequential integers
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * The name of the role (see enum: RoleType)
     */
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
}
