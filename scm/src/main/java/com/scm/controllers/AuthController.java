package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.Entities.User;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.repositories.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    // verify email

    @Autowired
    private UserRepo userRepo;

  @GetMapping("/verify-email")
public String verifyEmail(
        @RequestParam("token") String token,
        HttpSession session) {

    // token ko email maan rahe hain
    User user = userRepo.findByEmail(token).orElse(null);

    if (user == null) {
        session.setAttribute("message", Message.builder()
                .type(MessageType.red)
                .content("Email not verified! Invalid token.")
                .build());
        return "error_page";
    }

    user.setEmailVarified(true);
    user.setEnabled(true);
    userRepo.save(user);

    session.setAttribute("message", Message.builder()
            .type(MessageType.green)
            .content("Your email is verified. Now you can login.")
            .build());

    return "success_page";
}
}