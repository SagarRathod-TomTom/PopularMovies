/**
 * Copyright (C) June 2016
 * The Popular Movies Stage I project
 */

package com.sagarrathod.popularmovies.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sagarrathod.popularmovies.R;
import com.sagarrathod.popularmovies.beans.Movie;
import com.sagarrathod.popularmovies.util.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the list of movies and acts as a data source for movies data.
 *
 * @author Sagar Rathod
 * @version 1.0
 */
public class PosterAdapter extends BaseAdapter {

    private List<Movie> mMovies;
    private Context mContext;
    private String mBasePosterPath = "http://image.tmdb.org/t/p/w185/";

    /**
     * Constructs an empty movie list.
     *
     * @param mContext
     */
    public PosterAdapter(Context mContext) {
        this.mContext = mContext;
        mMovies = new ArrayList<Movie>();
    }

    /**
     * Provides the total count of movies in the list.
     *
     * @return int The total number of movies in the list.
     */
    @Override
    public int getCount() {
        return mMovies.size();
    }

    /**
     * Provides the movie object of particular position in the list.
     *
     * @param position The position of movie object in the movie list.
     * @return int The movie object {@link Movie}.
     */
    @Override
    public Object getItem(int position) {
        try {
            return mMovies.get(position);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Provides the item id of movie object as a position in the list.
     *
     * @param position The position of movie object in the movie list.
     * @return int The id as a position of movie object {@link Movie} in the list.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Finds the movie object position within the movie list.
     *
     * @param movie
     * @return index of this movie object within a movie list.
     */

    public int findItemPosition(Movie movie) {
        for (int index = 0; index < mMovies.size(); index++) {
            if (movie.equals(mMovies.get(index))) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Constructs the poster_grid_item layout for a movie.
     *
     * @param position    The position of movie in the movie list.
     * @param convertView The view associated with this movie.
     * @param parent      The parent view.
     * @return view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.poster_grid_item, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.poster_grid_item);
        final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.image_loading_bar);
        progressBar.setVisibility(View.VISIBLE);

        Movie movie = (Movie) getItem(position);

        TextView textView = (TextView) convertView.findViewById(R.id.movie_title);
        textView.setText(movie.getOriginalTitle());

        TextView releaseDateTextView = (TextView) convertView.findViewById(R.id.release_date);
        releaseDateTextView.setText(Utils.formatReleaseDate(movie.getReleaseDate()));


        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.rating_bar);
        double rating = movie.getVoteAverage();
        float fillStars = (float)(rating / 10) * 5;
        ratingBar.setRating(fillStars);


        if (imageView != null && movie != null) {
            String posterPath = mBasePosterPath + movie.getPosterPath();

            // Log.v("Poster Path", posterPath);
            Picasso.with(mContext).load(posterPath)
                    .fit()
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        } else {
            //error has oocurred.
            //load error image.
            Picasso.with(mContext).load(R.mipmap.error_image).into(imageView);
        }

        return convertView;
    }

    /**
     * Sets the list of movies and notifies the adapter.
     *
     * @param mMovies The new list of movies.
     */
    public void setMovies(List<Movie> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

}
