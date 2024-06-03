package com.meraphorce.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.meraphorce.services.ResourceNotFoundException;
import com.meraphorce.services.UserAlreadyExistsException;
import com.meraphorce.services.UserNotFoundException;

/**
 * Handles the exception accross the application.
 */
@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler
{
    /**
     * Handles UserNotFoundException and ResourceNotFoundException exceptions.
     *
     * @param e the UserNotFoundException or ResourceNotFoundException exception
     * @param request the web request
     * @return a ResponseEntity containing the error message with NOT_FOUND status
     */
    @ExceptionHandler({ UserNotFoundException.class, ResourceNotFoundException.class })
    public final ResponseEntity<String> handleResourceNotFoundException(RuntimeException e, WebRequest request)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles UserAlreadyExistsException exceptions.
     *
     * @param e the UserAlreadyExistsException exception
     * @param request the web request
     * @return a ResponseEntity containing the error message with BAD_REQUEST status
     */
    @ExceptionHandler({ UserAlreadyExistsException.class })
    public final ResponseEntity<String> handleUserAlreadyExistsException(Exception e, WebRequest request)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other exceptions.
     *
     * @param e the exception
     * @param request the web request
     * @return a ResponseEntity containing the error message with INTERNAL_SERVER_ERROR status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
