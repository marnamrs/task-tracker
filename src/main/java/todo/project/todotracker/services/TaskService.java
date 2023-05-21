package todo.project.todotracker.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import todo.project.todotracker.models.tasks.Task;
import todo.project.todotracker.models.tasks.TaskDTO;
import todo.project.todotracker.models.users.User;
import todo.project.todotracker.repositories.TaskRepository;

import java.util.Collections;
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

    public Page<Task> getAllTasks(Pageable pageable){
        //Pageable paging = PageRequest.of(page, 10);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int start = currentPage * pageSize;
        List<Task> tasks = taskRepository.findAll();
        List<Task> list = null;
        if(tasks.size() < start){
            list = Collections.emptyList();
        } else {
            int end = Math.min(start + pageSize, tasks.size());
            list = tasks.subList(start, end);
        }
        Page<Task> taskPage = new PageImpl<Task>(list, PageRequest.of(currentPage, pageSize), tasks.size());
        return taskPage;
    };
    public List<Task> getTasksByUser(User user){
        return taskRepository.findByUser(user.getUsername());
    };
    public List <Task> getTasksByTitle(String query){
        return taskRepository.findByTitleContainingIgnoreCase(query);
    };

    public Task getTaskById(int taskId) {
        Optional<Task> task = taskRepository.findById(Long.valueOf(taskId));
        if(task.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found.");
        }
        return task.get();
    }

    /**
     * Write Services
     * */

    public void createTask(TaskDTO taskDTO, User user) {
        if(taskDTO.getTitle().length()>200 || taskDTO.getTitle().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task title length must be between 1 and 200 characters.");
        }
        log.info("Creating new task...");
        Boolean status = taskDTO.getIsComplete() == 1;
        Task task = new Task(taskDTO.getTitle(), status, user);
        taskRepository.save(task);
    }

    public void updateTask(Task task, TaskDTO taskDTO, User user) {
        if(taskDTO.getTitle().length()>200 || taskDTO.getTitle().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task title length must be between 1 and 200 characters.");
        }
        log.info("Updating task...");
        boolean status = taskDTO.getIsComplete() == 1 ? true : false;
        task.setTitle(taskDTO.getTitle());
        task.setUser(user);
        task.setComplete(status);
        taskRepository.save(task);
    }

    public void deleteTask(int id){
        Optional<Task> task = taskRepository.findById(Long.valueOf(id));
        if(task.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
        log.info("Deleting task...");
        taskRepository.delete(task.get());
    }

}
