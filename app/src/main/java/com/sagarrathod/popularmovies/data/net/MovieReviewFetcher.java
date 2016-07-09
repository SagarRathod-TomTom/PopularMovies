/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */

package com.sagarrathod.popularmovies.data.net;

import android.net.Uri;
import android.os.AsyncTask;

import com.sagarrathod.popularmovies.beans.Movie;
import com.sagarrathod.popularmovies.beans.UserReview;
import com.sagarrathod.popularmovies.util.DataFetcher;
import com.sagarrathod.popularmovies.util.MovieDataParser;

import org.json.JSONException;

import java.util.List;


/**
 * Implements the {@link AsyncTask} to fetch the user review comments from
 * https://api.themoviedb.org site.
 *
 * @author Sagar Rathod
 * @version 1.0
 */
public class MovieReviewFetcher extends AsyncTask<List<Movie>,Void,Void> {

    private String mBaseUrl = "http://api.themoviedb.org/3/movie";
    private String mApiKey;

    /**
     * Constructs the MovieReviewFetcher to fetch user's review comments about movie.
     *
     * @param mApiKey   The api key of https://api.themoviedb.org.
     *
     */
    public MovieReviewFetcher(String mApiKey) {
        this.mApiKey = mApiKey;
    }

    /**
     * Runs in background to fetch user reviews for each movie in the list.
     *
     * @param params
     * @return list<Movie> The ordered list of movies.
     */
    @Override
    protected Void doInBackground(List<Movie>... params) {

        List<UserReview> userReviews;
        String data;

        for ( Movie movie: params[0]) {

            Uri builtUri = Uri.parse(mBaseUrl).buildUpon()
                    .appendPath(movie.getMovieId() + "")
                    .appendPath("reviews")
                    .appendQueryParameter("api_key",mApiKey)
                    .build();

            data = DataFetcher.fetchData(builtUri);

            try {
                userReviews = MovieDataParser.parseUserReviewData(data);
            } catch (JSONException e) {
               userReviews = null;
            }
            movie.setUserReviews(userReviews);
        }
        return null;
    }
}