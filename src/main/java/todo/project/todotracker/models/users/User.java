package todo.project.todotracker.models.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import todo.project.todotracker.models.tasks.Task;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    /*
     * The primary key for the User table
     * Ids are automatically generated as sequential integers
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    public User(String name, String username, String password, Address address) {
        setName(name);
        setUsername(username);
        setPassword(password);
        setAddress(address);
    }
}
