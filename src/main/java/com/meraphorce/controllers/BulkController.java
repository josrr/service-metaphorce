package com.meraphorce.controllers;

import com.meraphorce.dto.StatusResponse;
import com.meraphorce.dto.UserRequest;
import com.meraphorce.mappers.impl.UserMapper;
import com.meraphorce.services.UserAlreadyExistsException;
import com.meraphorce.services.UserService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
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

    @Autowired
    private Validator validator;

    @PostMapping("/users")
    public ResponseEntity<List<StatusResponse>> createUsersInBulk(@NotEmpty @RequestBody
                                                                  List<UserRequest> users) {
        return ResponseEntity.ok(users.stream().map(user -> {
                    Set<ConstraintViolation<UserRequest>> violations = validator.validate(user);
                    if ( violations.isEmpty() ) {
                        try {
                            userService.createUser(mapper.requestToEntity(user));
                            return StatusResponse.builder()
                                .status(HttpStatus.OK.value())
                                .message(String.format("New user with name=%s created", user.getName()))
                                .build();
                        } catch ( UserAlreadyExistsException ex ) {
                            return StatusResponse.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(ex.getMessage())
                                .build();
                        }
                    } else {
                        StringJoiner joiner = new StringJoiner(", ");
                        for ( ConstraintViolation<UserRequest> constraintViolation : violations ) {
                            joiner.add(String.format("%s: %s", getFieldName(constraintViolation),
                                                     constraintViolation.getMessage()));
                        }
                        return StatusResponse.builder()
                            .status(HttpStatus.BAD_REQUEST.value()).message(joiner.toString())
                            .build();
                    }
                }).collect(Collectors.toList()));
    }

    private String getFieldName(ConstraintViolation violation) {
        String field = null;
        for (Path.Node node : violation.getPropertyPath()) {
            field = node.getName();
        }
        return field;
    }
}
