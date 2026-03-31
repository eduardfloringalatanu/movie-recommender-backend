package com.torm.movierecommender.repository;

import com.torm.movierecommender.entity.RefreshTokenEntity;
import com.torm.movierecommender.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);

    @Modifying
    @Query("DELETE FROM RefreshTokenEntity WHERE expiryDate < :now")
    void deleteAllExpiredSince(Instant now);

    @Modifying
    void deleteByUser(UserEntity user);
}