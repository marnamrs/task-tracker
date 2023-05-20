package todo.project.todotracker.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import todo.project.todotracker.models.tasks.TaskDTO;
import todo.project.todotracker.models.users.User;
import todo.project.todotracker.services.TaskService;
import todo.project.todotracker.services.UserService;

import java.io.IOException;

@Controller
public class HomeController {

    @Autowired
    TaskService taskService;
    @Autowired
    UserService userService;


    @RequestMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String getIndex(Model model){
        model.addAttribute("allTasks", taskService.getAllTasks());
        return "index";
    }
    @RequestMapping("/newtask")
    @ResponseStatus(HttpStatus.OK)
    public String getNewTaskPage(Model model){
        model.addAttribute("taskDTO", new TaskDTO());
        model.addAttribute("allUsers", userService.getAllUsers());
        return "newtask";
    }

    /** postNewTask takes the DTO task object generated in /newtask and tries to retrieve the User, create and save a new Task. If successful, it returns a redirect to the landing page. Failure to create the Task or redirect will reload /new task with an error message.
     * @param taskDTO task DTO with properties obtained through thymeleaf form binding in /newtask page
     * @param model required to display Exception messages, if any
     * @param httpResponse manages the redirect
     * @return String path
     */
    @PostMapping("/savetask")
    @ResponseStatus(HttpStatus.CREATED)
    public String postNewTask(@ModelAttribute("taskDTO") TaskDTO taskDTO, Model model, HttpServletResponse httpResponse){
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

}
