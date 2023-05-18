package todo.project.todotracker.models.tasks;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import todo.project.todotracker.models.users.User;
import todo.project.todotracker.utils.DataType;

import java.util.List;

/**
 * AdditionalAttribute:
 * Additional fields/columns created by Users
 * */

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalAttribute {
    /**
     * The primary key for the AdditionalAttribute table
     * Ids are automatically generated as sequential integers
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated
    private DataType type;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "attribute")
    private List<AttributeValue> values;

    public AdditionalAttribute(String name, DataType type, User user) {
        setName(name);
        setType(type);
        setUser(user);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
