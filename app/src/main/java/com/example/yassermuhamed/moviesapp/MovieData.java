package com.example.yassermuhamed.moviesapp;

/**
 * Created by Yasser Muhamed on 06/03/2018.
 */

public class MovieData {

    private String mId;
    private String mPosterPath ;
    private String mOverview ;
    private String mVoteAverage ;
    private String mReleaseDate ;

    public MovieData(){

    }
    public MovieData(String id , String posterPath , String overview , String voteAverage , String releaseDate ){
        mId = id ;
        mPosterPath = posterPath ;
        mOverview = overview ;
        mVoteAverage = voteAverage ;
        mReleaseDate = releaseDate ;
    }

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
}
