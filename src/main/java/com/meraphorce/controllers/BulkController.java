package com.meraphorce.controllers;

import com.meraphorce.dtos.StatusResponse;
import com.meraphorce.dtos.UserRequest;
import com.meraphorce.mappers.impl.UserMapper;
import com.meraphorce.services.UserService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bulk")
@Slf4j
public class BulkController
{
    private final UserService userService;

    /**
     * Constructs a BulkController with the necessary dependencies.
     *
     * @param userService the service handling user operations.
     */
    @Autowired
    public BulkController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates multiple new users in bulk.
     *
     * @param users a list of UserRequest objects representing the users to be created
     * @return a ResponseEntity containing the list of creation status of every user and HTTP status OK
     */
    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<StatusResponse>> createUsersInBulk(@NotEmpty @Size(min=1, max=100)
                                                                  @RequestBody List<UserRequest> users) {
        log.info("Creating {} users in bulk", users.size());
        return ResponseEntity.ok(userService.createUsersInBulk(users));
    }
}
