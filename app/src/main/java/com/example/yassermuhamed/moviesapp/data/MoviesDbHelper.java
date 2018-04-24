package com.example.yassermuhamed.moviesapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yasser Muhamed on 21/04/2018.
 */

public class MoviesDbHelper extends SQLiteOpenHelper {


    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
            MovieContract.MovieEntry.COLUMN_TABLE_NAME + " (" +
            MovieContract.MovieEntry.COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
            MovieContract.MovieEntry.COLUMN_POSTER_PATH + "TEXT NOT NULL)";


    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + MovieContract.MovieEntry.COLUMN_TABLE_NAME ;

    private static final int DATABASE_VERSION = 1 ;

    private static final String DATABASE_NAME = "movie.db";

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
