package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.Entities.Providers;
import com.scm.Entities.User;
import com.scm.helper.AppConstants;
import com.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

       

        logger.info("OAuthAuthenticationsuccessHandler");

        // identify the provider

        var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        // pata chalea kisse hum authentication provider mil raha h
        String authorizedClientRegistrationClientId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

        logger.info(authorizedClientRegistrationClientId);
        var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        oauthUser.getAttributes().forEach((key, value) -> {
            logger.info(key + ":" + value);
        });

        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVarified(true);
        user.setEnabled(true);
        user.setPassword("dummy");

        if (authorizedClientRegistrationClientId.equalsIgnoreCase("google")) {

            // google attributes
            user.setEmail(oauthUser.getAttribute("email").toString());
            user.setProfilePic(oauthUser.getAttribute("picture").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setProviderUserId(oauthUser.getName());
             user.setAbout("google");

        } else if (authorizedClientRegistrationClientId.equalsIgnoreCase("github")) {

            // github attributes
            String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
                    : oauthUser.getAttribute("login").toString() + "@gmail.com";
            String picture = oauthUser.getAttribute("avatar_url").toString();
            String name = oauthUser.getAttribute("login").toString();
            String providerUserId = oauthUser.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderUserId(providerUserId);
            user.setProvider(Providers.GITHUB);
            user.setAbout("github");

        } else if (authorizedClientRegistrationClientId.equalsIgnoreCase("linkedin")) {
            // linkedin
        } else {
            logger.info("OAuthAuthenicationSuccessHandler: Unknown provider");
        }

        // DefaultOAuth2User user2 = (DefaultOAuth2User) authentication.getPrincipal();

        // // logger.info(user.getName());

        // // user.getAttributes().forEach((key, value) -> {

        // // logger.info("{}=>{}", key, value);

        // // });

        User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (user2 == null){
            userRepo.save(user);
            System.out.println("user saved user saved user saved user saved user saved user saved user saved user saved user saved user saaaaaaaaaaaaaaaaaaaaaaved ");
}
        // authentication ke baad kaha redirect kerega
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }

}
