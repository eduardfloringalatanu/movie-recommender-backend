package com.torm.movierecommender.security;

import com.torm.movierecommender.entity.RefreshTokenEntity;
import com.torm.movierecommender.entity.UserEntity;
import com.torm.movierecommender.exception.ErrorCode;
import com.torm.movierecommender.exception.ResponseStatusException2;
import com.torm.movierecommender.repository.RefreshTokenRepository;
import com.torm.movierecommender.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TokenService {
    private final JwtEncoder jwtEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public String generateAccessToken(String identifier) {
        Instant now = Instant.now();

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(identifier)
                .issuedAt(now)
                .expiresAt(now.plusMillis(accessTokenExpiration))
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, jwtClaimsSet))
                .getTokenValue();
    }

    public String hashRefreshToken(String refreshToken) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(refreshToken.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder()
                    .encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Failed to hash refresh token.", e);
        }
    }

    @Transactional
    public String generateRefreshToken(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException2(HttpStatus.UNAUTHORIZED, ErrorCode.USER_UNAUTHORIZED_ERROR));

        RefreshTokenEntity refreshToken = new RefreshTokenEntity();

        String token = UUID.randomUUID().toString();
        String hashedToken = hashRefreshToken(token);

        refreshToken.setToken(hashedToken);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiration));
        refreshToken.setUser(user);

        refreshTokenRepository.save(refreshToken);

        return token;
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteAllExpiredRefreshTokens() {
        refreshTokenRepository.deleteAllExpiredSince(Instant.now());
    }
}