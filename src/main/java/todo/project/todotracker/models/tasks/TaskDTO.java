package todo.project.todotracker.models.tasks;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TaskDTO {

    @NotNull
    @NotEmpty(message = "Title cannot be empty.")
    @Size(max = 200, message = "Title cannot exceed 200 characters.")
    private String title;

    //if not status is provided, isComplete will set to false
    private boolean isComplete = false;

    private String username;


}
