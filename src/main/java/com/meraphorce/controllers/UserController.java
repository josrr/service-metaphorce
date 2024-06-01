package com.meraphorce.controllers;

import com.meraphorce.dtos.UserResponse;
import com.meraphorce.dtos.UserRequest;
import com.meraphorce.mappers.impl.UserMapper;
import com.meraphorce.services.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public UserController(UserMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest user) {
        return ResponseEntity.ok(mapper.entityToResponse(userService
                                                         .createUser(mapper.requestToEntity(user))));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers().stream()
                                 .map(mapper::entityToResponse)
                                 .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(mapper.entityToResponse(userService.getUserById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String id,
                                                   @Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity
            .ok(mapper.entityToResponse(userService
                                        .updateUser(id, mapper.requestToEntity(userRequest))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getNames() {
        return ResponseEntity.ok(userService.getNames());
    }

}
