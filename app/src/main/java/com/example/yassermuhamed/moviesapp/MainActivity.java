package com.example.yassermuhamed.moviesapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.yassermuhamed.moviesapp.utilities.NetworkUtils;
import com.example.yassermuhamed.moviesapp.utilities.OpenMovieJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

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

        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(MainActivity.this);

        mRecyclerView.setAdapter(mMovieAdapter);

        loadMovieData();
    }

    private void loadMovieData(){
        String movieData = NetworkUtils.TOP_RATED_MOVIE_BASE_URL ;
        new FetchMovieAsyncTask().execute(movieData);
    }

    class FetchMovieAsyncTask extends AsyncTask<String, Void, MovieData> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected MovieData doInBackground(String... strings) {
            if (strings.length == 0)
                return null;
            String url = strings[0];
            URL buildUrlMoviesData = NetworkUtils.buildUrl(url);
            String dataResponseByHttp = " " ;

            try {

                 dataResponseByHttp = NetworkUtils.makeHttpUrlConnection(buildUrlMoviesData);

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
        protected void onPostExecute(MovieData movieData) {
            if ( movieData != null){
                mMovieAdapter.setImageThumbnail(movieData);
            }else {
                Log.v(TAG , "NULL POINTER EXCEPTION");
                //Toast.makeText(MainActivity.this, " NULL POINTER EXCEPTION ", Toast.LENGTH_LONG).show();
            }
        }
    }
}
