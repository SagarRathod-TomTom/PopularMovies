/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */

package com.sagarrathod.popularmovies.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sagarrathod.popularmovies.R;
import com.sagarrathod.popularmovies.beans.MovieTrailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for movie trailer list view.
 *
 * @author Sagar Rathod
 * @version 1.0
 */
public class MovieTrailerAdapter extends BaseAdapter {

    private Context mContext;
    private List<MovieTrailer> movieTrailers = new ArrayList<MovieTrailer>();
    private Uri uri = Uri.parse("https://img.youtube.com/vi/");

    /**
     * Creates an instance of movie trailer adapter.
     *
     * @param mContext the application context
     */
    public MovieTrailerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Creates an instance of movie trailer adapter.
     *
     * @param mContext      the application context
     * @param movieTrailers list of movie trailer object
     */
    public MovieTrailerAdapter(Context mContext, List<MovieTrailer> movieTrailers) {
        this.mContext = mContext;
        this.movieTrailers = movieTrailers;
    }

    /**
     * Counts the number of movie trailer object in the list.
     *
     * @return the count.
     */

    @Override
    public int getCount() {
        return movieTrailers.size();
    }

    /**
     * Returns the movie trailer object from the list as specified by the position
     *
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return movieTrailers.get(position);
    }

    /**
     * Returns the position as item id
     *
     * @param position
     * @return
     */

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Inflates the movie trailer item layout and populates a view.
     *
     * @param position    position of movie trailer in the list.
     * @param convertView the populated view
     * @param parent      the parent view
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.movie_trailer_list_item, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.name);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.movie_screenshot);

        MovieTrailer movieTrailer = movieTrailers.get(position);

        textView.setText(movieTrailer.getName());

        Uri builtUri = uri.buildUpon()
                .appendPath(movieTrailer.getKey())
                .appendPath("0.jpg").build();

        Picasso.with(mContext).load(builtUri).placeholder(R.mipmap.ic_launcher)
                .into(imageView);

        return convertView;
    }

    /**
     * Sets the list of movie trailers for this adapter and notifies the view of this change.
     *
     * @param movieTrailers the list of movie trailer object
     */
    public void setMovieTrailers(List<MovieTrailer> movieTrailers) {
        this.movieTrailers = movieTrailers;
        notifyDataSetChanged();
    }
}
