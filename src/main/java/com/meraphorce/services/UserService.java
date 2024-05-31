package com.meraphorce.services;

import com.meraphorce.dto.StatusResponse;
import com.meraphorce.dto.UserRequest;
import com.meraphorce.models.User;
import com.meraphorce.processors.UserBulkProcessor;
import com.meraphorce.respositories.UserRepository;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UserRepository repository;
    private final UserBulkProcessor processor;

    @Autowired
    public UserService(UserRepository repository, UserBulkProcessor processor) {
        this.repository = repository;
        this.processor = processor;
    }

    public User createUser(User user) {
        if ( repository.existsByEmail(user.getEmail()) )
            throw new UserAlreadyExistsException(String.format("A user with email=%s already exists.",
                                                               user.getEmail()));
        user.setId(UUID.randomUUID().toString());
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

    public User updateUser(String id, User request) {
        if ( ! repository.existsById(id) )
            throw new ResourceNotFoundException(String.format("User with id=%s not found", id));
        request.setId(id);
        return repository.save(request);
    }

    public List<String> getNames() {
        return repository.findNames();
    }
}
