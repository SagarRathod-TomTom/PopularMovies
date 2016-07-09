/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */

package com.sagarrathod.popularmovies.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The bean class for movie trailer object to hold movie trailer name and its key.
 *
 * @author Sagar Rathod
 * @version 1.0
 */

public class MovieTrailer implements Parcelable {

    private String mKey;
    private String mName;

    /**
     * An empty constructor for movie trailer object.
     */
    public MovieTrailer(){
    }

    /**
     * Constructs an instance of movie trailer object.
     * @param in parcel input
     */
    public MovieTrailer(Parcel in) {
        mKey = in.readString();
        mName = in.readString();
    }

    /**
     * The creator class for movie trailer object from parcel.
     */

    public static final Creator<MovieTrailer> CREATOR = new Creator<MovieTrailer>() {

        /**
         * Unmarshals the parcel to construct movie trailer object.
         *
         * @param in The parcel object from which properties of movie trailers are read.
         * @return @link{MovieTrailer}
         */
        @Override
        public MovieTrailer createFromParcel(Parcel in) {
            return new MovieTrailer(in);
        }

        @Override
        public MovieTrailer[] newArray(int size) {
            return new MovieTrailer[size];
        }
    };

    public String getKey() {
        return mKey;
    }

    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Marshals the movie trailer object into parcel destination.
     *
     * @param dest  The parcel object to which movie trailer properties are written.
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mKey);
        dest.writeString(mName);
    }

    public String toString(){
        return  mName;
    }
}
