package com.meraphorce.respositories;

import java.util.List;
import java.util.Optional;
import com.meraphorce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    @Query("select u.name from User u")
    List<String> findNames();
    boolean existsByEmail(String email);
}
