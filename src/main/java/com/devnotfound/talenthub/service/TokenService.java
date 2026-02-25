package com.devnotfound.talenthub.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.devnotfound.talenthub.entity.Token;
import com.devnotfound.talenthub.repository.TokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${spring.secret}")
    private String secret;

    @Value("${spring.expiration}")
    private long expiration;

    @Value("${spring.emissor}")
    private String emissor;

    @Autowired
    private TokenRepository tokenRepository;

    @Transactional
    public String gerarToken(String subject) {
        var dataExpiracao = this.getDataExpiracao();

        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withIssuer(emissor)
                .withSubject(subject)
                .withExpiresAt(dataExpiracao)
                .sign(algorithm);

        Token tokenEntity = new Token();
        tokenEntity.setToken(token);
        tokenRepository.save(tokenEntity);

        return token;
    }

    public DecodedJWT verificarToken(String token) throws JWTVerificationException {
        if (!tokenRepository.existsByToken(token)) {
            throw new JWTVerificationException("Token inválido!");
        }

        Algorithm algorithm = Algorithm.HMAC256(secret);
        com.auth0.jwt.JWTVerifier verifier = JWT.require(algorithm).withIssuer(emissor).build();
        return verifier.verify(token);
    }

    private Instant getDataExpiracao() {
        return LocalDateTime.now().plusMinutes(expiration).toInstant(ZoneOffset.of("-03:00"));
    }
}
