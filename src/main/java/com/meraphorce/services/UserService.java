package com.meraphorce.services;

import com.meraphorce.dtos.StatusResponse;
import com.meraphorce.dtos.UserRequest;
import com.meraphorce.models.User;
import com.meraphorce.processors.UserBulkProcessor;
import com.meraphorce.respositories.UserRepository;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UserRepository repository;
    private final UserBulkProcessor processor;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, UserBulkProcessor processor,
                       PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.processor = processor;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        if ( repository.existsByEmail(user.getEmail()) )
            throw new UserAlreadyExistsException(String.format("A user with email=%s already exists.",
                                                               user.getEmail()));
        user.setId(UUID.randomUUID().toString());
        if ( user.getPassword() != null )
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public List<StatusResponse> createUsersInBulk(List<UserRequest> users) {
        return users.stream().map(request -> {
                try {
                    processor.processUser(request);
                    return StatusResponse.builder().created(true)
                        .message(String.format("New user with name=%s created", request.getName()))
                        .build();
                } catch ( UserAlreadyExistsException | ConstraintViolationException ex ) {
                    return StatusResponse.builder().created(false).message(ex.getMessage()).build();
                } catch ( Exception ex ) {
                    return StatusResponse.builder().created(false)
                        .message(String.format("An unexpected error occurred: %s", ex.getMessage()))
                        .build();
                }
            }).collect(Collectors.toList());
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id=%s not found",
                                                                           id)));
    }

    public void deleteUser(String id) {
        User user = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id=%s not found",
                                                                           id)));
        repository.delete(user);
    }

    public User updateUser(String id, User user) {
        if ( ! repository.existsById(id) )
            throw new ResourceNotFoundException(String.format("User with id=%s not found", id));
        user.setId(id);
        if ( user.getPassword() != null )
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public List<String> getNames() {
        return repository.findNames();
    }
}
