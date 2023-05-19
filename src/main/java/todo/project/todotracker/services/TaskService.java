package todo.project.todotracker.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todo.project.todotracker.models.tasks.Task;
import todo.project.todotracker.models.tasks.TaskDTO;
import todo.project.todotracker.models.users.User;
import todo.project.todotracker.repositories.TaskRepository;

import java.util.List;

@Service
@Slf4j
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    /**
     * Read Services
     */

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    };
    public List<Task> getTasksByUser(User user){
        return taskRepository.findByUser(user.getUsername());
    };
    public List <Task> getTasksByTitle(String query){
        return taskRepository.findByTitleContainingIgnoreCase(query);
    };

    /**
     * Write Services
     * */

    void addTask(TaskDTO taskDTO){
        //TODO add task method in service layer

    };


}
