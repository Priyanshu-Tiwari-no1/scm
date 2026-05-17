package com.scm.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {

        // OAuth2 login (Google / GitHub)
        if (authentication instanceof OAuth2AuthenticationToken) {

            OAuth2AuthenticationToken authToken =
                    (OAuth2AuthenticationToken) authentication;

            OAuth2User user = authToken.getPrincipal();

            String clientId = authToken.getAuthorizedClientRegistrationId();

            if (clientId.equalsIgnoreCase("google")) {

                System.out.println("getting email from google");
                return user.getAttribute("email");

            } 
            else if (clientId.equalsIgnoreCase("github")) {

                System.out.println("getting email from github");

                String email = user.getAttribute("email");

                if (email != null) {
                    return email;
                } else {
                    return user.getAttribute("login") + "@gmail.com";
                }
            }
        }

        // normal login (database email/password)
        System.out.println("Getting data from local database");
        return authentication.getName();
    }
}