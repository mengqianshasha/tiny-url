package edu.northeastern.tinyurl.service;

import edu.northeastern.tinyurl.model.UrlMapping;
import edu.northeastern.tinyurl.model.UrlMappingRequest;

import java.util.List;

public interface UrlMappingService {
    public UrlMapping createShortenedUrl(long userId, UrlMappingRequest request);
    public void deleteShortenedUrl(long userId, String shortUrl);
    public UrlMapping getUrlMapping(String shortUrl);
    public List<UrlMapping> getUrlMappings(long userId);
}
