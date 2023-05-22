package todo.project.todotracker.controllers;



import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import todo.project.todotracker.models.tasks.SearchDTO;
import todo.project.todotracker.models.tasks.Task;
import todo.project.todotracker.models.tasks.TaskDTO;
import todo.project.todotracker.models.users.User;
import todo.project.todotracker.repositories.TaskRepository;
import todo.project.todotracker.repositories.UserRepository;
import todo.project.todotracker.services.TaskService;
import todo.project.todotracker.services.UserService;

import java.io.IOException;
import java.util.List;

@Controller
public class ViewController {

    @Autowired
    TaskService taskService;
    @Autowired
    UserService userService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;


    @RequestMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String landingController(HttpServletResponse httpResponse){
        try {
            httpResponse.sendRedirect("/1?sort-by=lastEdit&sort-dir=asc");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    @RequestMapping("/{page-num}")
    @ResponseStatus(HttpStatus.OK)
    public String dataViewController(
            @PathVariable("page-num") int pageNum,
            @RequestParam(value = "sort-by", required = false) String sortField,
            @RequestParam("sort-dir") String sortDir,
            Model model
    ){
        //sorting default: last edited
        if(sortField == null){
            sortField = "lastEdit";
        }
        Page<Task> page = taskService.getPaginated(pageNum, sortField, sortDir);
        List<Task> tasks = page.getContent();
        //pagination
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalTasks", page.getTotalElements());
        //sorting
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSort", sortDir.equals("asc")?"desc":"asc");
        //data
        model.addAttribute("searchDTO", new SearchDTO());
        model.addAttribute("allTasks", tasks);
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
    public String updateTaskController(Model model, @RequestParam("task") int id, HttpServletResponse httpResponse, Authentication user){
        Task task = taskService.getTaskById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken){
            try {
                httpResponse.sendRedirect("/login");
            } catch (IOException e) {
                model.addAttribute("error", e.getMessage());
                return "index";
            }
        } else {
            if(userService.verifyOwnership(task, user.getName())){
                int status = task.isComplete() ? 1 : 0;
                model.addAttribute("taskDTO", new TaskDTO(task.getTitle(), status, Math.toIntExact(task.getUser().getId())));
                model.addAttribute("allUsers", userService.getAllUsers());
                model.addAttribute("id", id);
                return "updatetask";
            }
            try {
                httpResponse.sendRedirect("/");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @RequestMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public String searchController(@ModelAttribute("searchDTO") SearchDTO searchDTO, Model model){
        List<Task> tasks = taskService.getSearch(searchDTO.getFindBy(), searchDTO.getSearchQuery());
        System.out.println("*** COMPLETED SEARCH ***" + tasks.stream().count());
        model.addAttribute("tasks", tasks);
        return "search";
    }

    @RequestMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(){
        return "login";
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
            model.addAttribute("allUsers", userService.getAllUsers());
            return "newtask";
        }
        return null;
    }

    @PostMapping("/updatetask")
    @ResponseStatus(HttpStatus.CREATED)
    public String postUpdateController(@ModelAttribute("task") TaskDTO taskDTO, @RequestParam("id") int id, Model model, HttpServletResponse httpResponse){
        try{
            Task task = taskService.getTaskById(id);
            User user = userService.getUserById((long) taskDTO.getUserId());
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

    @GetMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public String deleteController(@RequestParam("task") int id, Model model, HttpServletResponse httpResponse){
        try{
           taskService.deleteTask(id);
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
