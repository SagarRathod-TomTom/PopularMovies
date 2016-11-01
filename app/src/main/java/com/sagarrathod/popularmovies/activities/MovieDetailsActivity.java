/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */

package com.sagarrathod.popularmovies.activities;

import android.media.Image;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.sagarrathod.popularmovies.R;
import com.sagarrathod.popularmovies.beans.Movie;
import com.sagarrathod.popularmovies.fragments.MovieDetailsFragment;


/**
 * Shows the movie details for a particular movie when its poster is on clicked.
 * @author Sagar Rathod
 * @version 1.0
 */

public class MovieDetailsActivity extends AppCompatActivity {

    private MovieDetailsFragment mMovieDetailsFragment;
    public CollapsingToolbarLayout mToolbar;
    public ImageView mImageThumbnail;
    /**
     * Sets activity_movie_details layout.
     * Obtains the reference to {@link MovieDetailsFragment} from received intent to initialize the  movie details
     * in its view.
     *
     * @param savedInstanceState The saved instance state of this activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mImageThumbnail = (ImageView) findViewById(R.id.poster_thumbnail);

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        mMovieDetailsFragment = (MovieDetailsFragment)
                fragmentManager.findFragmentById(R.id.movie_details_fragment);

        Intent intent = getIntent();
        if (intent != null) {

            Movie movie = (Movie) intent.getParcelableExtra("movie");

            mMovieDetailsFragment.setMovieDetails(movie);

        }
    }

}

