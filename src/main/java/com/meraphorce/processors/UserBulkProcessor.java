package com.meraphorce.processors;

import com.meraphorce.dtos.StatusResponse;
import com.meraphorce.dtos.UserRequest;
import com.meraphorce.mappers.impl.UserMapper;
import com.meraphorce.models.User;
import com.meraphorce.services.UserAlreadyExistsException;
import com.meraphorce.respositories.UserRepository;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Processor for handling operations in bulk of users.
 */
@Component
@Validated
public class UserBulkProcessor {

    private final UserRepository repository;
    private final UserMapper mapper;

    /**
     * Constructs a new UserBulkProcessor with the specified UserRepository and UserMapper.
     *
     * @param repository the repository for accessing user data
     * @param mapper the mapper for converting between UserRequest and User entities
     * @param validator the Jakarta Bean Validation validator
     */
    @Autowired
    public UserBulkProcessor(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // private String getViolationFieldName(ConstraintViolation violation) {
    //     String field = null;
    //     for (Path.Node node : violation.getPropertyPath()) {
    //         field = node.getName();
    //     }
    //     return field;
    // }

    /**
     * Processes a single user by creating a new user entity in the repository.
     * Throws UserAlreadyExistsException if a user with the same email already exists.
     *
     * @param request the UserRequest object representing the user to be created
     * @throws UserAlreadyExistsException if a user with the given email already exists
     */
    public void processUser(@Valid UserRequest request) {
        if ( repository.existsByEmail(request.getEmail()) )
            throw new UserAlreadyExistsException(String.format("User with email = %s already exists",
                                                               request.getEmail()));
        User user = mapper.requestToEntity(request);
        user.setId(UUID.randomUUID().toString());
        repository.save(user);
    }
}
