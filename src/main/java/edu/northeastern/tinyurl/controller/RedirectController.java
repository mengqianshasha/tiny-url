package edu.northeastern.tinyurl.controller;

import edu.northeastern.tinyurl.exception.ShortUrlExpiredException;
import edu.northeastern.tinyurl.model.UrlMapping;
import edu.northeastern.tinyurl.model.UrlMappingStatus;
import edu.northeastern.tinyurl.service.UrlMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String getOriginalUrl(@PathVariable String shortUrl, Model model) {
        String originalUrl = null;
        try {
             UrlMapping mapping = this.mappingService.getUrlMapping(shortUrl);
             if (mapping.getStatus() == UrlMappingStatus.Expired){
                 this.mappingService.deleteShortenedUrlAsync(
                         mapping.getUser().getEmail(), shortUrl);
                 throw new ShortUrlExpiredException("Error: this short url has been expired");
             }

             originalUrl = mapping.getOriginalUrl();
        }catch (Exception ex){
             model.addAttribute("error", ex.getMessage());
             return "redirect_error";
        }

        return "redirect:" + originalUrl;
    }
}
