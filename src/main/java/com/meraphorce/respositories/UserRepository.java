package com.meraphorce.respositories;

import java.util.List;
import java.util.Optional;
import com.meraphorce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for performing CRUD operations on User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>
{
    /**
     * Get a list names of all users.
     *
     * @return a List of Strings containing the names of all users.
     */
    @Query("select u.name from User u")
    List<String> findNames();

    /**
     * Checks if a user with the specified email exists.
     *
     * @param email the email to check for existence
     * @return true if a user exists with the specified email, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Finds a user by name.
     *
     * @param name the name of the user to find
     * @return an Optional object containing the User, if it exists
     */
    public Optional<User> findByName(String name);
}
