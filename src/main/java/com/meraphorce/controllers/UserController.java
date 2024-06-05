package com.meraphorce.controllers;

import com.meraphorce.dtos.UserResponse;
import com.meraphorce.dtos.UserRequest;
import com.meraphorce.mappers.impl.UserMapper;
import com.meraphorce.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@Validated
public class UserController
{
    private final UserMapper mapper;
    private final UserService userService;

    /**
     * Constructs a UserController with the necessary dependencies.
     *
     * @param mapper the mapper from UserResponse and UserRequest objects to User objects.
     * @param userService the service handling user operations.
     */
    @Autowired
    public UserController(UserMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    /**
     * Creates a new user.
     *
     * @param user the UserRequest object representing the user to be created
     * @return a ResponseEntity containing the created user and HTTP status CREATED
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest user) {
        log.info("Creating user: {}", user);
        return new ResponseEntity<>(mapper.entityToResponse(userService.createUser(mapper.requestToEntity(user))),
                                    HttpStatus.CREATED);
    }

    /**
     * Retrieves the list of all users.
     *
     * @return a ResponseEntity containing the list of all users and HTTP status OK
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        log.info("Fetching all users");
        List<UserResponse> users = userService.getAllUsers().stream()
            .map(mapper::entityToResponse)
            .collect(Collectors.toList());
        log.info("Fetched {} users", users.size());
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieves an user by id
     *
     * @Param id a string containing the id of the user to retrieve
     * @return a ResponseEntity containing the user and HTTP status OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@NotBlank @PathVariable String id) {
        log.info("Fetching user with id: {}", id);
        return ResponseEntity.ok(mapper.entityToResponse(userService.getUserById(id)));
    }

    /**
     * Updates an existing user.
     *
     * @param id the id of the user to update
     * @param userRequest the UserRequest object representing the updated user information
     * @return a ResponseEntity containing the updated user and HTTP status OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String id,
                                                   @Valid @RequestBody UserRequest userRequest) {
        log.info("Updating user with id: {}", id);
        return ResponseEntity
            .ok(mapper.entityToResponse(userService.updateUser(id, mapper.requestToEntity(userRequest))));
    }

    /**
     * Deletes a user by their id.
     *
     * @param id the id of the user to delete
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        log.info("Deleting user with id: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves the names of all users.
     *
     * @return a ResponseEntity containing a list of all user names
     */
    @GetMapping("/names")
    public ResponseEntity<List<String>> getNames() {
        log.info("Get names of all users");
        return ResponseEntity.ok(userService.getNames());
    }

}
