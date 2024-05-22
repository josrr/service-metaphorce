package com.meraphorce.services;

import com.meraphorce.models.User;
import com.meraphorce.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        user.setId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id=%s not found",
                                                                           id)));
    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id=%s not found",
                                                                           id)));
        userRepository.delete(user);
    }

    public User updateUser(String id, User request) {
        if ( ! userRepository.existsById(id) )
            throw new ResourceNotFoundException(String.format("User with id=%s not found", id));
        request.setId(id);
        return userRepository.save(request);
    }
}
