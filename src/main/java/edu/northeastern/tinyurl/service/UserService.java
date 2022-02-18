package edu.northeastern.tinyurl.service;

import edu.northeastern.tinyurl.model.User;

public interface UserService {
    void createUser(User user);
    User getUserByEmail(String email);
}
