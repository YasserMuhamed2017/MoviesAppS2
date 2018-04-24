package com.example.yassermuhamed.moviesapp;

/**
 * Created by Yasser Muhamed on 24/04/2018.
 */

public class MovieIdItem {

    private String mTrailer;
    private int mImageDrawableId;

    public MovieIdItem( String trailer , int imageDrawableId ){
        mTrailer = trailer;
        mImageDrawableId = imageDrawableId;
    }

    public String getTrailer(){
        return mTrailer;
    }

    public int getImageDrawableId(){
        return mImageDrawableId;
    }
}
