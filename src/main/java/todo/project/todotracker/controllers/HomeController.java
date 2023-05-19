package todo.project.todotracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import todo.project.todotracker.services.TaskService;

@Controller
public class HomeController {

    @Autowired
    TaskService taskService;


    @RequestMapping("/") //path
    public String getIndex(Model model){
        model.addAttribute("allTasks", taskService.getAllTasks());
        return "index";
    }
    @RequestMapping("/newtask")
    public String getNewTaskPage(Model model){
        return "newtask";
    }

}
