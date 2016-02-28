package sec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sec.model.DBCreate;

@Controller
public class AppController {
    @Autowired
    DBCreate create;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("message","This is page is free for use. Non-authenticated users have access");
        return "index";
    }

    @RequestMapping("/private")
    public String privateInf(Model model){
        model.addAttribute("message","This page contains private information. Only authenticated users access allowed.");
        return "private";
    }

    @RequestMapping("/admin")
    public String admin(Model model){
        return "admin";
    }

    @RequestMapping("/create")
    public String create(Model model){
        model.addAttribute("resultUser", create.createUser());
        model.addAttribute("resultAuthorities",create.createAuthorities());
        model.addAttribute("resultInsert",create.insertInfo());
        model.addAttribute("userModel",create.getUsers());
        model.addAttribute("name","User");
        model.addAttribute("password","Password");
        model.addAttribute("valid","Enabled");
        model.addAttribute("instruction","Use these users to access private area!");
        return "createQuestion";
    }

    @RequestMapping("/createQuestion")
    public String createQuestion(){
        return "createQuestion";
    }
}
