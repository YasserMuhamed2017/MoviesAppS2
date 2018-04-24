package com.example.yassermuhamed.moviesapp.utilities;

import com.example.yassermuhamed.moviesapp.MovieData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Yasser Muhamed on 06/03/2018.
 */

public final class OpenMovieJsonUtils {


 public static ArrayList<MovieData> extractJSONMovieData(String movieJSONString ) throws JSONException {

     ArrayList<MovieData> mMovieDataArrayList = new ArrayList<>();

     JSONObject root = new JSONObject(movieJSONString);

     JSONArray resultsArray = root.optJSONArray("results");

     for ( int i = 0 ; i < resultsArray.length() ; i++){

         JSONObject jsonObject = resultsArray.optJSONObject(i);

         String id = jsonObject.optString("id");

         String posterPath = jsonObject.optString("poster_path");

         String overview = jsonObject.optString("overview");

         String voteAverage = jsonObject.optString("vote_average");

         String releaseDate = jsonObject.optString("release_date");

         String originalTitle = jsonObject.optString("original_title");

         MovieData mMovieData = new MovieData(id , posterPath , overview , voteAverage , releaseDate , originalTitle);

         mMovieDataArrayList.add(mMovieData);
     }
     return mMovieDataArrayList;
 }


    public static ArrayList<String> extractJSONMovieKeys(String dataResponseByHttp) throws JSONException {

        ArrayList<String> arrayListOfKeys = new ArrayList<>();

        JSONObject root = new JSONObject(dataResponseByHttp);

        JSONArray resultsArray = root.optJSONArray("results");

        for ( int i = 0 ; i < resultsArray.length() ; i++){

            JSONObject jsonObject = resultsArray.optJSONObject(i);

            String key = jsonObject.optString("key");

            arrayListOfKeys.add(key);
        }
        return arrayListOfKeys;
    }
}
