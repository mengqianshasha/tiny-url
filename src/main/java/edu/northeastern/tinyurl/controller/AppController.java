package edu.northeastern.tinyurl.controller;

import edu.northeastern.tinyurl.model.UrlMapping;
import edu.northeastern.tinyurl.model.UrlMappingRequest;
import edu.northeastern.tinyurl.model.User;
import edu.northeastern.tinyurl.service.UrlMappingService;
import edu.northeastern.tinyurl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AppController {
    @Autowired
    private UserService userService;

    @Autowired
    private UrlMappingService mappingService;

    @GetMapping("/")
    public String showHomePage(Model model, HttpServletRequest request) {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        UrlMappingRequest mappingRequest = new UrlMappingRequest();
        mappingRequest.setDomainName(baseUrl);

        model.addAttribute("mapping", mappingRequest);
        return "home";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @GetMapping("/mapping_history")
    public String showHistory(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("mappings", this.mappingService.getUrlMappings(
                this.userService.getUserByEmail(userDetails.getUsername()).getUserId()));
        return "history";
    }

    @PostMapping("/create_mapping")
    public String createMapping(
            UrlMappingRequest request,
            Model model,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UrlMapping mapping = this.mappingService.createShortenedUrl(
                this.userService.getUserByEmail(userDetails.getUsername()).getUserId(), request);
        model.addAttribute("created", mapping);
        model.addAttribute("mappings", this.mappingService.getUrlMappings(
                this.userService.getUserByEmail(userDetails.getUsername()).getUserId()));
        return "history";
    }

    @PostMapping("/delete_mapping/")
    public String deleteMapping(
            UrlMapping mapping,
            Model model,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        long userId = this.userService.getUserByEmail(userDetails.getUsername()).getUserId();
        this.mappingService.deleteShortenedUrl(userId, mapping.getShortUrl());
        model.addAttribute("deleted", mapping);
        model.addAttribute("mappings", this.mappingService.getUrlMappings(userId));
        return "history";
    }

    @PostMapping("/register")
    public String handleRegistration(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        this.userService.createUser(user);

        return "login";
    }
}
