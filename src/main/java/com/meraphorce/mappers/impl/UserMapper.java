package com.meraphorce.mappers.impl;

import com.meraphorce.dtos.UserResponse;
import com.meraphorce.dtos.UserRequest;
import com.meraphorce.mappers.MapperI;
import com.meraphorce.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements MapperI<User, UserResponse, UserRequest>
{
    // @Override
    // public User responseToEntity(UserResponse response) {
    //     return User.builder()
    //         .id(response.getId())
    //         .name(response.getName())
    //         .email(response.getEmail())
    //         .build();
    // }

    @Override
    public User requestToEntity(UserRequest request) {
        return User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(request.getPassword())
            .roles(request.getRoles())
            .build();
    }

    @Override
    public UserResponse entityToResponse(User entity) {
        return UserResponse.builder()
            .id(entity.getId())
            .name(entity.getName())
            .email(entity.getEmail())
            // .roles(entity.getRoles())
            .build();
    }

    // @Override
    // public UserRequest entityToRequest(User entity) {
    //     return UserRequest.builder()
    //         .name(entity.getName())
    //         .email(entity.getEmail())
    //         .build();
    // }
}
