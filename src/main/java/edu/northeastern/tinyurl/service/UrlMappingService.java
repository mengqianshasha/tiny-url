package edu.northeastern.tinyurl.service;

import edu.northeastern.tinyurl.model.UrlMapping;
import edu.northeastern.tinyurl.model.UrlMappingRequest;

import java.util.List;

public interface UrlMappingService {
    public String createShortenedUrl(long userId, UrlMappingRequest request);
    public String getOriginalUrl(String shortUrl);
    public List<UrlMapping> getUrlMappings(long userId);
}
