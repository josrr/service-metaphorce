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

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler({ UserNotFoundException.class, ResourceNotFoundException.class })
    public final ResponseEntity<String> handleResourceNotFoundException(RuntimeException e, WebRequest request)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ UserAlreadyExistsException.class })
    public final ResponseEntity<String> handleUserAlreadyExistsException(Exception e, WebRequest request)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
