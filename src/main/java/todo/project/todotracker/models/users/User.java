package todo.project.todotracker.models.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import todo.project.todotracker.models.tasks.AdditionalField;
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
/*    *//*
     * A User can only be assigned a single Role.
     * *//*
    @OneToOne
    private Role role;*/
    /*
     * Additional fields/columns created by the User
     * */
    @OneToMany(mappedBy = "user")
    private List<AdditionalField> additionalFields;

    public User(String name, String username, String password, Address address) {
        setName(name);
        setUsername(username);
        setPassword(password);
        setAddress(address);
        //setRole(role);
    }
}
