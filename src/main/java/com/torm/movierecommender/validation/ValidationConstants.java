package com.torm.movierecommender.validation;

public final class ValidationConstants {
    private ValidationConstants() {}

    public static final int DIRECTORS_MAX_SIZE = 1500;
    public static final int EMAIL_MIN_SIZE = 6;
    public static final int EMAIL_MAX_SIZE = 320;
    public static final int IDENTIFIER_MAX_SIZE = EMAIL_MAX_SIZE;
    public static final int RELEASE_YEAR_MIN = 1888;
    public static final int MIN_RELEASE_YEAR_MIN = RELEASE_YEAR_MIN;
    public static final int MOVIE_ID_MIN = 1;
    public static final int PASSWORD_MIN_SIZE = 8;
    public static final int PASSWORD_MAX_SIZE = 64;
    public static final int PLOT_MAX_SIZE = 1000;
    public static final int RATING_MIN = 1;
    public static final int RATING_MAX = 10;
    public static final int TITLE_MAX_SIZE = 500;
    public static final int USERNAME_MIN_SIZE = 3;
    public static final int USERNAME_MAX_SIZE = 32;
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9](?:[._-]?[a-zA-Z0-9]+)*@[a-zA-Z0-9](?:[.-]?[a-zA-Z0-9]+)*\\.[a-zA-Z]{2,}$";
    public static final String PASSWORD_REGEX_1 = ".*[a-z].*";
    public static final String PASSWORD_REGEX_2 = ".*[A-Z].*";
    public static final String PASSWORD_REGEX_3 = ".*[0-9].*";
    public static final String PASSWORD_REGEX_4 = ".*[~!@#$%^&*()_+`\\-=\\[\\]\\\\{}|;':\",./<>?].*";
    public static final String PASSWORD_REGEX_5 = "^[a-zA-Z0-9~!@#$%^&*()_+`\\-=\\[\\]\\\\{}|;':\",./<>?]+$";
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9](?:[._-]?[a-zA-Z0-9]+)*$";
}