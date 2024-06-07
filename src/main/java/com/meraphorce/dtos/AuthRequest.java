package com.meraphorce.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for the authorization request.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    /**
     * The username of the user to authorize.
     */
    @NotBlank
    @Size(min = 2, max = 50)
    private String username;

    /**
     * The password of the user to authorize.
     */
    @NotBlank
    @Size(max = 512)
    private String password;
}
