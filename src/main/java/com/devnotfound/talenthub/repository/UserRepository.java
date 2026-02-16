package com.devnotfound.talenthub.repository;

import com.devnotfound.talenthub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    User findByEmail(String email);
    
    List<User> findByNameContainingIgnoreCase(String name);
}
