package com.meraphorce.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for JWT response.
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AuthResponse
{
    /**
     * The JWT token.
     */
    private String token;

    /**
     * The token type.
     */
    private String type;
}
