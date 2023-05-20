package todo.project.todotracker.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import todo.project.todotracker.models.tasks.Task;
import todo.project.todotracker.models.tasks.TaskDTO;
import todo.project.todotracker.models.users.User;
import todo.project.todotracker.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;

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


    public void createTask(TaskDTO taskDTO, User user) {
        if(taskDTO.getTitle().length()>200 || taskDTO.getTitle().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task title length must be between 1 and 200 characters.");
        }
        log.info("Creating new task...");
        Boolean status = taskDTO.getIsComplete() == 1 ? true : false;
        Task task = new Task(taskDTO.getTitle(), status, user);
        taskRepository.save(task);
    }


}
