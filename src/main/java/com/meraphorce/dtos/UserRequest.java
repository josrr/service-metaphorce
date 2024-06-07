package com.meraphorce.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object representing user information.
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserRequest {
    /**
     * Name of the user
     */
    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    /**
     * Email of the user
     */
    @NotBlank
    @Email
    private String email;

    /**
     * Password of the user
     */
    @Size(min = 6, max = 512)
    private String password;

    /**
     * Roles of the user
     */
    @Size(max = 64)
    @Pattern(regexp="^(administrator|manager|user)(,(administrator|manager|user)){0,2}$")
    private String roles;
}
