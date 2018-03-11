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


   static ArrayList<MovieData> mMovieDataArrayList = new ArrayList<>();

 public static ArrayList<MovieData> extractJSONMovieData(String movieJSONString ) throws JSONException {

     JSONObject root = new JSONObject(movieJSONString);

     JSONArray resultsArray = root.optJSONArray("results");

     for ( int i = 0 ; i < resultsArray.length() ; i++){

         JSONObject jsonObject = resultsArray.optJSONObject(i);

         String id = jsonObject.optString("id");

         String posterPath = jsonObject.optString("poster_path");

         String overview = jsonObject.optString("overview");

         String voteAverage = jsonObject.optString("vote_average");

         String releaseDate = jsonObject.optString("release_date");

         MovieData mMovieData = new MovieData(id , posterPath , overview , voteAverage , releaseDate);

         mMovieDataArrayList.add(mMovieData);
     }
     return mMovieDataArrayList;
 }

}
