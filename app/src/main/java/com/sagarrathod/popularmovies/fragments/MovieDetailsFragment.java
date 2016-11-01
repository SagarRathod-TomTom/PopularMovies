/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */
package com.sagarrathod.popularmovies.fragments;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.net.Uri;
import android.widget.Toast;

import com.sagarrathod.popularmovies.R;
import com.sagarrathod.popularmovies.activities.MovieDetailsActivity;
import com.sagarrathod.popularmovies.adapters.MovieTrailerAdapter;
import com.sagarrathod.popularmovies.adapters.UserReviewAdapter;
import com.sagarrathod.popularmovies.beans.Movie;
import com.sagarrathod.popularmovies.beans.MovieTrailer;
import com.sagarrathod.popularmovies.beans.UserReview;
import com.sagarrathod.popularmovies.data.contract.MovieContract;

import com.sagarrathod.popularmovies.util.Utils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Shows the movie details fragment for a particular movie.
 *
 * @author Sagar Rathod
 * @version 1.0
 */
public class MovieDetailsFragment extends Fragment {

    private final String TAG = "#Popular Movies";
    private MovieDetailsActivity mContext;
    private String mBasePosterPath = "http://image.tmdb.org/t/p/w185/";
    private MovieTrailerAdapter mMovieTrailerAdapter;
    private UserReviewAdapter mUserReviewAdapter;
    private MovieTrailerClickListener mMovieTrailerClickListener;
    private Movie mMovie;
    private ImageButton mFavoriteButton;
    private Uri baseUri = Uri.parse("http://www.youtube.com/watch");

    /**
     * An Empty constructor.
     */
    public MovieDetailsFragment() {
        setHasOptionsMenu(true);
    }

    /**
     * Holds the reference to context from fragments's activity to which fragment is attached.
     *
     * @param context The activity context.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (MovieDetailsActivity) context;
    }

    /**
     * Inflates the fragment_movie_details layout
     *
     * @param inflater           {@link LayoutInflater}
     * @param container          The parent view for this view
     * @param savedInstanceState
     * @return view An inflated layout
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    /**
     * Inflates the movie details menu.
     *
     * @param menu     The menu.
     * @param inflater the menu inflater.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_details_menu_fragment, menu);
    }

    /**
     * Handles the share movie trailer action.
     *
     * @param item the menu item.
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_share && mMovie != null) {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, createSharableData());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Intent chooser = Intent.createChooser(intent, "Share via:");

            if (chooser.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Creates movie trailer list in a single string.
     *
     * @return
     */
    private String createSharableData() {
        String data = "";
        final String URL = "http://www.youtube.com/watch?v=";
        if (mMovie != null) {
            data = "Movie:" + mMovie.getOriginalTitle() + "\n";
            for (MovieTrailer trailer : mMovie.getMovieTrailers()) {
                data += trailer.getName() + ": " + URL + trailer.getKey() + "\n";
            }
        }
        data += TAG;
        return data;
    }

    /**
     * Displays the movie details on views.
     *
     * @param movie The movie object.
     * @throws NullPointerException If movie object is null.
     */
    public void setMovieDetails(Movie movie) throws NullPointerException {

        if (movie == null)
            throw new NullPointerException();

        this.mMovie = movie;

        View view = getView();

        mContext.mToolbar.setTitle(movie.getOriginalTitle());

        Picasso.with(mContext).load(mBasePosterPath + movie.getPosterPath())
                .fit()
                .into(mContext.mImageThumbnail);

        TextView overview = (TextView) view.findViewById(R.id.overview);
        overview.setText(String.format(" %s ", movie.getOverview()));

        TextView userRating = (TextView) view.findViewById(R.id.user_rating);
        userRating.setText(movie.getVoteAverage() + "/10");

        TextView releaseDate = (TextView) view.findViewById(R.id.release_date);

        releaseDate.setText(Utils.formatReleaseDate(movie.getReleaseDate()));

        mMovieTrailerAdapter = new MovieTrailerAdapter(mContext);
        mUserReviewAdapter = new UserReviewAdapter(mContext);

        ListView trailerListView = (ListView) view.findViewById(R.id.video_grid_view);

        List<MovieTrailer> movieTrailers = movie.getMovieTrailers();

        if (movieTrailers != null) {
            mMovieTrailerAdapter.setMovieTrailers(movieTrailers);
        }

        trailerListView.setAdapter(mMovieTrailerAdapter);
        mMovieTrailerClickListener = new MovieTrailerClickListener();
        trailerListView.setOnItemClickListener(mMovieTrailerClickListener);

        ListView userReviewListVeiw = (ListView) view.findViewById(R.id.user_review_list_view);

        List<UserReview> userReviews = movie.getUserReviews();

        if (userReviews != null) {
            mUserReviewAdapter.setUserReviews(userReviews);
        }

        userReviewListVeiw.setAdapter(mUserReviewAdapter);

        mFavoriteButton = (ImageButton) view.findViewById(R.id.favorite);
        FavoriteMovieClickListener favoriteMovieClickListener = new FavoriteMovieClickListener();
        mFavoriteButton.setOnClickListener(favoriteMovieClickListener);

        mMovie.setFavoriteMovie(isFavoriteMovie(mMovie.getMovieId()));
        markAsFavorite();
    }

    /**
     * Checks whether the movie is favorite movie or not by querying to content provider.
     *
     * @param movieId The id of the movie.
     * @return
     */

    boolean isFavoriteMovie(long movieId) {

        String projection[] = {"_id"}, selection = null, selectionArg[] = null, sortOrder = null;

        Cursor cursor = getContext().getContentResolver().query(
                MovieContract.MovieEntry.buildFavoriteMovieWithId(movieId),
                projection,
                selection,
                selectionArg,
                sortOrder
        );

        return cursor.moveToNext();
    }

    /**
     * Highlights the star button if movie is favorite movie of user.
     */
    public void markAsFavorite() {
        if (mMovie.isFavoriteMovie())
            mFavoriteButton.setImageResource(R.mipmap.on);
        else
            mFavoriteButton.setImageResource(R.mipmap.off);
    }

    /**
     * The movie trailer click listener.
     */
    private class MovieTrailerClickListener implements AdapterView.OnItemClickListener {

        /**
         * Creates an implicit intent to play movie trailer.
         *
         * @param parent   the parent view.
         * @param view     the view associated with the item.
         * @param position the position of movie in the list.
         * @param id
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            MovieTrailer movieTrailer = (MovieTrailer) mMovieTrailerAdapter.getItem(position);

            Uri builtUri = baseUri.buildUpon().appendQueryParameter("v", movieTrailer.getKey())
                    .build();

            Intent intent = new Intent(Intent.ACTION_VIEW, builtUri);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            Intent intentChooser = Intent.createChooser(intent, "Play video via");

            if (intentChooser.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            }

        }
    }

    /**
     * Handler class if movie is marked as favorite.
     * Stores the movie in favorite_movies table in sqlite.
     */
    private class FavoriteMovieClickListener implements AdapterView.OnClickListener {

        /**
         * Creates an asynchronous thread to deal with database operation.
         *
         * @param v The image button view.
         */
        @Override
        public void onClick(View v) {
            mMovie.setFavoriteMovie(!mMovie.isFavoriteMovie());
            WriterThread writerThread = new WriterThread();
            writerThread.execute();
        }
    }

    /**
     * Extends the @link{AsyncTask} to run database queries in background thread.
     */
    private class WriterThread extends AsyncTask<Void, Void, Long> {
        private boolean mDeletion;

        /**
         * If movie is favorite movie user, it is deleted from the favorite_movies table
         * otherwise it is written to the database.
         *
         * @param params
         * @return
         */
        @Override
        protected Long doInBackground(Void... params) {

            ContentResolver contentResolver = getContext().getContentResolver();

            long id = 0;
            if (mMovie.isFavoriteMovie()) {

                //insert this movie to the database
                ContentValues contentValues = new ContentValues();
                //Log.e("Movie id:", String.valueOf(mMovie.getMovieId()));
                contentValues.put(MovieContract.MovieEntry._ID, mMovie.getMovieId());
                contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, mMovie.getOriginalTitle());
                contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, mMovie.getReleaseDate());
                contentValues.put(MovieContract.MovieEntry.COLUMN_RATING, mMovie.getVoteAverage());
                contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, mMovie.getOverview());
                contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, mMovie.getPosterPath());

                Uri uri = contentResolver.insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
                id = uri != null ? mMovie.getMovieId() : -1;

            } else {
                //delete this movie from the database
                id = contentResolver.delete(MovieContract.MovieEntry.buildFavoriteMovieWithId(
                        mMovie.getMovieId()),
                        null,
                        null);
                mDeletion = true;
            }
            return id;

        }

        /**
         * Notifies the user with the selected operation.
         *
         * @param rowId
         */

        @Override
        protected void onPostExecute(Long rowId) {
            // enable or disable star button
            markAsFavorite();

            if (!mDeletion) {
                if (rowId != -1) {

                    Toast.makeText(mContext, mMovie.getOriginalTitle() + " marked as favorite Movie."
                            , Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(mContext, "Unable to mark as favorite."
                            , Toast.LENGTH_SHORT).show();

                }
            } else {
                if (rowId > 0) {
                    Toast.makeText(mContext, mMovie.getOriginalTitle() + " is not longer your" +
                                    "favorite movie."
                            , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, mMovie.getOriginalTitle() + " can't be deleted."
                            , Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

}


