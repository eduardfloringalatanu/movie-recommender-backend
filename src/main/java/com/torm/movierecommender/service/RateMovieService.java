package com.torm.movierecommender.service;

import com.torm.movierecommender.dto.RateMovieRequestDto;
import com.torm.movierecommender.entity.MovieEntity;
import com.torm.movierecommender.entity.UserEntity;
import com.torm.movierecommender.exception.ErrorCode;
import com.torm.movierecommender.exception.ResponseStatusException2;
import com.torm.movierecommender.repository.MovieRepository;
import com.torm.movierecommender.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateMovieService {
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Transactional
    public void rateMovie(Long movieId, RateMovieRequestDto rateMovieRequestDto, Jwt jwt) {
        String username = jwt.getSubject();

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResponseStatusException2(HttpStatus.UNAUTHORIZED, ErrorCode.USER_UNAUTHORIZED_ERROR));

        MovieEntity movie = movieRepository.findByMovieIdAndUser(movieId, user)
                .orElseThrow(() ->
                        new ResponseStatusException2(HttpStatus.NOT_FOUND, ErrorCode.MOVIE_NOT_FOUND_ERROR));

        movie.setRating(rateMovieRequestDto.rating());

        movieRepository.save(movie);
    }
}