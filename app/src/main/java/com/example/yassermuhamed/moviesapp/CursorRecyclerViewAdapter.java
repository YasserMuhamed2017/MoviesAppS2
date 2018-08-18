package com.example.yassermuhamed.moviesapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;

public abstract class CursorRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private Context mContext;

    private Cursor mCursor;

    public CursorRecyclerViewAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public Cursor getCursor() {
        return mCursor;
    }

    public abstract void onBindViewHolder(VH viewHolder, Cursor cursor);

}
