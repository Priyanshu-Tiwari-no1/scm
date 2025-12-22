package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    // user dashboard page

    @RequestMapping(value = "/dashboard")
    public String userDashboard()
    {
        System.out.println("user dashboard");
        return "user/dashboard";
    }

    // user profile page
    @RequestMapping(value = "/profile")
    public String userProfile()
    {
        System.out.println("user profile");
        return "user/profile";
    }
    // user contact page

    // user view contact page

    // user edit contact page

    // user delete contact page

}
