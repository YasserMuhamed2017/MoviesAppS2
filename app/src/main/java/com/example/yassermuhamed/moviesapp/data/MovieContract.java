package com.example.yassermuhamed.moviesapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Yasser Muhamed on 21/04/2018.
 */

public final class MovieContract {


    public static final String CONTENT_AUTHORITY = "com.example.android.moviesapp" ;

    public static final Uri BASE_CONTENT_URI = Uri.parse("Content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "moviesapp";

    public static final class MovieEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String COLUMN_TABLE_NAME = "movie";

        public static final String COLUMN_ID = BaseColumns._ID;

        public static final String COLUMN_POSTER_PATH = "poster_path";


    }
}
