package com.devnotfound.talenthub.repository;

import com.devnotfound.talenthub.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    boolean existsByToken(String token);
}
