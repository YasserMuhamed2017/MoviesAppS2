package com.example.yassermuhamed.moviesapp;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.example.yassermuhamed.moviesapp.data.MovieContract;
import com.squareup.picasso.Picasso;

public class MovieCursorAdapter extends CursorAdapter {

    Context mContext ;

    public MovieCursorAdapter(Context context, Cursor c) {

        super(context, c );

        mContext = context ;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        ImageView IVPosterPath = view.findViewById(R.id.poster_view);

        String posterPath = cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_POSTER_PATH));

        Picasso.with(mContext).load(posterPath).into(IVPosterPath);
    }
}
