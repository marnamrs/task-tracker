package todo.project.todotracker.controllers;

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

    @PostMapping("/savetask")
    @ResponseStatus(HttpStatus.CREATED)
    public String postNewTask(@ModelAttribute("taskDTO") TaskDTO taskDTO){
        System.out.println(taskDTO.getTitle() + " " + taskDTO.getUserId() + " " + taskDTO.getIsComplete());
        User user = userService.getUserById((long) taskDTO.getUserId());
        taskService.createTask(taskDTO, user);
        return "index";
    }

}
