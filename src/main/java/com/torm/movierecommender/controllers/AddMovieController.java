package com.torm.movierecommender.controllers;

import com.torm.movierecommender.services.AddMovieService;
import com.torm.movierecommender.validation.MaxYear;
import com.torm.movierecommender.validation.ValidationGroupSequences.First;
import com.torm.movierecommender.validation.ValidationGroupSequences.Second;
import com.torm.movierecommender.validation.ValidationGroupSequences.Third;
import com.torm.movierecommender.validation.ValidationGroupSequences.Fourth;
import com.torm.movierecommender.validation.ValidationGroupSequences.Fifth;
import com.torm.movierecommender.validation.ValidationGroupSequences.Sixth;
import com.torm.movierecommender.validation.ValidationGroupSequences.Seventh;
import com.torm.movierecommender.validation.ValidationGroupSequences.ValidationGroupSequence1;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AddMovieController {
    private final AddMovieService addMovieService;

    public record AddMovieRequestBody(
            @NotBlank(message = "Title cannot be blank.", groups = First.class)
            String title,

            @NotNull(message = "Year cannot be null.", groups = Second.class)
            @Min(value = 1990, message = "Year cannot be less than 1990.", groups = Third.class)
            @MaxYear(message = "Year cannot be greater than current year.", groups = Fourth.class)
            Short year,

            @NotEmpty(message = "Genres cannot be empty.", groups = Fifth.class)
            List<@NotBlank(message = "Genres cannot contain blank items.", groups = Sixth.class) String> genres,

            @NotBlank(message = "Plot cannot be blank.", groups = Seventh.class)
            String plot) {}

    @PostMapping("/add_movie")
    public void addMovie(@RequestBody @Validated(ValidationGroupSequence1.class) AddMovieRequestBody addMovieRequestBody, @AuthenticationPrincipal Jwt jwt) {
        addMovieService.addMovie(addMovieRequestBody, jwt);
    }
}