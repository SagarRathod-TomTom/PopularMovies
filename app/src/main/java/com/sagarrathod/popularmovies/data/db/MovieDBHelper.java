/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */
package com.sagarrathod.popularmovies.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sagarrathod.popularmovies.data.contract.MovieContract;

/**
 * The movie database helper class.
 *
 * @author Sagar Rathod
 * @version 1.0
 *
 */
public class MovieDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION  = 1;
    private static final String DATABASE_NAME = "movie.db";

    /**
     * Creates an instance of movie db helper.
     *
     * @param context the application context.
     */
    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    /**
     * Creates a favorite movie table in movie database.
     * @param db an instance of database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MOVIE_TABLE= "create table " + MovieContract.MovieEntry.TABLE_NAME
                + "(" + MovieContract.MovieEntry._ID + " INTEGER NOT NULL," +
                MovieContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL," +
                MovieContract.MovieEntry.COLUMN_RATING + " REAL NOT NULL," +
                MovieContract.MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL," +
                MovieContract.MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL," +
                "PRIMARY KEY(_id));";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);

    }

    /**
     * Upgrades the version of database if database version changed.
     *
     * @param db db an instance of database.
     * @param oldVersion   the old version of database.
     * @param newVersion   the new version of database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
