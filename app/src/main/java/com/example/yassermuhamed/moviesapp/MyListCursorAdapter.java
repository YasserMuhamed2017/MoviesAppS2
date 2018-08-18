package com.example.yassermuhamed.moviesapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yassermuhamed.moviesapp.data.MovieContract;
import com.squareup.picasso.Picasso;

class MyListCursorAdapter extends CursorRecyclerViewAdapter<MyListCursorAdapter.ViewHolder>{

    Context mContext ;

    Cursor mCursor;

    public MyListCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);

        mCursor = cursor ;
    }

   public   static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView IVPosterPath;
        public ViewHolder(View view) {
            super(view);
            IVPosterPath = view.findViewById(R.id.poster_view);
        }
    }

    @Override
    public void onBindViewHolder(MyListCursorAdapter.ViewHolder viewHolder, Cursor cursor) {

        String posterPath = cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_POSTER_PATH));

        Picasso.with(mContext).load(posterPath).into(viewHolder.IVPosterPath);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
