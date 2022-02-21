package edu.northeastern.tinyurl.controller;

import edu.northeastern.tinyurl.model.UrlMapping;
import edu.northeastern.tinyurl.model.UrlMappingRequest;
import edu.northeastern.tinyurl.service.UrlMappingService;
import edu.northeastern.tinyurl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This is the controller class for the restful web service
 */
@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private UrlMappingService urlMappingService;

    @GetMapping("/urls/{shortUrl}")
    public String getOneUrlMapping(@PathVariable String shortUrl){
        return this.urlMappingService.getUrlMapping(shortUrl).getOriginalUrl();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/urls")
    public UrlMapping createUrlMapping(@RequestBody UrlMappingRequest input, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return this.urlMappingService.createShortenedUrl(userDetails.getUsername(), input);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/urls/{shortUrl}")
    public ResponseEntity deleteUrlMapping(@PathVariable String shortUrl, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        this.urlMappingService.deleteShortenedUrl(userDetails.getUsername(), shortUrl);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/urls")
    public List<UrlMapping> getUrlMapping(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return this.urlMappingService.getUrlMappings(userDetails.getUsername());
    }
}