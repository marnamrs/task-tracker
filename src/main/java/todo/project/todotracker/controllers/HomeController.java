package todo.project.todotracker.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todo.project.todotracker.models.tasks.Task;
import todo.project.todotracker.models.tasks.TaskDTO;
import todo.project.todotracker.models.users.User;
import todo.project.todotracker.repositories.TaskRepository;
import todo.project.todotracker.repositories.UserRepository;
import todo.project.todotracker.services.TaskService;
import todo.project.todotracker.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class HomeController {

    @Autowired
    TaskService taskService;
    @Autowired
    UserService userService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;


    /** landingController takes the current page as an Optional request parameter to allow pagination of the task list results, injects the appropiate task page in the thymeleaf template, as well as a list of all available pages for navigation purposes.
     * @param model allows injection of content
     * @param page int current page (default=1)
     * @return index.html
     */
    @RequestMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String landingController(
            Model model,
            @RequestParam("page") Optional<Integer> page){
        int currentPage = page.orElse(1);
        Page<Task> taskPage = taskService.getAllTasks(PageRequest.of(currentPage - 1, 10));
        //model.addAttribute("allTasks", taskService.getAllTasks(currentPage));
        model.addAttribute("allTasks", taskPage);

        int numPages = taskPage.getTotalPages();
        if(numPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, numPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "index";
    }

    /** newTaskController passes an empty task DTO and a list of all users the newtask.html view, which contains a form with the fields 'title', 'user' and 'status' necessary to fill the DTO properties.
     * @param model Model allows injection of content (task DTO, list of Users)
     * @return newtask.html
     */
    @RequestMapping("/newtask")
    @ResponseStatus(HttpStatus.OK)
    public String newTaskController(Model model){
        model.addAttribute("taskDTO", new TaskDTO());
        model.addAttribute("allUsers", userService.getAllUsers());
        return "newtask";
    }

    /** updateTaskController takes the id of the task to be Edited and retrieves the Task from the DB, generates a DTO populated with the Task properties and passes it to the thymelief form to prefill the fields, together with the task id.
     * @param model Model allows injection of content (task DTO, list of Users, edited Task id)
     * @param id unique identifier of the edited Task
     * @return updatetask.html
     */
    @RequestMapping("/editTask")
    @ResponseStatus(HttpStatus.OK)
    public String updateTaskController(Model model, @RequestParam("task") int id){
        Task task = taskService.getTaskById(id);
        int status = task.isComplete() == true ? 1 : 0;
        model.addAttribute("taskDTO", new TaskDTO(task.getTitle(), status, Math.toIntExact(task.getUser().getId())));
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("id", id);
        return "updatetask";
    }

    /** postNewTaskController takes the DTO task object generated in /newtask and tries to retrieve the User, create and save a new Task. If successful, it returns a redirect to the landing page. Failure to create the Task or redirect will reload /new task with an error message.
     * @param taskDTO task DTO with properties obtained through thymeleaf form binding in /newtask page
     * @param model required to display Exception messages, if any
     * @param httpResponse manages the redirect
     * @return newtask.html (error) or index.html (success)
     */
    @PostMapping("/savetask")
    @ResponseStatus(HttpStatus.CREATED)
    public String postNewTaskController(@ModelAttribute("taskDTO") TaskDTO taskDTO, Model model, HttpServletResponse httpResponse){
        try{
        User user = userService.getUserById((long) taskDTO.getUserId());
        taskService.createTask(taskDTO, user);
        } catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "newtask";
        }
        try {
            httpResponse.sendRedirect("/");
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
            return "newtask";
        }
        return null;
    }

    @PostMapping("/updatetask")
    @ResponseStatus(HttpStatus.CREATED)
    public String postUpdateController(@ModelAttribute("task") TaskDTO taskDTO, @RequestParam("id") int id, Model model, HttpServletResponse httpResponse){
        try{
            Task task = taskService.getTaskById(id);
            User user = userService.getUserById(Long.valueOf(taskDTO.getUserId()));
            taskService.updateTask(task, taskDTO, user);
        } catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "index";
        }
        try {
            httpResponse.sendRedirect("/");
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
            return "index";
        }
        return null;
    }


}
