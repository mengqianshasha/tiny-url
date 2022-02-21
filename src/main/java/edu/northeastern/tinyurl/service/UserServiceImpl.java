package edu.northeastern.tinyurl.service;

import edu.northeastern.tinyurl.exception.UserNotFoundException;
import edu.northeastern.tinyurl.model.User;
import edu.northeastern.tinyurl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User getUserByEmail(String email) {
        return this.userRepository.findById(email).orElseThrow(
                () -> new UserNotFoundException("User email doesn't exist"));
    }
}
