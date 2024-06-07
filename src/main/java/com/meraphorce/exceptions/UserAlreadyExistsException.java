package com.meraphorce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a user with certain email already exists in the database.
 * This results in a HTTP 400 Bad Request response.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends RuntimeException
{
    /**
     * Constructs a new UserAlreadyExistsException with the specified detail message.
     * @param message the detail message.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
