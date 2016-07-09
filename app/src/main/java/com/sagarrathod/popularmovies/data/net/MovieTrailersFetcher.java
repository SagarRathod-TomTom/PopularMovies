/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */

package com.sagarrathod.popularmovies.data.net;

import android.net.Uri;
import android.os.AsyncTask;

import com.sagarrathod.popularmovies.beans.Movie;
import com.sagarrathod.popularmovies.beans.MovieTrailer;
import com.sagarrathod.popularmovies.util.DataFetcher;
import com.sagarrathod.popularmovies.util.MovieDataParser;

import org.json.JSONException;

import java.util.List;

/**
 * Implements the {@link AsyncTask} to fetch the movie trailer data from https://api.themoviedb.org
 * site.
 *
 * @author Sagar Rathod
 * @version 1.0
 */

public class MovieTrailersFetcher extends AsyncTask<List<Movie>, Void, Void> {

    private String mBaseUrl = "http://api.themoviedb.org/3/movie";
    private String mApiKey;

    /**
     * Constructs the MovieTrailersFetcher to fetch movies trailer.
     *
     * @param mApiKey   The api key of https://api.themoviedb.org.
     *
     */
    public MovieTrailersFetcher(String mApiKey){
        this.mApiKey = mApiKey;
    }

    /**
     * Runs in background to fetch movie trailers for each movie in the list.
     *
     * @param params
     * @return list<Movie> The ordered list of movies.
     */

    @Override
    protected Void doInBackground(List<Movie>... params) {

        List<MovieTrailer> movieTrailers;
        String data;

            for ( Movie movie: params[0]) {

                Uri builtUri = Uri.parse(mBaseUrl).buildUpon()
                        .appendPath(movie.getMovieId() + "")
                        .appendPath("videos")
                        .appendQueryParameter("api_key",mApiKey)
                        .build();

                data = DataFetcher.fetchData(builtUri);

                try {
                    movieTrailers = MovieDataParser.parseMovieTrailerData(data);
                } catch (JSONException e) {
                    movieTrailers = null;
                }
                movie.setMovieTrailers(movieTrailers);
            }
        return null;
    }
}
