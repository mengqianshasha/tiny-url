package edu.northeastern.tinyurl.service;

import edu.northeastern.tinyurl.exception.ShortUrlAlreadyExistException;
import edu.northeastern.tinyurl.exception.UrlMappingNotFoundException;
import edu.northeastern.tinyurl.model.UrlMapping;
import edu.northeastern.tinyurl.model.UrlMappingId;
import edu.northeastern.tinyurl.model.UrlMappingRequest;
import edu.northeastern.tinyurl.model.User;
import edu.northeastern.tinyurl.repository.UrlMappingIdRepository;
import edu.northeastern.tinyurl.repository.UrlMappingRepository;
import edu.northeastern.tinyurl.repository.UserRepository;
import edu.northeastern.tinyurl.util.ShortUrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UrlMapping createShortenedUrl(long userId, UrlMappingRequest input) {
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
    public UrlMapping getUrlMapping(String shortUrl) {
        return this.mappingRepository.findById(shortUrl).
                orElseThrow(() -> new UrlMappingNotFoundException("There is no" +
                        " original url for current shortened url:" + shortUrl));
    }

    @Override
    public List<UrlMapping> getUrlMappings(long userId) {
        return this.userRepository.findById(userId).get().getMappings();
    }
}
