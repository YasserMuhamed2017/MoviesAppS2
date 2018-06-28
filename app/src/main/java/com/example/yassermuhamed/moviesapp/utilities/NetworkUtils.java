package com.example.yassermuhamed.moviesapp.utilities;

import android.net.Uri;

import com.example.yassermuhamed.moviesapp.BuildConfig;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Yasser Muhamed on 06/03/2018.
 */

public final class NetworkUtils {

    public static final String IMAGE_MOVIE_BASE_URL = "http://image.tmdb.org/t/p/w185/" ;

    private static final String API_KEY = BuildConfig.API_KEY;

    public static final String TOP_RATED_MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY;

    public static final String POPULAR_MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY;

    public static final String MOVIE_TRAILER_URL = "https://www.youtube.com/watch?v=";

   public static final Uri BASE_EACH_MOVIE_ID = Uri.parse("https://api.themoviedb.org/3/movie");

   public static final String PATH_APPENDED = "videos?api_key=" + API_KEY;
           //"https://api.themoviedb.org/3/movie/" + 284054 + "/videos?api_key=" + API_KEY;


    private static final String LANGUAGE_PARAM = "language";

    private static final String PAGE_PARAM = "page";

    private static final String LANGUAGE_FORMAT = "en-US";

    private static final int PAGE_NUMBER = 1;

    public static URL buildUrl( String movieDataQuery ){
        Uri buildUri = Uri.parse(movieDataQuery).buildUpon()
                       .appendQueryParameter(LANGUAGE_PARAM , LANGUAGE_FORMAT)
                       .appendQueryParameter(PAGE_PARAM , String.valueOf(PAGE_NUMBER))
                       .build();
        URL url = null ;

        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    // Construct URL for each movie id https://api.themoviedb.org/3/movie/269149/videos?api_key=api_key&language=en-US

    public static URL buildVideoIdUrl(String movieId){

    //         Uri buildUri = BASE_EACH_MOVIE_ID.buildUpon().
    //                 appendPath(movieId).
    //                 appendPath( PATH_APPENDED ).
    //                 appendQueryParameter(LANGUAGE_PARAM , LANGUAGE_FORMAT).
    //                 build();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3").appendPath("movie").appendPath(movieId).appendPath("videos").appendQueryParameter("api_key" , API_KEY).appendQueryParameter(LANGUAGE_PARAM ,LANGUAGE_FORMAT );

        URL url = null ;

        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;

    }

    public static Uri constructYoutubeUrl(String key){

        Uri builtUr = Uri.parse( MOVIE_TRAILER_URL + key );

        return builtUr;
    }

    public static String makeHttpUrlConnection(URL url) throws IOException {
        HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
        String dataRequested;
        try {
            InputStream inputStream = httpURLConnection.getInputStream();

            inputStream = new BufferedInputStream(inputStream);

            dataRequested =  readFromStream(inputStream);

            inputStream.close();

            return dataRequested;
        }finally {
            httpURLConnection.disconnect();
        }

    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}
