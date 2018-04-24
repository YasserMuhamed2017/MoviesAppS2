package com.example.yassermuhamed.moviesapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        overviewTV = findViewById(R.id.overview);
        imageView = findViewById(R.id.poster_view_movie);
        releaseDateTV = findViewById(R.id.release_date);
        rankingTV = findViewById(R.id.ranking);
        originalTitleTV = findViewById(R.id.original_title);

        Intent intent = getIntent();

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {

            MovieData dataExtracted = intent.getParcelableExtra(Intent.EXTRA_TEXT);

            overviewTV.setText(dataExtracted.getOverview());

            Picasso.with(this).load(NetworkUtils.IMAGE_MOVIE_BASE_URL + dataExtracted.getPosterPath()).into(imageView);

            releaseDateTV.setText(dataExtracted.getReleaseDate());

            rankingTV.setText(dataExtracted.getVoteAverage());

            originalTitleTV.setText(dataExtracted.getOriginalTitle());

            new FetchMovieIdAsyncTask().execute(NetworkUtils.buildYoutubeUrl(dataExtracted.getId()));
        }

        final ArrayList<MovieIdItem> trailersArrayList = new ArrayList<>();

        trailersArrayList.add(new MovieIdItem("trailer1" , R.drawable.ic_play_arrow_black_24dp));
        trailersArrayList.add(new MovieIdItem("trailer2" , R.drawable.ic_play_arrow_black_24dp));
        trailersArrayList.add(new MovieIdItem("trailer3" , R.drawable.ic_play_arrow_black_24dp));
        trailersArrayList.add(new MovieIdItem("trailer4" , R.drawable.ic_play_arrow_black_24dp));
        trailersArrayList.add(new MovieIdItem("trailer5" , R.drawable.ic_play_arrow_black_24dp));
        trailersArrayList.add(new MovieIdItem("trailer6" , R.drawable.ic_play_arrow_black_24dp));

         movieIdAdapter = new MovieIdAdapter(this , trailersArrayList);

         listView = findViewById(R.id.listView);

        listView.setAdapter(movieIdAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               String key =  movieIdAdapter.getKeysExtracted().get(position);

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

                return OpenMovieJsonUtils.extractJSONMovieKeys(dataResponseByHttp);

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
                movieIdAdapter.setKeysExtracted(movieKeysArrayList);
            }else {
                Toast.makeText(MovieDetailsActivity.this, " NULL POINTER EXCEPTION ", Toast.LENGTH_LONG).show();
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

        }

        return super.onOptionsItemSelected(item);
    }
}
