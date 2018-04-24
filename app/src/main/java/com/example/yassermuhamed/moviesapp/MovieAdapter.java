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

    public ArrayList<MovieData> mArrayListOfThumbnails = new ArrayList<>();

    final private MovieAdapterOnClickHandler mMovieListItemClicked ;

    Context mContext ;

    public MovieAdapter(Context context , MovieAdapterOnClickHandler movieAdapterOnClickHandler){
        mContext = context ;
        mMovieListItemClicked = movieAdapterOnClickHandler;
    }

    public interface MovieAdapterOnClickHandler{
        void onListClickItem(MovieData position);
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

        Picasso.with(mContext).load(NetworkUtils.IMAGE_MOVIE_BASE_URL + mArrayListOfThumbnails.get(position).getPosterPath()).into(holder.mPosterImageView);

    }

    @Override
    public int getItemCount() {
        if (mArrayListOfThumbnails == null)
            return 0 ;
        return mArrayListOfThumbnails.size();
    }

    public void setImageThumbnail( ArrayList<MovieData> movieDataExtracted){

//        String poster_path = movieDataExtracted.getPosterPath();
//
//        String thumbnail = NetworkUtils.IMAGE_MOVIE_BASE_URL  + poster_path ;

        mArrayListOfThumbnails = movieDataExtracted ;

       notifyDataSetChanged();
    }

    class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final ImageView mPosterImageView ;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mPosterImageView = itemView.findViewById(R.id.poster_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int viewHolderPosition = getAdapterPosition();
            mMovieListItemClicked.onListClickItem( mArrayListOfThumbnails.get(viewHolderPosition) );
        }
    }
}
