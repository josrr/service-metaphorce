package com.meraphorce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a resource is not found in the database.
 * This results in a HTTP 404 Not Found response.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceNotFoundException extends RuntimeException
{
    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     * @param message the detail message.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
