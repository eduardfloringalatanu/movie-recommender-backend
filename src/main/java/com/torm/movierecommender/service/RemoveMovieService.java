package com.torm.movierecommender.service;

import com.torm.movierecommender.entity.MovieEntity;
import com.torm.movierecommender.entity.UserEntity;
import com.torm.movierecommender.repository.MovieRepository;
import com.torm.movierecommender.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RemoveMovieService {
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Transactional
    public void removeMovie(Long movieId, Jwt jwt) {
        String username = jwt.getSubject();

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "USER_UNAUTHORIZED_ERROR"));

        MovieEntity movie = movieRepository.findByMovieIdAndUser(movieId, user)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "MOVIE_NOT_FOUND_ERROR"));

        movieRepository.delete(movie);
    }
}