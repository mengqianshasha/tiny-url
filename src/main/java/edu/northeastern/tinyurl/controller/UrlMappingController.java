package edu.northeastern.tinyurl.controller;

import edu.northeastern.tinyurl.model.UrlMapping;
import edu.northeastern.tinyurl.model.UrlMappingRequest;
import edu.northeastern.tinyurl.service.UrlMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url")
public class UrlMappingController {
    @Autowired
    private UrlMappingService urlMappingService;

    @GetMapping("/mapping/{shortUrl}")
    public ResponseEntity getOneUrlMapping(@PathVariable String shortUrl){
        String originalUrl = this.urlMappingService.getOriginalUrl(shortUrl);
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", originalUrl).build();
    }

    @PostMapping("/mapping/{userId}")
    public String createUrlMapping(@PathVariable long userId, @RequestBody UrlMappingRequest input){
        return this.urlMappingService.createShortenedUrl(userId, input);
    }
}
