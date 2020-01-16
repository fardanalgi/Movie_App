package com.dicoding.picodiploma.mybottomnavigation.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dicoding.picodiploma.mybottomnavigation.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbmovie";
    private static final int DATABASE_VERSION = 3 ;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    //berfungsi sebagai DDL

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s" +
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s INTEGER," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.MovieFav.TABLE_NAME,
            DatabaseContract.MovieFav._ID,
            DatabaseContract.MovieFav.COLOMN_MOVIEID,
            DatabaseContract.MovieFav.COLOMN_TITLE,
            DatabaseContract.MovieFav.COLOMN_OVERVIEW,
            DatabaseContract.MovieFav.COLOMN_POSTER_PATH,
            DatabaseContract.MovieFav.COLOMN_BACKDROP_PATH

    );
    private static final String SQL_CREATE_TABLE_TV = String.format("CREATE TABLE %s" +
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s INTEGER," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TvFav.TABLE_NAME_TV,
            DatabaseContract.TvFav._ID,
            DatabaseContract.TvFav.COLOMN_TVID,
            DatabaseContract.TvFav.COLOMN_TITLE_TV,
            DatabaseContract.TvFav.COLOMN_OVERVIEW_TV,
            DatabaseContract.TvFav.COLOMN_POSTER_PATH_TV,
            DatabaseContract.TvFav.COLOMN_BACKDROP_PATH_TV

    );


    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(SQL_CREATE_TABLE_MOVIE);
        database.execSQL(SQL_CREATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.MovieFav.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TvFav.TABLE_NAME_TV);
        onCreate(db);

    }
}
