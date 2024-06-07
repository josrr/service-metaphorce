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
 * Data Transfer Object for the sign up request.
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SignUpRequest {
    /**
     * The username of the new user to sign up.
     */
    @NotBlank
    @Size(min = 2, max = 50)
    private String username;

    /**
     * The email of the new user to sign up.
     */
    @NotBlank
    @Email
    private String email;

    /**
     * The password of the new user to sign up.
     */
    @NotBlank
    @Size(min = 6, max = 512)
    private String password;

    /**
     * The roles of the new user to sign up.
     */
    @NotBlank
    @Size(max = 64)
    @Pattern(regexp="^(administrator|manager|user)(,(administrator|manager|user)){0,2}$")
    private String roles;
}
