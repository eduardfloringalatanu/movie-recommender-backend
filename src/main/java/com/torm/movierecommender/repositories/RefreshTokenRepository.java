package com.torm.movierecommender.repositories;

import com.torm.movierecommender.entities.RefreshTokenEntity;
import com.torm.movierecommender.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
    Optional<RefreshTokenEntity> findByUser(UserEntity user);
    void deleteByUser(UserEntity user);
}