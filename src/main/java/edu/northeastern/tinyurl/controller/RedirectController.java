package edu.northeastern.tinyurl.controller;

import edu.northeastern.tinyurl.service.UrlMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * This is the controller class to handle read original url for given short url and redirect
 */
@Controller
public class RedirectController {
    @Autowired
    private UrlMappingService mappingService;

    @GetMapping("/{shortUrl}")
    public ResponseEntity getOriginalUrl(@PathVariable String shortUrl) {
        String originalUrl = this.mappingService.getUrlMapping(shortUrl).getOriginalUrl();
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", originalUrl).build();
    }
}
