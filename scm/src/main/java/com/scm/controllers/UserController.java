package com.scm.controllers;

import com.scm.Entities.Contact;
import com.scm.Entities.User;
import com.scm.forms.ContactSearchForm;
import com.scm.services.ContactService;
import com.scm.services.UserService;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// @Controller
// // protected thing in user
// @RequestMapping("/user")
// public class UserController {
//     private Logger logger = LoggerFactory.getLogger(UserController.class);

//     @Autowired
//     private UserService userService;

//     // ✅ YAHAN PASTE KARO (IMPORTANT)
//     private String getEmail(Principal principal, Authentication authentication) {

//         if (authentication != null &&
//             authentication.getPrincipal() instanceof org.springframework.security.oauth2.core.user.DefaultOAuth2User oauthUser) {

//             return oauthUser.getAttribute("email");
//         }

//         if (principal != null) {
//             return principal.getName();
//         }

//         return null;
//     }

//     // user dashboard page

//     @RequestMapping(value = "/dashboard")
//     public String userDashboard() {
//         System.out.println("user dashboard");
//         return "user/dashboard";
//     }

// @RequestMapping(value = "/profile")
// public String userProfile(Authentication authentication, Model model) {

//     return "user/profile";
// }
//     // user contact page

//     // user view contact page

//     // user edit contact page

//     // user delete contact page

// //   @GetMapping("/edit-profile")
// // public String editProfile(Model model, Principal principal) {

// //     String email = principal.getName();
// //     User user = userService.getUserByEmail(email);
// // System.out.println("EMAIL VALUE: " + email);
// //     // ⚠️ NULL safe
// //     if (user == null) {
// //         user = new User();
// //     }

// //     model.addAttribute("user", user); // 🔥 MUST

// //     return "user/edit_profile";
// // }
// @GetMapping("/edit-profile")
// public String editProfile(Model model, Principal principal) {

//     String email = null;

//     if (principal instanceof org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken token) {
//         email = token.getPrincipal().getAttribute("email");
//     } else {
//         email = principal.getName();
//     }

//     System.out.println("EMAIL VALUE: " + email);

//     User user = userService.getUserByEmail(email);

//     if (user == null) {
//         user = new User();
//     }

//     model.addAttribute("user", user);

//     return "user/edit_profile";
// }

// // @PostMapping("/update-profile")
// // public String updateProfile(@ModelAttribute("user") User formUser,
// //                             Principal principal) {

// //     String email = principal.getName();

// //     User existingUser = userService.getUserByEmail(email);

// //     if (existingUser == null) {
// //         throw new RuntimeException("User not found for email: " + email);
// //     }

// //     existingUser.setName(formUser.getName());
// //     existingUser.setPhoneNumber(formUser.getPhoneNumber());
// //     existingUser.setAbout(formUser.getAbout());

// //     // ❌ DO NOT CALL saveUser()
// //     userService.updateUser(existingUser);

// //     return "redirect:/user/profile";
// // }
// @PostMapping("/update-profile")
// public String updateProfile(@ModelAttribute("user") User formUser,
//                             Principal principal,
//                             Authentication authentication) {

//     String email = null;

//     if (authentication.getPrincipal() instanceof org.springframework.security.oauth2.core.user.DefaultOAuth2User oauthUser) {
//         email = oauthUser.getAttribute("email"); // 🔥 Google email
//     } else {
//         email = principal.getName(); // normal login
//     }

//     User existingUser = userService.getUserByEmail(email);

//     existingUser.setName(formUser.getName());
//     existingUser.setPhoneNumber(formUser.getPhoneNumber());
//     existingUser.setAbout(formUser.getAbout());

//     userService.updateUser(existingUser);

//     return "redirect:/user/profile";
// }

// @Autowired
// private ContactService contactService;
// @GetMapping("/contacts")
// public String viewContacts(Model model) {

//     Page<Contact> pageContact = contactService.getContacts(0, 5);

//     model.addAttribute("pageContact", pageContact);

//     model.addAttribute("contactSearchForm", new ContactSearchForm());

//     return "user/contacts";
// }

// @GetMapping("/dashboard")
// public String dashboard(Model model, Principal principal, Authentication authentication) {

//     String email = getEmail(principal, authentication); 

//     User user = userService.getUserByEmail(email);

//     List<Contact> contacts = contactService.getContactsByUser(user);

//     model.addAttribute("contacts", contacts);
//     model.addAttribute("favoriteCount", contactService.countFavorites(user));
//     model.addAttribute("recentCount", contactService.countRecent(user));
//     model.addAttribute("loggedInUser", user);

//     return "user/dashboard";
// }
// }

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    
    private String getEmail(Principal principal, Authentication authentication) {

        if (authentication != null &&
                authentication
                        .getPrincipal() instanceof org.springframework.security.oauth2.core.user.DefaultOAuth2User oauthUser) {

            return oauthUser.getAttribute("email");
        }

        if (principal != null) {
            return principal.getName();
        }

        return null;
    }

    //  GLOBAL USER 
    @ModelAttribute
    public void addLoggedInUser(Model model, Principal principal, Authentication authentication) {

        String email = getEmail(principal, authentication);

        if (email != null) {
            User user = userService.getUserByEmail(email);
            model.addAttribute("loggedInUser", user);
        }
    }

    //  DASHBOARD (ONLY ONE METHOD)
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal, Authentication authentication) {

        String email = getEmail(principal, authentication);
        User user = userService.getUserByEmail(email);

        List<Contact> contacts = contactService.getContactsByUser(user);

        model.addAttribute("contacts", contacts);
        model.addAttribute("favoriteCount", contactService.countFavorites(user));
        model.addAttribute("recentCount", contactService.countRecent(user));

        return "user/dashboard";
    }

    @RequestMapping("/profile")
    public String userProfile() {
        return "user/profile";
    }

    @GetMapping("/edit-profile")
    public String editProfile(Model model, Principal principal) {

        String email = null;

        if (principal instanceof org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken token) {
            email = token.getPrincipal().getAttribute("email");
        } else {
            email = principal.getName();
        }

        System.out.println("EMAIL VALUE: " + email);

        User user = userService.getUserByEmail(email);

        if (user == null) {
            user = new User();
        }

        model.addAttribute("user", user);

        return "user/edit_profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute("user") User formUser,
            Principal principal,
            Authentication authentication) {

        String email = null;

        if (authentication
                .getPrincipal() instanceof org.springframework.security.oauth2.core.user.DefaultOAuth2User oauthUser) {
            email = oauthUser.getAttribute("email"); // 🔥 Google email
        } else {
            email = principal.getName(); // normal login
        }

        User existingUser = userService.getUserByEmail(email);

        existingUser.setName(formUser.getName());
        existingUser.setPhoneNumber(formUser.getPhoneNumber());
        existingUser.setAbout(formUser.getAbout());

        userService.updateUser(existingUser);

        return "redirect:/user/profile";
    }
}