/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */

package com.sagarrathod.popularmovies.data.contract;

import android.content.ContentResolver;
import android.provider.BaseColumns;
import android.net.Uri;

/**
 * The contract class of movie to store the data in the data source such as sqlite.
 * It acts an agreement between data model and presentation view.
 *
 * @author Sagar Rathod
 * @version 1.0
 */
public class MovieContract {

    /**
     * Content Authority string.
     */
    public static final String CONTENT_AUTHORITY = "com.sagarrathod.popularmovies";

    /**
     * Base content uri.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Favorite movie path segment.
     */
    public static final String PATH_FAVORITE_MOVIES = "favorite_movies";

    /**
     * Represents a single entity/entry of movie object in the table.
     */
    public static final class MovieEntry implements BaseColumns {

        /**
         * Content URI to access movie table.
         */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVORITE_MOVIES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +
                        PATH_FAVORITE_MOVIES;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +
                        PATH_FAVORITE_MOVIES;

        public static final String TABLE_NAME = "Favorite_movie";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_POSTER_PATH = "poster_path";

        /**
         * Constructs a Uri of particular movie object in the table.
         *
         * @param id the movie id.
         * @return Uri with movie id.
         */
        public static Uri buildFavoriteMovieWithId(long id) {
            Uri builtUri = CONTENT_URI.buildUpon().appendPath(id + "").build();
            return builtUri;
        }

    }


}
