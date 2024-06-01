package com.meraphorce.mappers.impl;

import com.meraphorce.dtos.SignUpResponse;
import com.meraphorce.dtos.SignUpRequest;
import com.meraphorce.mappers.MapperI;
import com.meraphorce.models.User;
import org.springframework.stereotype.Component;

@Component
public class SignUpMapper implements MapperI<User, SignUpResponse, SignUpRequest>
{
    @Override
    public User requestToEntity(SignUpRequest request) {
        return User.builder()
            .name(request.getUsername())
            .email(request.getEmail())
            .password(request.getPassword())
            .roles(request.getRoles())
            .build();
    }

    @Override
    public SignUpResponse entityToResponse(User entity) {
        return SignUpResponse.builder()
            .id(entity.getId())
            .username(entity.getName())
            .email(entity.getEmail())
            .roles(entity.getRoles())
            .build();
    }

}
