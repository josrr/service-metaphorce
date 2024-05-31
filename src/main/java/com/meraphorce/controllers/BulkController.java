package com.meraphorce.controllers;

import com.meraphorce.dto.StatusResponse;
import com.meraphorce.dto.UserRequest;
import com.meraphorce.mappers.impl.UserMapper;
import com.meraphorce.services.UserService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bulk")
public class BulkController
{
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper mapper;

    @PostMapping("/users")
    public ResponseEntity<List<StatusResponse>> createUsersInBulk(@NotEmpty @Size(min=1, max=100)
                                                                  @RequestBody List<UserRequest> users) {
        return ResponseEntity.ok(userService.createUsersInBulk(users));
    }
}
