package com.torm.movierecommender.security;

import com.torm.movierecommender.entities.RefreshTokenEntity;
import com.torm.movierecommender.entities.UserEntity;
import com.torm.movierecommender.repositories.RefreshTokenRepository;
import com.torm.movierecommender.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TokenService {
    private final SecretKey secretKey;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public String generateAccessToken(String identifier) {
        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(identifier)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(accessTokenExpiration)))
                .signWith(secretKey)
                .compact();
    }

    public String hashRefreshToken(String refreshToken) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(refreshToken.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing refresh token.", e);
        }
    }

    @Transactional
    public String generateRefreshToken(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        RefreshTokenEntity refreshToken = refreshTokenRepository.findByUser(user)
                .orElse(new RefreshTokenEntity());

        String token = UUID.randomUUID().toString();
        String hashedToken = hashRefreshToken(token);

        refreshToken.setToken(hashedToken);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiration));
        refreshToken.setUser(user);

        refreshTokenRepository.save(refreshToken);

        return token;
    }

    @Transactional
    public RefreshTokenEntity verifyExpirationDate(RefreshTokenEntity refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token is expired.");
        }

        return refreshToken;
    }
}