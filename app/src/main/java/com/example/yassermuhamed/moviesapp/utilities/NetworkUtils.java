package com.example.yassermuhamed.moviesapp.utilities;

import android.net.Uri;
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

    public static final String TOP_RATED_MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=4e21e64da6a3965021ffdaba52ad6ace";

    public static final String POPULAR_MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/popular?api_key=4e21e64da6a3965021ffdaba52ad6ace";

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
