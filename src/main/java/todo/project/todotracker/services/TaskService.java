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
import todo.project.todotracker.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaskService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    /**
     * Read Services
     */

    public Long getTotalTasks(){
        log.info("Obtaining number of Tasks in database...");
        return taskRepository.count();
    }
    public Page<Task> getAllTasks(Pageable pageable){
        //Pageable paging = PageRequest.of(page, 10);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int start = currentPage * pageSize;
        List<Task> tasks = taskRepository.findAll();
        List<Task> list;
        if(tasks.size() < start){
            list = Collections.emptyList();
        } else {
            int end = Math.min(start + pageSize, tasks.size());
            list = tasks.subList(start, end);
        }
        Page<Task> taskPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), tasks.size());
        return taskPage;
    }
    public List<Task> getTasksByUser(User user){
        return taskRepository.findByUser(user);
    }
    public List <Task> getTasksByTitle(String query){
        return taskRepository.findByTitleContainingIgnoreCase(query);
    }
    public Task getTaskById(int taskId) {
        Optional<Task> task = taskRepository.findById((long) taskId);
        if(task.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found.");
        }
        return task.get();
    }
    public Page<Task> getPaginated(final int pageNum, final String sortField, final String sortDirection){
        log.info("Fetching paginated results from database...");
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                    Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNum - 1, 10, sort);
        return taskRepository.findAll(pageable);
    }
    public List<Task> getSearch(String field, String query){
        log.info("Fetching search results from database...");
        System.out.println("*** field: " + field + "*** query: " + query);
        switch (field){
            case "title":
                return taskRepository.findByTitleContainingIgnoreCase(query);
            case "user":
                Optional<User> user = userRepository.findByUsername(query);
                if(user.isPresent()){
                    return taskRepository.findByUser(user.get());
                };
            default:
                return taskRepository.findAll();
        }
    }

    /**
     * Write Services
     * */

    public void createTask(TaskDTO taskDTO, User user) {
        if(taskDTO.getTitle().length()>200 || taskDTO.getTitle().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task title length must be between 1 and 200 characters.");
        }
        log.info("Creating new task...");
        boolean status = taskDTO.getIsComplete() == 1;
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
        task.setLastEdit();
        taskRepository.save(task);
    }

    public void deleteTask(int id){
        Optional<Task> task = taskRepository.findById((long) id);
        if(task.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
        log.info("Deleting task...");
        taskRepository.delete(task.get());
    }

}
