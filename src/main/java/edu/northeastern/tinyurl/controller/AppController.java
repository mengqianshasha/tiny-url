package edu.northeastern.tinyurl.controller;

import edu.northeastern.tinyurl.model.UrlMapping;
import edu.northeastern.tinyurl.model.UrlMappingRequest;
import edu.northeastern.tinyurl.model.User;
import edu.northeastern.tinyurl.service.UrlMappingService;
import edu.northeastern.tinyurl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

/**
 * This is the controller class for the web application
 */
@Controller
@RequestMapping("/app")
public class AppController {
    @Autowired
    private UserService userService;

    @Autowired
    private UrlMappingService mappingService;

    @GetMapping("")
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

    @PostMapping("/register")
    public String handleRegistration(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        this.userService.createUser(user);

        return "login";
    }

    @GetMapping("/myurls")
    public String showHistory(
            Model model,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("mappings", this.mappingService.getUrlMappings(
                this.userService.getUserByEmail(userDetails.getUsername()).getUserId()));

        return "history";
    }

    @PostMapping("/myurls")
    public String createMapping(
            UrlMappingRequest request,
            Model model,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UrlMapping mapping = this.mappingService.createShortenedUrl(
                this.userService.getUserByEmail(userDetails.getUsername()).getUserId(), request);
        model.addAttribute("actionMapping", mapping);
        model.addAttribute("mapping_action", "created");
        model.addAttribute("mappings", this.mappingService.getUrlMappings(
                this.userService.getUserByEmail(userDetails.getUsername()).getUserId()));
        return "history";
    }

    @PostMapping("/myurls/{shortUrl}")
    public String deleteMapping(
            @PathVariable String shortUrl,
            RedirectAttributes attributes,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        long userId = this.userService.getUserByEmail(userDetails.getUsername()).getUserId();
        UrlMapping mapping = this.mappingService.deleteShortenedUrl(userId, shortUrl);
        // These two will be added as model attributes after redirect
        attributes.addFlashAttribute("actionMapping", mapping);
        attributes.addFlashAttribute("mapping_action", "deleted");
        return "redirect:/app/myurls";
    }
}
