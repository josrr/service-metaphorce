package com.meraphorce.dtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for the sign up response.
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SignUpResponse {
    /**
     * The id of the signed up user.
     */
    private String id;

    /**
     * The username of the signed up user.
     */
    private String username;

    /**
     * The email of the signed up user.
     */
    private String email;

    /**
     * The roles of the signed up user.
     */
    private String roles;
}
