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

    Cursor mCursor;

    public MovieCursorAdapter(Context context, Cursor c) {

        super(context, c );

        mContext = context ;
        mCursor = c ;
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

    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null;
        }
        Cursor temp = mCursor;
        this.mCursor = c;

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

}
