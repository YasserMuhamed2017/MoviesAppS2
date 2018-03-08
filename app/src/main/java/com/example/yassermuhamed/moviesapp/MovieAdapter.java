package com.example.yassermuhamed.moviesapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yassermuhamed.moviesapp.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Yasser Muhamed on 06/03/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    public ArrayList<String> mArrayListOfThumbnails ;
    Context mContext ;
    public MovieAdapter(Context context){
        mContext = context ;
    }
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int listItemMovieShouldPopulated = R.layout.movie_list_item ;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(listItemMovieShouldPopulated , parent , false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {

        Picasso.with(mContext).load(mArrayListOfThumbnails.get(position)).into(holder.mPosterImageView);

    }

    @Override
    public int getItemCount() {
        if (mArrayListOfThumbnails == null)
            return 0 ;
        return mArrayListOfThumbnails.size();
    }

    public void setImageThumbnail( MovieData movieDataExtracted){

        String poster_path = movieDataExtracted.getPosterPath();

        String thumbnail = NetworkUtils.IMAGE_MOVIE_BASE_URL  + poster_path ;

        mArrayListOfThumbnails.add(thumbnail);

       notifyDataSetChanged();
    }

    class MovieAdapterViewHolder extends RecyclerView.ViewHolder{
        public final ImageView mPosterImageView ;
        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mPosterImageView = itemView.findViewById(R.id.poster_view);
        }
    }
}
