package todo.project.todotracker.models.tasks;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import todo.project.todotracker.models.users.User;

import java.time.LocalDateTime;
import java.time.ZoneId;


@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    /* The primary key for the Task table
     * Ids are automatically generated as sequential integers */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private boolean isComplete;
    @ManyToOne
    private User user;
    private String userCountry;
    private LocalDateTime lastEdit;


    /**
     * Constructor for Task object. Besides the required params, a Long: id and LocalDateTime: lastEdit properties will be added authomatically.
     * @param title title of the task
     * @param complete boolean indicating status of the task
     * @param user user assigned to the task
     * @see User
     * */
    public Task(String title, boolean complete, User user) {
        setTitle(title);
        setComplete(complete);
        setUser(user);
        userCountry = user.getAddress().getCountry();
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

    public String getUserCountry(){
        return user.getAddress().getCountry();
    }
}
