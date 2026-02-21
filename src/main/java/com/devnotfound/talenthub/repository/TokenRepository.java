package com.devnotfound.talenthub.repository;

import com.devnotfound.talenthub.entity.Token;
import com.devnotfound.talenthub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {

    boolean existsByToken(String token);
}
