/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */

package com.sagarrathod.popularmovies.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * The bean class for movie object to hold movie details.
 *
 * @author Sagar Rathod
 * @version 1.0
 */

public class Movie implements Parcelable {

    private int mMovieId;
    private String mOriginalTitle;
    private String mPosterPath;
    private String mReleaseDate;
    private String mOverview;
    private double mPopularity;
    private double mVoteAverage;
    private List<MovieTrailer> mMovieTrailers;
    private List<UserReview> mUserReviews;
    private boolean mIsFavoriteMovie;

    /**
     * An empty constructor for movie object.
     */
    public Movie() {
    }

    /**
     * Constructs a movie object with the given details.
     * @param movieId       The id of this movie.
     * @param originalTitle The title for this movie.
     * @param posterPath    The poster path of this movie.
     * @param releaseDate   The date of release of this movie.
     * @param overview      The movie overview.
     * @param popularity    The popularity of this movie.
     * @param voteAverage   The user rating of this movie.
     */
    public Movie(int movieId,String originalTitle, String posterPath, String releaseDate, String overview,
                 double popularity, double voteAverage,byte isFavoriteMovie) {
        this.mMovieId = movieId;
        this.mOriginalTitle = originalTitle;
        this.mPosterPath = posterPath;
        this.mReleaseDate = releaseDate;
        this.mOverview = overview;
        this.mPopularity = popularity;
        this.mVoteAverage = voteAverage;
        this.mIsFavoriteMovie = isFavoriteMovie == 1 ? true : false;
    }

    public Movie(Parcel in){
        this(in.readInt(),in.readString(), in.readString(), in.readString(), in.readString(), in.readDouble(),
                in.readDouble(),in.readByte());
        mMovieTrailers = new ArrayList<MovieTrailer>();
        in.readTypedList(mMovieTrailers, MovieTrailer.CREATOR);

        mUserReviews = new ArrayList<UserReview>();
        in.readTypedList(mUserReviews,UserReview.CREATOR);
    }

    /**
     * The creater class for movie object from parcel.
     */
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {

        /**
         * Unmarshals the parcel to construct movie object.
         *
         * @param in The parcel object from which movie details are read.
         * @return movie
         */
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Marshals the movie object into parcel destination.
     *
     * @param dest  The parcel object to which movie details are written.
     * @param flags
     */

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //write the variables in that order
        //String originalTitle, String posterPath, String releaseDate,String overview, double popularity, double voteAverage)
        dest.writeInt(mMovieId);
        dest.writeString(mOriginalTitle);
        dest.writeString(mPosterPath);
        dest.writeString(mReleaseDate);
        dest.writeString(mOverview);
        dest.writeDouble(mPopularity);
        dest.writeDouble(mVoteAverage);
        dest.writeByte((byte) (mIsFavoriteMovie == true ? 1 : 0));
        dest.writeTypedList(mMovieTrailers);
        dest.writeTypedList(mUserReviews);
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.mOriginalTitle = originalTitle;
    }

    public double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(double popularity) {
        this.mPopularity = popularity;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        this.mOverview = overview;
    }

    public void setVoteAverage(double voteAverage) {
        this.mVoteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.mReleaseDate = releaseDate;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        this.mPosterPath = posterPath;
    }

    public int getMovieId() {
        return mMovieId;
    }

    public void setMovieId(int mMovieId) {
        this.mMovieId = mMovieId;
    }

    public List<UserReview> getUserReviews() {
        return mUserReviews;
    }

    public void setUserReviews(List<UserReview> userReviews) {
        this.mUserReviews = userReviews;
    }

    public List<MovieTrailer> getMovieTrailers() {
        return mMovieTrailers;
    }

    public void setMovieTrailers(List<MovieTrailer> movieTrailers) {
        this.mMovieTrailers = movieTrailers;
    }

    public boolean isFavoriteMovie() {
        return mIsFavoriteMovie;
    }

    public void setFavoriteMovie(boolean favoriteMovie) {
        mIsFavoriteMovie = favoriteMovie;
    }

    /**
     * Compares Movie objects on basis of movie title.
     *
     * @param o An instance of this class.
     * @return Returns true if this.mOriginalTile.equals(o.mOriginalTitle) to true, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Movie) {
            Movie movie = (Movie) o;
            if (this.mMovieId == movie.getMovieId()) {
                return true;
            } else
                return false;
        } else
            return false;
    }

    @Override
    public String toString() {
        return "Movie:["+ mMovieId + mOriginalTitle + "]";
    }
}
