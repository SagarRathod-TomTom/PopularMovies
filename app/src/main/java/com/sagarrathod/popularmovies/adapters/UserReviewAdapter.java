/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */
package com.sagarrathod.popularmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sagarrathod.popularmovies.R;
import com.sagarrathod.popularmovies.beans.UserReview;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for user review list view.
 *
 * @author Sagar Rathod
 * @version 1.0
 */

public class UserReviewAdapter extends BaseAdapter {

    private Context mContext;
    private List<UserReview> mUserReviews = new ArrayList<UserReview>();

    /**
     * Creates an instance of user review adapter.
     *
     * @param context the application context
     */
    public UserReviewAdapter(Context context) {
        this.mContext = context;
    }

    /**
     * Creates an instance of user review adapter.
     *
     * @param mContext      the application context
     * @param mUserReviews  list of user reviews object
     */
    public UserReviewAdapter(Context mContext, List<UserReview> mUserReviews) {
        this.mContext = mContext;
        this.mUserReviews = mUserReviews;
    }

    /**
     * Counts the number of user review object in the list.
     *
     * @return the count.
     */
    @Override
    public int getCount() {
        return mUserReviews.size();
    }

    /**
     * Returns the user review object from the list as specified by the position
     *
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return mUserReviews.get(position);
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
     * Inflates the user review list item layout and populates a view.
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
            convertView = inflater.inflate(R.layout.review_list_item, parent, false);
        }

        TextView authorTextView = (TextView) convertView.findViewById(R.id.author);

        TextView commentTextView = (TextView) convertView.findViewById(R.id.comment);

        UserReview userReview = mUserReviews.get(position);

        authorTextView.setText(" " + userReview.getAuthor());

        commentTextView.setText(userReview.getContent());

        return convertView;
    }

    /**
     * Sets the list of user reviews for this adapter and notifies the view of this change.
     *
     * @param mUserReviews the list of user review object
     */
    public void setUserReviews(List<UserReview> mUserReviews) {
        this.mUserReviews = mUserReviews;
        notifyDataSetChanged();
    }
}
