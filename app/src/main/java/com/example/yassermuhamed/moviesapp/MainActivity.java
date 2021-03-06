package com.example.yassermuhamed.moviesapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
import com.example.yassermuhamed.moviesapp.data.MoviesDbHelper;
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

    private SQLiteDatabase sqLiteDatabase;

    GridLayoutManager gridLayoutManager ;

    ImageView mPosterPathImageView;

    MyListItem myListItem = new MyListItem();

    Parcelable mLayoutManagerSavedState;

    MyListCursorAdapter mCursorAdapter ;

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

        mPosterPathImageView = findViewById(R.id.poster_view);

        MoviesDbHelper moviesDbHelper = new MoviesDbHelper(this);

        sqLiteDatabase = moviesDbHelper.getWritableDatabase();

        displayMoviesData();

        mMovieAdapter = new MovieAdapter(this , this );

        mRecyclerView.setAdapter(mMovieAdapter);

        loadPublicMoviesData();
    }

    private void displayMoviesData(){

        mRecyclerView = findViewById(R.id.RecyclerView);

        gridLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(gridLayoutManager);

    }


    private Cursor getAllGuests() {
        return sqLiteDatabase.query(
               MovieContract.MovieEntry.COLUMN_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

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
    public Loader onCreateLoader(int id, Bundle args) {

        String[] projection = {MovieContract.MovieEntry.COLUMN_ID ,
                MovieContract.MovieEntry.COLUMN_POSTER_PATH};

        Uri CONTENT_URI = MovieContract.MovieEntry.CONTENT_URI;

        CursorLoader cursorLoader = new CursorLoader(this , CONTENT_URI , projection , null,null ,null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {

        mCursorAdapter = new MyListCursorAdapter(this , cursor);

        myListItem.setAdapterObject(mCursorAdapter);

        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader loader) {

        mCursorAdapter.swapCursor(null);
    }


  public  class FetchMovieAsyncTask extends AsyncTask<String, Void, ArrayList<MovieData> > {

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

    private void retrieveData() {

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        if (restoredText != null) {
            //mSaved.setText(restoredText, TextView.BufferType.EDITABLE);
            // int selectionStart = prefs.getInt("selection-start", -1);
            // int selectionEnd = prefs.getInt("selection-end", -1);
            // /if (selectionStart != -1 && selectionEnd != -1) {
            // mSaved.setSelection(selectionStart, selectionEnd);
            // }/
            // }
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

            case R.id.movie_favorites :{

                getSupportLoaderManager().initLoader(MOVIE_LOADER_ID ,null ,this);

                displayMoviesData();

                mRecyclerView.setAdapter( mCursorAdapter );


            }return true;


        }
        return super.onOptionsItemSelected(item);
    }
}
