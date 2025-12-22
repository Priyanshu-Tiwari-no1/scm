package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.scm.Entities.User;
import com.scm.forms.UserForm;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }

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
        System.out.println("Contact page handler");
        return "contactPage";
    }

    @GetMapping("/login")
    public String login() {
        System.out.println("Login page handler");
        return "login";
    }

    @GetMapping("/regisPage")
    public String register(Model model) {
        System.out.println("Register page handler");
        UserForm userform = new UserForm();
        // userform.setName("Priyanshu");
        // userform.setAbout("This is about : Write some thing about yourself.");
        // You can pre-fill default data if needed
        model.addAttribute("user", userform);
        return "regisPage";
    }

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
public String processRegister(
        @Valid @ModelAttribute("user") UserForm userForm,
        BindingResult bindingResult,
        HttpSession session,
        Model model) {

    System.out.println("Processing registration for: " + userForm);

    //  Validation check
    if (bindingResult.hasErrors()) {
        // show validation messages back on form
        model.addAttribute("user", userForm);
        return "regisPage";
    }

    //  Map UserForm -> User entity
    User user = new User();
    user.setName(userForm.getName());
    user.setEmail(userForm.getEmail());
    user.setPassword(userForm.getPassword());
    user.setAbout(userForm.getAbout());
    user.setPhoneNumber(userForm.getPhoneNumber());
    user.setProfilePic("pic.jpeg");

    userService.saveUser(user);
    System.out.println("User Saved Successfully!");

    Message message = Message.builder()
            .content("Registration Successful")
            .type(MessageType.green)
            .build();
    session.setAttribute("message", message);

    // Redirect to clear POST data
    return "redirect:/regisPage";
}
}