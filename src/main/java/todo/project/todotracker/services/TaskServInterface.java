package todo.project.todotracker.services;

import todo.project.todotracker.models.tasks.Task;
import todo.project.todotracker.models.users.User;

import java.util.List;

public interface TaskServInterface {
    /**
     * Read Services
     */

    List<Task> getAllTasks();
    List<Task> getTasksByUser(User user);
    List <Task> getTasksByTitle(String query);

    /**
     * Write Services
     * */

    void addTask(TaskDTO taskDTO);


}
