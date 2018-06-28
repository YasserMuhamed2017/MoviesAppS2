package com.example.yassermuhamed.moviesapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.yassermuhamed.moviesapp.data.MovieContract;
import com.example.yassermuhamed.moviesapp.utilities.NetworkUtils;
import com.example.yassermuhamed.moviesapp.utilities.OpenMovieJsonUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler,LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = MainActivity.class.getSimpleName();

    RecyclerView mRecyclerView;

    MovieAdapter mMovieAdapter;

    GridLayoutManager gridLayoutManager ;

    ImageView mPosterPathImageView;

    Parcelable mLayoutManagerSavedState;

    ArrayList<MovieData> cursorList = new ArrayList<>();

    int spanCount = 2;

    private static final int MOVIE_LOADER_ID = 0;

    private static final String KEY_INSTANCE_STATE_RV_POSITION = "classname.recycler.layout";

    public static final String[] MAIN_MOVIE_PROJECTION = {
            MovieContract.MovieEntry.COLUMN_POSTER_PATH
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.RecyclerView);

        mPosterPathImageView = findViewById(R.id.poster_view);

        gridLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(gridLayoutManager);

        mMovieAdapter = new MovieAdapter(this , this);

        mRecyclerView.setAdapter(mMovieAdapter);

        loadPublicMoviesData();
    }

//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//
//        savedInstanceState.putParcelable(KEY_INSTANCE_STATE_RV_POSITION, gridLayoutManager.onSaveInstanceState());
//
//        if (savedInstanceState != null) {
//            mLayoutManagerSavedState = savedInstanceState.getParcelable(KEY_INSTANCE_STATE_RV_POSITION);
//        }
//
//        if (mLayoutManagerSavedState != null) {
//            gridLayoutManager.onRestoreInstanceState(mLayoutManagerSavedState);
//        }
//
//    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }

    private void loadTopRatedMoviesData(){

        String movieData = NetworkUtils.TOP_RATED_MOVIE_BASE_URL ;

        new FetchMovieAsyncTask().execute(movieData);
    }

    private void loadPublicMoviesData(){
        String movieData = NetworkUtils.POPULAR_MOVIE_BASE_URL ;
        new FetchMovieAsyncTask().execute(movieData);
    }

    @Override
    public void onListClickItem(MovieData position) {

        Intent intent = new Intent(MainActivity.this , MovieDetailsActivity.class);

        intent.putExtra(Intent.EXTRA_TEXT , position);

        startActivity(intent);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, MovieContract.MovieEntry.CONTENT_URI , MAIN_MOVIE_PROJECTION ,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            int posterPathIndex =
                data.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH);

        String posterPath = data.getString(posterPathIndex);
        Picasso.with(this).load(NetworkUtils.IMAGE_MOVIE_BASE_URL + posterPath).into(mPosterPathImageView);

        mMovieAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mMovieAdapter.swapCursor(null);
    }


    class FetchMovieAsyncTask extends AsyncTask<String, Void, ArrayList<MovieData> > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<MovieData> doInBackground(String... strings) {
            if (strings.length == 0)
                return null;
            String url = strings[0];
            URL buildUrlMoviesData = NetworkUtils.buildUrl(url);

            try {

                String dataResponseByHttp = NetworkUtils.makeHttpUrlConnection(buildUrlMoviesData);

                return OpenMovieJsonUtils.extractJSONMovieData(dataResponseByHttp);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(ArrayList<MovieData> movieDataArrayList) {
            if ( movieDataArrayList != null){
                mMovieAdapter.setImageThumbnail(movieDataArrayList);
            }else {
                Toast.makeText(MainActivity.this, " There's no data requested ", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.menu_movie , menu);
         return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.movie_public : loadPublicMoviesData(); return true;

            case R.id.movie_top_rated : loadTopRatedMoviesData(); return true;

            case R.id.movie_favorites : getSupportLoaderManager().initLoader(MOVIE_LOADER_ID ,null,this); return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
