package com.meraphorce.dtos;
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
public class UserResponse {
    /**
     * Id of the user
     */
    private String id;

    /**
     * Name of the user
     */
    private String name;

    /**
     * Email of the user
     */
    private String email;
}
