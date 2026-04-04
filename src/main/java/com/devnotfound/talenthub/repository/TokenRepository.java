package com.devnotfound.talenthub.repository;

import com.devnotfound.talenthub.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    boolean existsByToken(String token);
}
