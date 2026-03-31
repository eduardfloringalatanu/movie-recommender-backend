package com.torm.movierecommender.service;

import com.torm.movierecommender.dto.AddMovieRequestDto;
import com.torm.movierecommender.entity.MovieEntity;
import com.torm.movierecommender.entity.UserEntity;
import com.torm.movierecommender.repository.MovieRepository;
import com.torm.movierecommender.repository.UserRepository;
import com.torm.movierecommender.util.GenreUtils;
import com.torm.movierecommender.util.JsonUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddMovieService {
    private final UserRepository userRepository;
    private final EmbeddingModel embeddingModel;
    private final MovieRepository movieRepository;

    @Transactional
    public void addMovie(AddMovieRequestDto addMovieRequestDto, Jwt jwt) {
        String username = jwt.getSubject();

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "USER_UNAUTHORIZED_ERROR"));

        String title = addMovieRequestDto.title()
                .strip()
                .replaceAll("\\s+", " ");

        Short releaseYear = addMovieRequestDto.releaseYear();

        String directors = addMovieRequestDto.directors()
                .stream()
                .map(String::strip)
                .map(director -> director.replaceAll("\\s+", " "))
                .sorted()
                .collect(Collectors.joining(","));

        if (movieRepository.existsByTitleAndReleaseYearAndDirectorsAndUser(title, releaseYear, directors, user))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "MOVIE_CONFLICT_ERROR");

        String genres = addMovieRequestDto.genres()
                .stream()
                .map(String::strip)
                .map(String::toLowerCase)
                .map(GenreUtils.GENRE_DICTIONARY::get)
                .sorted()
                .collect(Collectors.joining(","));

        String plot = addMovieRequestDto.plot()
                .strip();

        MovieEntity movie = new MovieEntity();
        movie.setTitle(title);
        movie.setReleaseYear(releaseYear);
        movie.setDirectors(directors);
        movie.setGenres(genres);
        movie.setPlot(plot);

        float[] embeddings = embeddingModel.embed(plot);
        movie.setEmbeddings(JsonUtils.toJson(embeddings));

        movie.setRating(null);
        movie.setUser(user);

        movieRepository.save(movie);
    }
}