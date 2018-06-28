package com.example.yassermuhamed.moviesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class MovieIdAdapter extends RecyclerView.Adapter<MovieIdAdapter.MovieIdAdapterViewHolder>{


    Context mContext;

    private ArrayList<String> mKeysExtracted;

    final private MovieAdapter.MovieAdapterOnClickHandler mMovieListItemClicked ;

    public MovieIdAdapter(Context context , MovieAdapter.MovieAdapterOnClickHandler movieAdapterOnClickHandler){
        mContext = context ;
        mMovieListItemClicked = movieAdapterOnClickHandler;
    }

    public interface MovieIdAdapterOnClickHandler{
        void onListClickItem(MovieData position);
    }


    @Override
    public MovieIdAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MovieIdAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setKeysExtracted(ArrayList<String> keysExtracted) {

        mKeysExtracted = keysExtracted;

    }
    public ArrayList<String> getKeysExtracted(){
        return mKeysExtracted;
    }


    class MovieIdAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final ImageView mPosterImageView ;

        public MovieIdAdapterViewHolder(View itemView) {
            super(itemView);
            mPosterImageView = itemView.findViewById(R.id.poster_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int viewHolderPosition = getAdapterPosition();
            //mMovieListItemClicked.onListClickItem(  );
        }
    }
}
