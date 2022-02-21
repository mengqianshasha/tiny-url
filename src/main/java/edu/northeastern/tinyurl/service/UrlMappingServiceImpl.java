package edu.northeastern.tinyurl.service;

import edu.northeastern.tinyurl.exception.ShortUrlAlreadyExistException;
import edu.northeastern.tinyurl.exception.ShortUrlNotBelongToCurrentUserException;
import edu.northeastern.tinyurl.exception.ShortUrlNotFoundException;
import edu.northeastern.tinyurl.model.UrlMapping;
import edu.northeastern.tinyurl.model.UrlMappingId;
import edu.northeastern.tinyurl.model.UrlMappingRequest;
import edu.northeastern.tinyurl.model.User;
import edu.northeastern.tinyurl.repository.UrlMappingIdRepository;
import edu.northeastern.tinyurl.repository.UrlMappingRepository;
import edu.northeastern.tinyurl.repository.UserRepository;
import edu.northeastern.tinyurl.util.ShortUrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class UrlMappingServiceImpl implements UrlMappingService{
    @Autowired
    private UrlMappingRepository mappingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UrlMappingIdRepository idRepository;

    @Override
    public UrlMapping createShortenedUrl(String email, UrlMappingRequest input) {
        if (input.getCustomUrl() != null && !input.getCustomUrl().isEmpty() &&
                this.mappingRepository.findById(input.getCustomUrl()).isPresent()){
            throw new ShortUrlAlreadyExistException(
                    "The custom url:" + input.getCustomUrl() + " already exists");
        }

        long value = this.idRepository.save(new UrlMappingId()).getId();
        String shortUrl = input.getCustomUrl() != null && !input.getCustomUrl().isEmpty() ? input.getCustomUrl() :
                ShortUrlGenerator.base62Encoding(value);

        UrlMapping mapping = new UrlMapping(shortUrl ,input.getOriginalUrl());
        mapping.setDomainName(input.getDomainName());
        User user = new User();
        user.setEmail(email);
        mapping.setUser(user);
        Date currentTime = new Date();
        mapping.setCreateDate(currentTime);
        mapping.setExpiryDate(Date.from(
                currentTime.toInstant().plus(365, ChronoUnit.DAYS)));
        return this.mappingRepository.save(mapping);
    }

    @Override
    @CacheEvict(value = "url_mapping", key = "#shortUrl")
    public UrlMapping deleteShortenedUrl(String email, String shortUrl) {
        UrlMapping mapping = this.getUrlMapping(shortUrl);
        if (mapping.getUser().getEmail() != email){
            throw new ShortUrlNotBelongToCurrentUserException(
                    "Trying to delete a short url not belong to current user is not allowed");
        }

        this.mappingRepository.deleteById(shortUrl);
        return mapping;
    }

    @Override
    @Cacheable(value = "url_mapping", key = "#shortUrl")
    public UrlMapping getUrlMapping(String shortUrl) {
        return this.mappingRepository.findById(shortUrl).
                orElseThrow(() -> new ShortUrlNotFoundException("There is no" +
                        " original url for current shortened url:" + shortUrl));
    }

    @Override
    public List<UrlMapping> getUrlMappings(String email) {
        return this.userRepository.findById(email).get().getMappings();
    }
}
