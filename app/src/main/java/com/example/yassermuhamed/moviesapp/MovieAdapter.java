package com.example.yassermuhamed.moviesapp;

import android.app.Activity;
    import android.content.Context;
    import android.database.Cursor;
    import android.graphics.Movie;
    import android.net.Uri;
    import android.support.v7.widget.RecyclerView;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import com.example.yassermuhamed.moviesapp.data.MovieContract;
    import com.example.yassermuhamed.moviesapp.utilities.NetworkUtils;
    import com.squareup.picasso.Picasso;
    import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    public ArrayList<MovieData> mArrayListOfThumbnails = new ArrayList<>();

    final private MovieAdapterOnClickHandler mMovieListItemClicked ;

    Context mContext ;
    private Cursor mCursor ;

    public MovieAdapter(Context context , MovieAdapterOnClickHandler movieAdapterOnClickHandler , Cursor cursor){
        mContext = context ;
        mMovieListItemClicked = movieAdapterOnClickHandler;
        mCursor = cursor ;
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
//
//        int idIndexed = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID);
//
//        int posterPathIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH);
//
//        mCursor.moveToPosition(position);
//
//        int id = mCursor.getInt(idIndexed);
//
//        String posterPath = mCursor.getString(posterPathIndex);
//
//        holder.itemView.setTag(id);

       // MovieData movieData =  mArrayListOfThumbnails.get(position);

        //Picasso.with(mContext).load(NetworkUtils.IMAGE_MOVIE_BASE_URL + movieData.getPosterPath()).into(holder.mPosterImageView);

        //         Picasso.with(mContext).load(NetworkUtils.IMAGE_MOVIE_BASE_URL + mArrayListOfThumbnails.get(position).getPosterPath()).into(holder.mPosterImageView);

        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null
        if (mCursor.moveToPosition(position) ) {
            String posterPath = mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH));

            Picasso.with(mContext).load(posterPath).into(holder.mPosterImageView);
        }
    }

    @Override
    public int getItemCount() {
        if (mContext == null)
            return 0 ;
        return mArrayListOfThumbnails.size();
    }

    public void setImageThumbnail( ArrayList<MovieData> movieDataExtracted){

        mArrayListOfThumbnails = movieDataExtracted ;

       notifyDataSetChanged();
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
