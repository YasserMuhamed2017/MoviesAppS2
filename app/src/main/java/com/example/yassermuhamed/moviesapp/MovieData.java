package com.example.yassermuhamed.moviesapp;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieData implements Parcelable{

    private String mId;
    private String mPosterPath ;
    private String mOverview ;
    private String mVoteAverage ;
    private String mReleaseDate ;
    private String mOriginalTitle ;

    public MovieData(String id , String posterPath , String overview , String voteAverage , String releaseDate , String originalTitle ){
        mId = id ;
        mPosterPath = posterPath ;
        mOverview = overview ;
        mVoteAverage = voteAverage ;
        mReleaseDate = releaseDate ;
        mOriginalTitle = originalTitle ;
    }

    private MovieData(Parcel in) {
        mId = in.readString();
        mPosterPath = in.readString();
        mOverview = in.readString();
        mVoteAverage = in.readString();
        mReleaseDate = in.readString();
        mOriginalTitle = in.readString();
    }

    public static final Parcelable.Creator<MovieData> CREATOR = new Parcelable.Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    public String getId(){
        return mId;
    }

    public String getPosterPath(){
        return mPosterPath;
    }

    public String getOverview(){
        return mOverview;
    }

    public String getVoteAverage(){
        return mVoteAverage;
    }

    public String getReleaseDate(){
        return mReleaseDate;
    }

    public String getOriginalTitle(){
        return mOriginalTitle ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mPosterPath);
        dest.writeString(mOverview);
        dest.writeString(mVoteAverage);
        dest.writeString(mReleaseDate);
        dest.writeString(mOriginalTitle);
    }


}
