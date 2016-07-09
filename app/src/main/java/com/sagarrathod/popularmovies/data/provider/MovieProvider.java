/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */

package com.sagarrathod.popularmovies.data.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import android.support.annotation.Nullable;

import com.sagarrathod.popularmovies.data.contract.MovieContract;
import com.sagarrathod.popularmovies.data.db.MovieDBHelper;

/**
 * The movie provider class to provide managed access to movie database.
 *
 * @author Sagar Rathod
 * @version 1.0
 */

public class MovieProvider extends ContentProvider {

    //Uri's constants
    public static final int FAVORITE_MOVIES = 1;
    public static final int FAVORITE_MOVIES_DETAILS = 2;
    private static UriMatcher uriMatcher = buildUriMatcher();
    private SQLiteOpenHelper mSqLiteOpenHelper;


    /**
     * Creates an instance of Uri matcher to match known Uri's.
     *
     * @return @link{UriMatcher}
     */
    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_FAVORITE_MOVIES, FAVORITE_MOVIES);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_FAVORITE_MOVIES + "/#", FAVORITE_MOVIES_DETAILS);

        return uriMatcher;
    }

    /**
     * Create an instance of movie db helper.
     *
     * @return
     */
    @Override
    public boolean onCreate() {
        mSqLiteOpenHelper = new MovieDBHelper(getContext());
        return true;
    }

    /**
     * To retrive the movie data from the favorite movie table.
     *
     * @param uri           The content uri of movie. It determines whether to retrive all rows from the
     *                      table or particular row specified by movie id.
     *                      To fetch all movie content URI is:
     *                      "content://com.sagarrathod.popularmovies/favorite_movies"
     *                      and
     *                      To fetch movie details specified by movie id in the URI. For eg,
     *                      content://com.sagarrathod.popularmovies/favorite_movies/{movie_id}"
     * @param projection    The columns to be retrive.
     * @param selection     The selection condition.
     * @param selectionArgs The selection argument.
     * @param sortOrder     The sort order.
     * @return
     */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor = null;

        SQLiteDatabase sqLiteDatabase = mSqLiteOpenHelper.getReadableDatabase();

        switch (uriMatcher.match(uri)) {

            case FAVORITE_MOVIES:

                cursor = sqLiteDatabase.query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case FAVORITE_MOVIES_DETAILS:
                cursor = sqLiteDatabase.query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        MovieContract.MovieEntry._ID + "=?",
                        new String[]{uri.getLastPathSegment()},
                        null,
                        null,
                        null,
                        null);
                break;

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    /**
     * Determines the type of uri.
     *
     * @param uri The uri to be matched.
     * @return The string indicating single item or multiple items.
     */
    @Nullable
    @Override
    public String getType(Uri uri) {

        int match = uriMatcher.match(uri);

        switch (match) {
            case FAVORITE_MOVIES:
                return MovieContract.MovieEntry.CONTENT_TYPE;

            case FAVORITE_MOVIES_DETAILS:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown Uri:" + uri);
        }

    }

    /**
     * Inserts the movie details into favorite movie table.
     *
     * @param uri    The uri.
     * @param values The movie details.
     * @return
     */
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int match = uriMatcher.match(uri);

        SQLiteDatabase sqLiteDatabase = mSqLiteOpenHelper.getWritableDatabase();
        Uri returnUri = null;
        switch (match) {

            case FAVORITE_MOVIES:
                long _id = sqLiteDatabase.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = MovieContract.MovieEntry.buildFavoriteMovieWithId(_id);
                } else
                    throw new SQLException("Unable to insert data.");
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri:" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    /**
     * Deletes the movie from the favorite_movies table.
     *
     * @param uri The uri.
     * @param selection  The selection condition.
     * @param selectionArgs The selection argument.
     * @return
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int match = uriMatcher.match(uri);
        SQLiteDatabase database = mSqLiteOpenHelper.getWritableDatabase();
        int result = -1;
        switch (match) {

            case FAVORITE_MOVIES_DETAILS:

                result = database.delete(MovieContract.MovieEntry.TABLE_NAME,
                        MovieContract.MovieEntry._ID + "=?",
                        new String[]{uri.getLastPathSegment()});
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri:" + uri);

        }

        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
