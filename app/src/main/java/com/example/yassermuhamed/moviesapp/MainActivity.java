package com.example.yassermuhamed.moviesapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.yassermuhamed.moviesapp.utilities.NetworkUtils;
import com.example.yassermuhamed.moviesapp.utilities.OpenMovieJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{

    private static final String TAG = MainActivity.class.getSimpleName();

    RecyclerView mRecyclerView;

    MovieAdapter mMovieAdapter;

    int spanCount = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.RecyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(gridLayoutManager);

        mMovieAdapter = new MovieAdapter(this , this);

        mRecyclerView.setAdapter(mMovieAdapter);

        loadPublicMoviesData();
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
                Toast.makeText(MainActivity.this, " NULL POINTER EXCEPTION ", Toast.LENGTH_LONG).show();
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

            case R.id.movie_favorites : return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
