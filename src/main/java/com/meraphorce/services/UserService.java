package com.meraphorce.services;

import com.meraphorce.dto.UserResponse;
import com.meraphorce.dto.UserRequest;
import com.meraphorce.models.User;
import com.meraphorce.respositories.UserRepository;
import com.meraphorce.mappers.impl.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    public UserResponse createUser(UserRequest user) throws UserAlreadyExistsException {
        if ( userRepository.existsById(user.getId()) )
            throw new UserAlreadyExistsException(String.format("User with id=%s already exists",
                                                               user.getId()));
        return mapper.entityToResponse(userRepository.save(mapper.requestToEntity(user)));
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
            .map(mapper::entityToResponse)
            .collect(Collectors.toList());
    }

    public UserResponse getUserById(String id) throws ResourceNotFoundException {
        return userRepository.findById(id)
            .map(mapper::entityToResponse)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id=%s not found",
                                                                           id)));
    }

    public void deleteUser(String id) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id=%s not found",
                                                                           id)));
        userRepository.delete(user);
    }

    public UserResponse updateUser(String id, UserRequest request) throws ResourceNotFoundException {
        if ( ! userRepository.existsById(id) )
            throw new ResourceNotFoundException(String.format("User with id=%s not found", id));
        User user = mapper.requestToEntity(request);
        user.setId(id);
        return mapper.entityToResponse(userRepository.save(user));
    }
}
