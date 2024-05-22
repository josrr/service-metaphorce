package com.meraphorce.controllers;

import com.meraphorce.dto.UserResponse;
import com.meraphorce.dto.UserRequest;
import com.meraphorce.mappers.impl.UserMapper;
import com.meraphorce.models.User;
import com.meraphorce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper mapper;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest user) {
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
                                                   @RequestBody UserRequest userRequest) {
        return ResponseEntity
            .ok(mapper.entityToResponse(userService
                                        .updateUser(id, mapper.requestToEntity(userRequest))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
