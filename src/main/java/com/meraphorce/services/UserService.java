package com.meraphorce.services;

import com.meraphorce.dtos.StatusResponse;
import com.meraphorce.dtos.UserRequest;
import com.meraphorce.exceptions.ResourceNotFoundException;
import com.meraphorce.exceptions.UserAlreadyExistsException;
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

/**
 * Service for handling user operations.
 */
@Service
public class UserService
{
    private final UserRepository repository;
    private final UserBulkProcessor processor;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a UserService with the necessary dependencies.
     *
     * @param repository the repository for the user model.
     * @param processor the processor for user bulk operations.
     * @param passwordEncoder the password encoder.
     */
    @Autowired
    public UserService(UserRepository repository, UserBulkProcessor processor,
                       PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.processor = processor;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a new user.
     *
     * @param user the User object representing the user to be created
     * @return the created User object
     * @throws UserAlreadyExistsException when a user with the given email already exists
     */
    public User createUser(User user) {
        if ( repository.existsByEmail(user.getEmail()) )
            throw new UserAlreadyExistsException(String.format("A user with email=%s already exists.",
                                                               user.getEmail()));
        user.setId(UUID.randomUUID().toString());
        if ( user.getPassword() != null )
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    /**
     * Creates a group of users in bulk.
     *
     * @param users a list of UserRequest objects.
     * @return a list of StatusResponse objects.
     */
    public List<StatusResponse> createUsersInBulk(List<UserRequest> users) {
        return users.stream().map(request -> {
                try {
                    processor.processUser(request);
                    return StatusResponse.builder().successful(true)
                        .message(String.format("New user with name=%s created", request.getName()))
                        .build();
                } catch ( UserAlreadyExistsException | ConstraintViolationException ex ) {
                    return StatusResponse.builder().successful(false).message(ex.getMessage()).build();
                } catch ( Exception ex ) {
                    return StatusResponse.builder().successful(false)
                        .message(String.format("An unexpected error occurred: %s", ex.getMessage()))
                        .build();
                }
            }).collect(Collectors.toList());
    }

    /**
     * Gets the list of all users.
     *
     * @return a list of User objects
     */
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    /**
     * Gets an user by identifier.
     *
     * @param id the id of the user
     * @return an User object
     * @throws ResourceNotFoundException if the user is not found
     */
    public User getUserById(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id=%s not found",
                                                                           id)));
    }

    /**
     * Deletes an user by identifier.
     *
     * @param id the id of the user
     * @throws ResourceNotFoundException if the user is not found
     */
    public void deleteUser(String id) {
        User user = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id=%s not found",
                                                                           id)));
        repository.delete(user);
    }

    /**
     * Updates an existing user.
     *
     * @param id the id of the user
     * @param user an User object
     * @return the modificated User oject
     * @throws ResourceNotFoundException if the user is not found
     */
    public User updateUser(String id, User user) {
        if ( ! repository.existsById(id) )
            throw new ResourceNotFoundException(String.format("User with id=%s not found", id));
        user.setId(id);
        if ( user.getPassword() != null )
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    /**
     * Get the names of all users.
     *
     * @return a list of strings
     */
    public List<String> getNames() {
        return repository.findNames();
    }
}
