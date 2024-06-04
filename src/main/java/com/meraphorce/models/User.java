package com.meraphorce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a user.
 */
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    /**
     * The unique identifier of the user.
     */
    @Id
    private String id;

    /**
     * The name of the user.
     */
    @Column(nullable = false, length = 50)
    private String name;

    /**
     * The email address of the user.
     */
    @Column(nullable = false, length = 50)
    private String email;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The roles of the user.
     */
    private String roles;
}
