package edu.northeastern.tinyurl.controller;

import edu.northeastern.tinyurl.model.UrlMapping;
import edu.northeastern.tinyurl.model.UrlMappingRequest;
import edu.northeastern.tinyurl.service.UrlMappingService;
import edu.northeastern.tinyurl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This is the controller class for the restful web service
 */
@RestController
@RequestMapping("/url")
public class UrlMappingController {
    @Autowired
    private UrlMappingService urlMappingService;

    @Autowired
    private UserService userService;

    @Secured("permitAll")
    @GetMapping("/mapping/{shortUrl}")
    public ResponseEntity getOneUrlMapping(@PathVariable String shortUrl){
        String originalUrl = this.urlMappingService.getUrlMapping(shortUrl).getOriginalUrl();
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", originalUrl).build();
    }

    @PostMapping("/mapping")
    public UrlMapping createUrlMapping(@RequestBody UrlMappingRequest input, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return this.urlMappingService.createShortenedUrl(
                this.userService.getUserByEmail(userDetails.getUsername()).getUserId(), input);
    }

    @DeleteMapping("/mapping/{shortUrl}")
    public ResponseEntity deleteUrlMapping(@PathVariable String shortUrl, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        this.urlMappingService.deleteShortenedUrl(
                this.userService.getUserByEmail(userDetails.getUsername()).getUserId(), shortUrl);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/mapping")
    public List<UrlMapping> getUrlMapping(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return this.urlMappingService.getUrlMappings(
                this.userService.getUserByEmail(userDetails.getUsername()).getUserId());
    }
}