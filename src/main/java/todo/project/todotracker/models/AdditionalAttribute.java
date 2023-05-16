package todo.project.todotracker.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import todo.project.todotracker.utils.Type;

import java.util.List;

@Entity
@NoArgsConstructor
public class AdditionalAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated
    private Type type;

    private User user;

    @OneToMany(mappedBy = "attribute")
    private List<AttributeValue> values;

    public AdditionalAttribute(String name, Type type, User user) {
        this.name = name;
        this.type = type;
        this.user = user;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
