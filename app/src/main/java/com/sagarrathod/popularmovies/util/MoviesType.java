/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */
package com.sagarrathod.popularmovies.util;

/**
 * The enum constant class.
 *
 * @author Sagar Rathod
 * @version 1.0
 */
public enum MoviesType {

    POPULAR_MOVIES("popular"), TOP_RATED_MOVIES("top_rated"), FAVORITE_MOVIES("favorite_movies");

    MoviesType(String path) {
        this.path = path;
    }

    private String path;

    public String getPath() {
        return path;
    }
}
