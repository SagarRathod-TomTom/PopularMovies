/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */

package com.sagarrathod.popularmovies.fragments;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.support.v4.app.FragmentManager;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.sagarrathod.popularmovies.R;
import com.sagarrathod.popularmovies.activities.MovieDetailsActivity;
import com.sagarrathod.popularmovies.adapters.PosterAdapter;
import com.sagarrathod.popularmovies.beans.Movie;
import com.sagarrathod.popularmovies.data.net.MovieDataFetcher;
import com.sagarrathod.popularmovies.util.MoviesType;

/**
 * Sub class of {@link Fragment} to display movies in a grid view.
 *
 * @author Sagar Rathod
 * @version 1.0
 */
public class PosterGridFragment extends Fragment {

    public ListView mListView;
    private PosterAdapter mPosterAdapter;
    private Context mContext;
    private MovieDataFetcher mMovieDataFetcher;
    private MoviePosterClickListener mMoviePosterClickListener = new MoviePosterClickListener();
    private MoviesType mMoviesType;
    private boolean mMoviesTypeChanged = false;
    private int mCurrentMovieSelection = 0;
    public ProgressBar mProgressBar;

    public int getCurrentMovieSelection() {
        return mCurrentMovieSelection;
    }

    /**
     * Indicates that this fragment has a menu items.
     */
    public PosterGridFragment() {}

    /**
     * Constructs the poster adapter to hold the movies data.
     * Check the user preference either Popular Movie or Top-reated Movie.
     * Executes the {@link MovieDataFetcher} to fetch the movie data from https://api.themoviedb.org
     *
     * @param context The activity context.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        mPosterAdapter = new PosterAdapter(context);
    }

    /**
     * Inflates the fragment_poster_grid layout.
     * Sets the poster grid adapter to the grid view.
     * Registers the grid view item click listener.
     *
     * @param inflater
     * @param container          The parent view.
     * @param savedInstanceState The savedInstanceState for this fragment.
     * @return view The inflate view for this fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_poster_grid, container, false);

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_indicator);

        mListView = (ListView) rootView.findViewById(R.id.gridView);

        mListView.setAdapter(mPosterAdapter);

        mListView.setOnItemClickListener(mMoviePosterClickListener);

        return rootView;
    }

    /**
     * Checks weather user has changed the movie preference {@link MoviesType} for either popular or
     * top rated movies.
     * Get the selected movies from https://api.themoviedb.org
     */
    @Override
    public void onResume() {
        super.onResume();
        changeMovieList();
    }

    public void changeMovieList(){
        checkPreferenceValues();
        getMovies();
    }

    /**
     * If user preference for movie type is changed  {@link MovieDataFetcher},then it
     * executes {@link MovieDataFetcher}.
     */
    private void getMovies() {
        if (mMoviesTypeChanged) {
            //execute asynctask
            mMovieDataFetcher = new MovieDataFetcher(mContext, mPosterAdapter, mMoviesType,this);
            mMovieDataFetcher.execute();
            mMoviesTypeChanged = false;
        }
    }

    public void setCurrentMovieSelection(int mCurrentMovieSelection) {
        this.mCurrentMovieSelection = mCurrentMovieSelection;
    }

    /**
     * Inflates the setting menu layout.
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.settings_fragment, menu);
    }

    /**
     * Stores the state of the fragment.
     * In particular, stores the current selected movie.
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("movie_selection", mCurrentMovieSelection);
    }

    /**
     * Restores the state of the fragment.
     * In particular, restores the current selection movie.
     *
     * @param savedInstanceState
     */
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {

        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentMovieSelection = savedInstanceState.getInt("movie_selection");
            Movie movie = (Movie) mPosterAdapter.getItem(mCurrentMovieSelection);
            if (movie != null && isDeviceInLandscapeOrientation())
                showMovieDetails(movie);
        }
    }

    /**
     * Checks the user preference for either popular or top rated movies{@link MoviesType} from
     * default shared preference. Then indicates this change in boolean variable mMoviesTypeChanged.
     */
    private void checkPreferenceValues() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String movies = sharedPreferences.getString(getString(R.string.movies_type), MoviesType.POPULAR_MOVIES.toString());

        MoviesType newMoviesType = MoviesType.valueOf(movies);

        if (newMoviesType != this.mMoviesType) {
            this.mMoviesType = newMoviesType;
            mMoviesTypeChanged = true;
        }
    }

    /**
     * Checks weather the device is in landscape orientation.
     *
     * @return boolean Returns true if the device is in landscape orientation, otherwise false.
     */
    public boolean isDeviceInLandscapeOrientation() {

        int orientation = getResources().getConfiguration().orientation;

        return orientation == Configuration.ORIENTATION_LANDSCAPE ? true : false;
    }

    /**
     * Shows the movie details in the appropriate configuration of a device.
     * If the device is in landscape orientation, the movie details are shown in the right
     * fragment otherwise new activity is started to show the movie details.
     *
     * @param movie The details of movie of object to be shown.
     */

    public void showMovieDetails(Movie movie) {
        //if device is in landscape orientation
        if (isDeviceInLandscapeOrientation()) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            MovieDetailsFragment movieDetailsFragment;
            movieDetailsFragment = (MovieDetailsFragment) fragmentManager.findFragmentById(R.id.movie_details_fragment);
            movieDetailsFragment.setMovieDetails(movie);
        } else {
            Intent intent = new Intent(mContext, MovieDetailsActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        }
    }

    /**
     * Implements adapter view item events click listener
     */
    private class MoviePosterClickListener implements AdapterView.OnItemClickListener {

        /**
         * Handles the adapter view item click events when a particular movie poster is selected to
         * show its details.
         * Checks the orientation of a device if the device is in portrait orientation then
         * {@link MovieDetailsActivity} is started to show movie details, or if the device is in
         * landscape orientation then movie details are shown in {@link MovieDetailsFragment}.
         *
         * @param parent
         * @param view     The selected movie poster.
         * @param position The position of movie poster in adapter.
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Movie movie = (Movie) mPosterAdapter.getItem(position);

            // Create a ripple effect
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                int finalRadius = (int) Math.hypot(view.getWidth()/2,  view.getHeight()/2);
                Animator animator = ViewAnimationUtils.createCircularReveal(
                        view,
                        view.getWidth()/2, view.getHeight()/2,
                        0, finalRadius);
                animator.start();
            }
            if (movie != null) {

                setCurrentMovieSelection(position);
                showMovieDetails(movie);
            }
        }
    }

}

