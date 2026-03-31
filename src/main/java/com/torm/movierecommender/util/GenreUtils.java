package com.torm.movierecommender.util;

import java.util.Map;

public final class GenreUtils {
    public static final Map<String, String> GENRE_DICTIONARY = Map.ofEntries(
            Map.entry("action", "Action"),
            Map.entry("adventure", "Adventure"),
            Map.entry("comedy", "Comedy"),
            Map.entry("drama", "Drama"),
            Map.entry("horror", "Horror"),
            Map.entry("thriller", "Thriller"),
            Map.entry("romance", "Romance"),
            Map.entry("sci-fi", "Sci-Fi"),
            Map.entry("fantasy", "Fantasy"),
            Map.entry("crime", "Crime"),
            Map.entry("mystery", "Mystery"),
            Map.entry("animation", "Animation")
    );
}