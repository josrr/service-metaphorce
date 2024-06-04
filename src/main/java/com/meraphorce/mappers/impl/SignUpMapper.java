package com.meraphorce.mappers.impl;

import com.meraphorce.dtos.SignUpResponse;
import com.meraphorce.dtos.SignUpRequest;
import com.meraphorce.mappers.MapperI;
import com.meraphorce.models.User;
import org.springframework.stereotype.Component;

/**
 * Mapper interface to translate between user entities objects and sign up DTO objects.
 */
@Component
public class SignUpMapper implements MapperI<User, SignUpResponse, SignUpRequest>
{
    /**
     * Translates from the SignUpRequest DTO to the User entity object.
     *
     * @param request a SignUpRequest DTO
     * @return a User entity object
     */
    @Override
    public User requestToEntity(SignUpRequest request) {
        return User.builder()
            .name(request.getUsername())
            .email(request.getEmail())
            .password(request.getPassword())
            .roles(request.getRoles())
            .build();
    }

    /**
     * Translates from the User entity object to the SignUpResponse DTO.
     *
     * @param entity a User entity object
     * @return a SignUpResponse DTO
     */
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
