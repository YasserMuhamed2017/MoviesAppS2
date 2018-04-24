package com.example.yassermuhamed.moviesapp.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by Yasser Muhamed on 21/04/2018.
 */

public class MovieProvider extends ContentProvider {

    private static final int MOVIES = 100 ;

    private static final int MOVIES_ID = 101 ;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY , MovieContract.PATH_MOVIES , MOVIES);
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY , MovieContract.PATH_MOVIES + "/#" , MOVIES_ID);
    }

    private MoviesDbHelper mMoviesDbHelper;
    @Override
    public boolean onCreate() {

        mMoviesDbHelper = new MoviesDbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase sqLiteDatabase = mMoviesDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match){

            case MOVIES:
                cursor = sqLiteDatabase.query(MovieContract.MovieEntry.COLUMN_TABLE_NAME,
                                            projection ,
                                            selection,
                                             selectionArgs,
                                             null,
                                             null,
                                           sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver() , uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase sqLiteDatabase = mMoviesDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int count;
        switch (match){
        case MOVIES_ID:
            String id = uri.getPathSegments().get(1);
        count = sqLiteDatabase.update(MovieContract.MovieEntry.COLUMN_TABLE_NAME, values,
                MovieContract.MovieEntry.COLUMN_ID + " = " + id , selectionArgs);
        break;
        default:
        throw new IllegalArgumentException("Unknown URI " + uri );

    }
        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }
}
