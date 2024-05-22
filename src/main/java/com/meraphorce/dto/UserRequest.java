package com.meraphorce.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserRequest {
    @NotEmpty(message = "Can't be empty")
    @NotNull(message = "Can't be null")
    private String name;

    @NotEmpty(message = "Can't be empty")
    @NotNull(message = "Can't be null")
    @Email
    private String email;
}
