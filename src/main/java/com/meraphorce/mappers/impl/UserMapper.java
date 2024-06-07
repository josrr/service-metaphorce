package com.meraphorce.mappers.impl;

import com.meraphorce.dtos.UserResponse;
import com.meraphorce.dtos.UserRequest;
import com.meraphorce.mappers.MapperI;
import com.meraphorce.models.User;
import org.springframework.stereotype.Component;

/**
 * Mapper interface to translate between user entities objects and user DTO objects.
 */
@Component
public class UserMapper implements MapperI<User, UserResponse, UserRequest>
{
    /**
     * Translates from the UserRequest DTO to the User entity object.
     *
     * @param request a UserRequest DTO
     * @return a User entity object
     */
    @Override
    public User requestToEntity(UserRequest request) {
        return User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(request.getPassword())
            .roles(request.getRoles())
            .build();
    }

    /**
     * Translates from the User entity object to the UserResponse DTO.
     *
     * @param entity a User entity object
     * @return a UserResponse DTO
     */
    @Override
    public UserResponse entityToResponse(User entity) {
        return UserResponse.builder()
            .id(entity.getId())
            .name(entity.getName())
            .email(entity.getEmail())
            .build();
    }
}
