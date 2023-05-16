package todo.project.todotracker.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

@Entity
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private boolean isComplete;
    private User user;

    public Task(String title, boolean complete, User user) {
        this.title = title;
        this.isComplete = complete;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        this.isComplete = complete;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
