package com.example.yassermuhamed.moviesapp;

import android.database.Cursor;

public class MyListItem{
    private String name;
    MyListCursorAdapter listCursorAdapter;

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }

    public static MyListItem fromCursor(Cursor cursor) {
        //TODO return your MyListItem from cursor.
        return null;
    }

    public void setAdapterObject(MyListCursorAdapter myListCursorAdapter) {
        listCursorAdapter = myListCursorAdapter ;
    }

    public MyListCursorAdapter getListCursorAdapter() {
        return listCursorAdapter;
    }
}