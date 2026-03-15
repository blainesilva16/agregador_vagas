package com.devnotfound.talenthub.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.devnotfound.talenthub.entity.Token;
import com.devnotfound.talenthub.repository.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TokenService {

	 /** expiration in minutes */
    @Value("${app.jwt.expiration}")
    private long expiration;
    
	@Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.issuer}")
    private String issuer;

    private final TokenRepository tokenRepository;

    @Transactional
    public String generateToken(String subject, String role) {
        Instant expirationDate = getExpirationDate();

        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withIssuer(issuer)
                .withSubject(subject) 
                .withClaim("role", role)
                .withExpiresAt(expirationDate)
                .sign(algorithm);

        Token tokenEntity = new Token();
        tokenEntity.setToken(token);
        tokenRepository.save(tokenEntity);

        return token;
    }

    public DecodedJWT verifyToken(String token) throws JWTVerificationException {
        if (!tokenRepository.existsByToken(token)) {
            throw new JWTVerificationException("Invalid token.");
        }

        Algorithm algorithm = Algorithm.HMAC256(secret);
        var verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();

        return verifier.verify(token);
    }

    private Instant getExpirationDate() {
        return Instant.now().plusSeconds(expiration * 60);
    }
}