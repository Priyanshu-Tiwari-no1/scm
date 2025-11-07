package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.scm.forms.UserForm;

import org.springframework.ui.Model;

@Controller
public class PageController {

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page handler");
        model.addAttribute("name", "Anshikalla");
        model.addAttribute("youtubeChannel", "Learn code with durgesh");
        model.addAttribute("githubrepo", "https://github.com/Priyanshu-Tiwari-no1");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage() {
        System.out.println("About page handler");
        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("Services page handler");
        return "services";
    }

    @GetMapping("/contactPage")
    public String contact() {
        return "contactPage";
    }

    @GetMapping("/loginPage")
    public String login() {
        return "loginPage";
    }

    @GetMapping("/regisPage")
    public String register(Model model) {

        UserForm userform=new UserForm();
        //default data bhi dal sakte h
        model.addAttribute("user",userform);

        return "regisPage";
    }

    @PostMapping("/do-register")
    public String processRegister() {
        System.out.println("Processing registration");
        // TODO: fetch form data, validate, save to DB
        return "redirect:/regisPage"; // redirect after successful registration
    }
}
