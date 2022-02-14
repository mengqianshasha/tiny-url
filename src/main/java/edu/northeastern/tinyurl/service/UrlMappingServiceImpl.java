package edu.northeastern.tinyurl.service;

import edu.northeastern.tinyurl.exception.UrlMappingNotFoundException;
import edu.northeastern.tinyurl.model.UrlMapping;
import edu.northeastern.tinyurl.model.UrlMappingRequest;
import edu.northeastern.tinyurl.model.User;
import edu.northeastern.tinyurl.repository.UrlMappingRepository;
import edu.northeastern.tinyurl.repository.UserRepository;
import edu.northeastern.tinyurl.util.ShortUrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UrlMappingServiceImpl implements UrlMappingService{
    @Autowired
    private UrlMappingRepository mappingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String createShortenedUrl(long userId, UrlMappingRequest input) {
        UrlMapping mapping = new UrlMapping(
                input.getCustomUrl() != null ? input.getCustomUrl() :
                        ShortUrlGenerator.generateShortUrl(input.getOriginalUrl()), input.getOriginalUrl());
        User user = new User();
        user.setUserId(userId);
        mapping.setUser(user);
        return this.mappingRepository.save(mapping).getShortUrl();
    }

    @Override
    public String getOriginalUrl(String shortUrl) {
        return this.mappingRepository.findById(shortUrl).
                orElseThrow(() -> new UrlMappingNotFoundException("There is no" +
                        " original url for current shortened url:" + shortUrl)).getOriginalUrl();
    }

    @Override
    public List<UrlMapping> getUrlMappings(long userId) {
        return this.userRepository.findById(userId).get().getMappings();
    }
}
