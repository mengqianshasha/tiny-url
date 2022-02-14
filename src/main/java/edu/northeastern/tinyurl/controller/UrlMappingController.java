package edu.northeastern.tinyurl.controller;

import edu.northeastern.tinyurl.model.UrlMapping;
import edu.northeastern.tinyurl.model.UrlMappingRequest;
import edu.northeastern.tinyurl.service.UrlMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/mapping")
    public String createUrlMapping(@RequestBody UrlMappingRequest input){
        long userId = 1;
        return this.urlMappingService.createShortenedUrl(userId, input);
    }

    @GetMapping("/mapping")
    public List<UrlMapping> getUrlMapping(){
        long userId = 1;
        return this.urlMappingService.getUrlMappings(userId);
    }
}