package todo.project.todotracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {


    @RequestMapping("/") //path
    public String testTest(Model model){
        model.addAttribute("test", "Thyme is working");
        return "index"; //html to return on path
    }
}
