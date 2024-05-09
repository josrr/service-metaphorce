package com.tiktok.servicetiktok.respositories;

import com.tiktok.servicetiktok.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
}
