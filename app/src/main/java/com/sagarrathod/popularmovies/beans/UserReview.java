/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */

package com.sagarrathod.popularmovies.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The bean class for user review object to hold author name and review comments.
 *
 * @author Sagar Rathod
 * @version 1.0
 */
public class UserReview implements Parcelable {

    /**
     * The creator class for user review object from parcel.
     */
    public static final Creator<UserReview> CREATOR = new Creator<UserReview>() {
        @Override
        public UserReview createFromParcel(Parcel in) {
            return new UserReview(in);
        }

        @Override
        public UserReview[] newArray(int size) {
            return new UserReview[size];
        }
    };
    private String mAuthor;
    private String mContent;

    /**
     * An empty constructor for user review object.
     */
    public UserReview() {
    }

    /**
     * Constructs an instance of user review object from parcel.
     *
     * @param in parcel input
     */
    public UserReview(Parcel in) {
        mAuthor = in.readString();
        mContent = in.readString();
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Marshals the user review object into parcel destination.
     *
     * @param dest  The parcel object to which user review properties are written.
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAuthor);
        dest.writeString(mContent);
    }
}
