/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */
package com.sagarrathod.popularmovies.util;

import com.sagarrathod.popularmovies.beans.Movie;
import com.sagarrathod.popularmovies.beans.MovieTrailer;
import com.sagarrathod.popularmovies.beans.UserReview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Does the JSON parsing for movie data.
 *
 * @author Sagar Rathod
 * @version 1.0
 */
public class MovieDataParser {

    /**
     * Parse the JSON movie data.
     *
     * @param data The movie data in JSON format.
     * @return List<Movies> The parsed movie list.
     */
    public static List<Movie> parseMovieData(String data) throws JSONException {

        if(data == null)
            throw new NullPointerException();

        List<Movie> movies = null;

        JSONObject jsonObject = new JSONObject(data);

        JSONArray moviesArray = jsonObject.getJSONArray("results");

        movies = new ArrayList<Movie>();

        for (int index = 0; index < moviesArray.length(); index++) {
            JSONObject jMovie = moviesArray.getJSONObject(index);

            Movie movie = new Movie();
            movie.setMovieId(jMovie.getInt("id"));
            movie.setOriginalTitle(jMovie.getString("original_title"));
            movie.setPosterPath(jMovie.getString("poster_path"));
            movie.setReleaseDate(jMovie.getString("release_date"));
            movie.setOverview(jMovie.getString("overview"));
            movie.setPopularity(jMovie.getDouble("popularity"));
            movie.setVoteAverage(jMovie.getDouble("vote_average"));

            movies.add(movie);
        }

        return movies;
    }


    /**
     * Parse the JSON movie trailer data.
     *
     * @param data The movie trailer data in JSON format.
     * @return List<MovieTrailer> The parsed movie trailer list.
     */
    public static List<MovieTrailer> parseMovieTrailerData(String data) throws JSONException{

        if(data == null)
            throw new NullPointerException();

        List<MovieTrailer> movieTrailers = new ArrayList<MovieTrailer>();

        JSONObject jsonObject = new JSONObject(data);

        JSONArray trailers = jsonObject.getJSONArray("results");

        for (int index = 0; index < trailers.length(); index++) {

            JSONObject jMovieTrailer = trailers.getJSONObject(index);

            MovieTrailer movieTrailer = new MovieTrailer();

            movieTrailer.setKey(jMovieTrailer.getString("key"));
            movieTrailer.setName(jMovieTrailer.getString("name"));

            movieTrailers.add(movieTrailer);
        }

        return movieTrailers;
    }

    /**
     * Parse the JSON movie user review comments.
     *
     * @param data The user review comments in JSON format.
     * @return List<UserReview> The parsed user review comments about movie.
     */

    public static List<UserReview> parseUserReviewData(String data) throws JSONException{

        if(data == null)
            throw new NullPointerException();

        List<UserReview> userReviews = new ArrayList<UserReview>();

        JSONObject jsonObject = new JSONObject(data);

        JSONArray trailers = jsonObject.getJSONArray("results");

        for (int index = 0; index < trailers.length(); index++) {

            JSONObject jUserReviews = trailers.getJSONObject(index);

            UserReview userReview = new UserReview();

            userReview.setAuthor(jUserReviews.getString("author"));
            userReview.setContent(jUserReviews.getString("content"));

            userReviews.add(userReview);
        }

        return userReviews;
    }

}
