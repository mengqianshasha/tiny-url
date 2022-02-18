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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UrlMappingServiceImpl implements UrlMappingService{
    @Autowired
    private UrlMappingRepository mappingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UrlMapping createShortenedUrl(long userId, UrlMappingRequest input) {
        String shortUrl = input.getCustomUrl() != null ? input.getCustomUrl() :
                ShortUrlGenerator.generateShortUrl(input.getOriginalUrl());
        UrlMapping mapping = new UrlMapping(
                String.join("/", input.getDomainName(), shortUrl)
                ,input.getOriginalUrl());
        User user = new User();
        user.setUserId(userId);
        mapping.setUser(user);
        Date currentTime = new Date();
        mapping.setCreateDate(currentTime);
        mapping.setExpiryDate(Date.from(
                currentTime.toInstant().plus(365, ChronoUnit.DAYS)));
        return this.mappingRepository.save(mapping);
    }

    @Override
    public void deleteShortenedUrl(long userId, String shortUrl) {
        this.mappingRepository.deleteById(shortUrl);
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
