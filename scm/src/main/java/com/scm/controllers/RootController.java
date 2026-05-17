package com.scm.controllers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.Entities.User;
import com.scm.services.UserService;

@ControllerAdvice//class ander jo method h wo her ek request ke liye execute hoga
public class RootController {

    private Logger logger =org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired
private UserService userService;

    @ModelAttribute  //user controller ke her ek handler method me niche ka method add ho
  
public void addLoggedInUserInformation(Model model, Authentication authentication) {

    if (authentication == null) {
        return;
    }

    String email = null;

    // FORM LOGIN
    if (authentication.getPrincipal() instanceof UserDetails) {
        email = ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    //GOOGLE / GITHUB LOGIN
    else if (authentication.getPrincipal() instanceof OAuth2User) {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        // Google & GitHub both support "email"
        email = oauth2User.getAttribute("email");

        // GitHub fallback
        if (email == null) {
            email = oauth2User.getAttribute("login") + "@github.com";
        }
    }

    if (email == null) {
        return;
    }

    User user = userService.getUserByEmail(email);

    
    // ------------------ ADDED START ------------------
// Auto-create user if not found (for GitHub / Google login)
if (user == null) {
    logger.info("User not found in DB for email: {}. Creating new user.", email);

    user = new User();
    user.setEmail(email);

    // Try to set name from OAuth attributes
    if (authentication.getPrincipal() instanceof OAuth2User) {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String name = oauth2User.getAttribute("name"); // Google
        if (name == null) {
            name = oauth2User.getAttribute("login"); // GitHub fallback
        }
        user.setName(name != null ? name : "GitHub User");

        //  SET DUMMY PASSWORD for OAuth users
        // BCrypt encode a random/fixed string so it won't be null
        user.setPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("oauthDummyPassword123"));
    } else {
        user.setName("User");
        // For FORM users, password should come from registration form
        // Ensure it's already set if required
    }

    userService.saveUser(user); // <-- now works safely
}
// ------------------ ADDED END ------------------

    model.addAttribute("loggedInUser", user);
}


}
