/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */

package com.sagarrathod.popularmovies.data.net;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.sagarrathod.popularmovies.R;
import com.sagarrathod.popularmovies.adapters.PosterAdapter;
import com.sagarrathod.popularmovies.beans.Movie;
import com.sagarrathod.popularmovies.data.contract.MovieContract;
import com.sagarrathod.popularmovies.fragments.PosterGridFragment;
import com.sagarrathod.popularmovies.util.DataFetcher;
import com.sagarrathod.popularmovies.util.MovieDataParser;
import com.sagarrathod.popularmovies.util.MoviesType;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the {@link AsyncTask} to fetch the movie data from https://api.themoviedb.org site.
 *
 * @author Sagar Rathod
 * @version 1.0
 */
public class MovieDataFetcher extends AsyncTask<Void, Void, List<Movie>> {

    private String mBaseUrl = "http://api.themoviedb.org/3/movie";
    private MoviesType mMoviesType;
    private String mMovieData;
    private Context mContext;
    private PosterAdapter mPosterAdapter;
    private Fragment mFragment;
    private String mApiKey;

    /**
     * Constructs the MovieDataFetcher to fetch movies data for {@link MoviesType}.
     *
     * @param context       The context for app environment
     * @param posterAdapter The adapter {@link PosterAdapter} as a data source to store data.
     * @param moviesType    The type of movies data to fetch from https://api.themoviedb.org site.
     */
    public MovieDataFetcher(Context context, PosterAdapter posterAdapter, MoviesType moviesType, Fragment fragment) {
        this.mContext = context;
        this.mPosterAdapter = posterAdapter;
        this.mMoviesType = moviesType;
        this.mFragment = fragment;
        mApiKey = mContext.getString(R.string.api_key);
    }

    /**
     * Runs in background to fetch movie data.
     *
     * @param params
     * @return list<Movie> The ordered list of movies.
     */
    @Override
    protected List<Movie> doInBackground(Void... params) {

        List<Movie> movies = new ArrayList<Movie>();

        try {

            if (mMoviesType.equals(MoviesType.FAVORITE_MOVIES)) {
                String projection[] = null;
                String selection = null;
                String selectionArguments[] = null;
                String sortOrder = null;

                Cursor cursor = mContext.getContentResolver()
                        .query(MovieContract.MovieEntry.CONTENT_URI,
                                projection,
                                selection,
                                selectionArguments,
                                sortOrder);

                while (cursor.moveToNext()) {
                    Movie movie = new Movie();
                    movie.setMovieId(cursor.getInt(0));
                    movie.setOriginalTitle(cursor.getString(1));
                    movie.setReleaseDate(cursor.getString(2));
                    movie.setVoteAverage(cursor.getDouble(3));
                    movie.setOverview(cursor.getString(4));
                    movie.setPosterPath(cursor.getString(5));
                    movie.setFavoriteMovie(true);
                    movies.add(movie);
                }
            } else {
                Uri builtUri = Uri.parse(mBaseUrl).buildUpon()
                        .appendPath(mMoviesType.getPath())
                        .appendQueryParameter("api_key", mApiKey)
                        .build();


                mMovieData = DataFetcher.fetchData(builtUri);

                if (mMovieData != null) {
                    // parse the JSON data
                    movies = MovieDataParser.parseMovieData(mMovieData);
                }
            }

        } catch (JSONException e) {
            Log.e("MovieDataFetcher", "Cannot parse movie data.", e);
        }

        Log.i("Movies:",movies + "");

        return movies;
    }

    /**
     * Executes immediately after fetch movie data background task.
     * Notifies the adapter {@link PosterAdapter}.
     *
     * @param movies
     */
    @Override
    protected void onPostExecute(List<Movie> movies) {

        if (movies != null) {
            mPosterAdapter.setMovies(movies);

            MovieTrailersFetcher movieTrailersFetcher = new MovieTrailersFetcher(mApiKey);
            movieTrailersFetcher.execute(movies);

            MovieReviewFetcher movieReviewFetcher = new MovieReviewFetcher(mApiKey);
            movieReviewFetcher.execute(movies);
        } else {
            List<Movie> list = new ArrayList<Movie>();
            list.add(null);
            mPosterAdapter.setMovies(list);
        }

        PosterGridFragment posterGridFragment = (PosterGridFragment) mFragment;

        if (posterGridFragment.isDeviceInLandscapeOrientation())
            posterGridFragment.showMovieDetails((Movie) mPosterAdapter.getItem(posterGridFragment.getCurrentMovieSelection()));
    }

}
