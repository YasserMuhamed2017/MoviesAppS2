package com.example.yassermuhamed.moviesapp;

    import android.annotation.SuppressLint;
    import android.content.ActivityNotFoundException;
    import android.content.ContentUris;
    import android.content.ContentValues;
    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.net.Uri;
    import android.os.AsyncTask;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.LinearLayout;
    import android.widget.ListView;
    import android.widget.TextView;
    import android.widget.Toast;
    import com.example.yassermuhamed.moviesapp.data.MovieContract;
    import com.example.yassermuhamed.moviesapp.utilities.NetworkUtils;
    import com.example.yassermuhamed.moviesapp.utilities.OpenMovieJsonUtils;
    import com.squareup.picasso.Picasso;
    import org.json.JSONException;
    import java.io.IOException;
    import java.net.URL;
    import java.util.ArrayList;

public class MovieDetailsActivity extends AppCompatActivity {

    TextView overviewTV;
    ImageView imageView;
    TextView releaseDateTV;
    TextView rankingTV;
    TextView originalTitleTV ;
    MovieIdAdapter movieIdAdapter;
    Button mFavoriteButton;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        overviewTV = findViewById(R.id.overview);
        imageView = findViewById(R.id.poster_view_movie);
        releaseDateTV = findViewById(R.id.release_date);
        rankingTV = findViewById(R.id.ranking);
        originalTitleTV = findViewById(R.id.original_title);
        mFavoriteButton = findViewById(R.id.favorite_movie);

        Intent intent = getIntent();

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {

            final MovieData dataExtracted = intent.getParcelableExtra(Intent.EXTRA_TEXT);

            overviewTV.setText(dataExtracted.getOverview());

            Picasso.with(this).load(NetworkUtils.IMAGE_MOVIE_BASE_URL + dataExtracted.getPosterPath()).into(imageView);

            releaseDateTV.setText(dataExtracted.getReleaseDate());

            rankingTV.setText(dataExtracted.getVoteAverage());

            originalTitleTV.setText(dataExtracted.getOriginalTitle());

            final String id = dataExtracted.getId();

            new FetchMovieIdAsyncTask().execute(NetworkUtils.buildVideoIdUrl(id));

            mFavoriteButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public void onClick(View v) {

                    ContentValues contentValues = new ContentValues();

                    contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, NetworkUtils.IMAGE_MOVIE_BASE_URL + dataExtracted.getPosterPath());

                    getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);

                        }

            });
        }

        ArrayList<String> trailersArrayList = new ArrayList<>();

        for (int i = 0 ; i < OpenMovieJsonUtils.getListOfKeys() ; i++){

            trailersArrayList.add("trailer"+(i+1));

        }

        ListView listViewMoviesTrailer = findViewById(R.id.listView);

        movieIdAdapter = new MovieIdAdapter(this , trailersArrayList );

        listViewMoviesTrailer.setAdapter(movieIdAdapter);

        listViewMoviesTrailer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String key = movieIdAdapter.getItem(position);

                //String key = OpenMovieJsonUtils.getKeysExtracted().get(position);

                Intent intent = new Intent(Intent.ACTION_VIEW, NetworkUtils.constructYoutubeUrl(key));

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

    }


    class FetchMovieIdAsyncTask extends AsyncTask<URL, Void, ArrayList<String> > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(URL... urls) {
            if (urls.length == 0)
                return null;
            URL urlForEachMovieId = urls[0];

            try {

                String dataResponseByHttp = NetworkUtils.makeHttpUrlConnection(urlForEachMovieId);

                return OpenMovieJsonUtils.extractMovieKeys(dataResponseByHttp);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<String> movieKeysArrayList) {
            if ( movieKeysArrayList != null){

                // here we want to set keys that I get from each movie id https://www.youtube.com/watch?v=<<key>>

                OpenMovieJsonUtils.setKeysExtracted(movieKeysArrayList);

            }else {
                Toast.makeText(MovieDetailsActivity.this, " NULL POINTER EXCEPTION ", Toast.LENGTH_LONG).show();
            }
        }
    }
}
