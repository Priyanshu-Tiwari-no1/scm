package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
public class PageController {
    @RequestMapping("/home")
    public String home(Model model) {
        System.err.println("home page handler");
        // sending data to view
        model.addAttribute("name", "Anshikalla");
        model.addAttribute("youtubeChannel", "Learn code with durgesh");
        model.addAttribute("githubrepo", "https://github.com/Priyanshu-Tiwari-no1");
        return "home";
    }

//for  route
@RequestMapping("/about")
public String aboutPage(){
    System.out.println("about html");
    return "about";
}
@RequestMapping("/services")
public String servicesPage(){
    System.out.println("services html");
    return "services";
}

}
