package com.meraphorce.controllers;

import com.meraphorce.dtos.AuthRequest;
import com.meraphorce.dtos.SignUpResponse;
import com.meraphorce.dtos.SignUpRequest;
import com.meraphorce.models.User;
import com.meraphorce.mappers.impl.SignUpMapper;
import com.meraphorce.services.JwtService;
import com.meraphorce.services.UserInfoService;
import com.meraphorce.services.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@Validated
public class AuthController
{
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SignUpMapper signUpMapper;
    // private final UserInfoService userInfoService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
                          SignUpMapper singUpMapper, // UserInfoService userInfoService,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.signUpMapper = singUpMapper;
        // this.userInfoService = userInfoService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(signUpMapper.entityToResponse
                                 (userService.createUser(signUpMapper.requestToEntity(request))));
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "This endpoint is not secure yet";
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@Valid @RequestBody AuthRequest authRequest) {
        log.debug("authRequest: {}", authRequest);
        Authentication authentication = authenticationManager.authenticate
            (new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if ( authentication.isAuthenticated() ) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
