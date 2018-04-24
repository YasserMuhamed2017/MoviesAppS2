package com.example.yassermuhamed.moviesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Yasser Muhamed on 23/04/2018.
 */

public class MovieIdAdapter extends ArrayAdapter<MovieIdItem> {

    private ArrayList<String> mKeysExtracted;

    public MovieIdAdapter(@NonNull Context context, ArrayList<MovieIdItem> movie) {


        super(context,0, movie);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View movieIdListItem = convertView;
        if(movieIdListItem == null) {
            movieIdListItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_id_list_item, parent, false);
        }
        MovieIdItem trailers = getItem(position);

        TextView trailerTextView = movieIdListItem.findViewById(R.id.trailer);

        trailerTextView.setText(trailers.getTrailer());

        ImageView trailersImageView = movieIdListItem.findViewById(R.id.play_movie_arrow);

        trailersImageView.setImageResource(trailers.getImageDrawableId());

        return movieIdListItem;
    }

    public void setKeysExtracted(ArrayList<String> keysExtracted) {

        mKeysExtracted = keysExtracted;

    }
    public ArrayList<String> getKeysExtracted(){
        return mKeysExtracted;
    }
}
