package com.example.yassermuhamed.moviesapp;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public  class MovieIdAdapter extends ArrayAdapter<String>{


    public MovieIdAdapter(Context context, List<String> keys) {
        super(context, 0, keys);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_id_list_item, parent, false);
        }

        String movie = getItem(position);

        TextView trailerMovie = listItemView.findViewById(R.id.trailers_movies);

        trailerMovie.setText(movie);

        ImageView imageViewTrailer = listItemView.findViewById(R.id.play_movie_arrow);

        imageViewTrailer.setImageResource(R.drawable.ic_play_arrow_black_24dp);

        return listItemView;

    }

}
