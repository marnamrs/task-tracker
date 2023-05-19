package todo.project.todotracker.models.tasks;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import todo.project.todotracker.models.users.User;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    /**
     * The primary key for the Task table
     * Ids are automatically generated as sequential integers
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private boolean isComplete;
    @ManyToOne
    private User user;
    private LocalDateTime lastEdit;
    /**
     * Values for additional fields created by User
     * */
    @OneToMany(mappedBy = "task")
    private List<AdditionalValue> attributeValues;

    public Task(String title, boolean complete, User user) {
        setTitle(title);
        setComplete(complete);
        setUser(user);
        setLastEdit();
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

    public void setLastEdit(){
        //default: set according to CET timezone
        ZoneId zone = ZoneId.of("Europe/Madrid");
        lastEdit = LocalDateTime.now(zone);
    }
    public void setLastEdit(LocalDateTime date){
        //override: set given date
        this.lastEdit = date;
    }
}
