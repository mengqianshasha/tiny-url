package edu.northeastern.tinyurl.service;

import edu.northeastern.tinyurl.model.UrlMapping;
import edu.northeastern.tinyurl.model.UrlMappingRequest;

import java.util.List;

public interface UrlMappingService {
    public UrlMapping createShortenedUrl(String email, UrlMappingRequest request);
    public UrlMapping deleteShortenedUrl(String email, String shortUrl);
    public void deleteShortenedUrlAsync(String email, String shortUrl);
    public UrlMapping getUrlMapping(String shortUrl);
    public List<UrlMapping> getUrlMappings(String email);
    public void cleanUpExpiredMapping();
}
