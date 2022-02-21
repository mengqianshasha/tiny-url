package edu.northeastern.tinyurl.service;

import edu.northeastern.tinyurl.model.CustomUserDetails;
import edu.northeastern.tinyurl.model.User;
import edu.northeastern.tinyurl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    @Cacheable(value = "user", key = "#email")
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
}
