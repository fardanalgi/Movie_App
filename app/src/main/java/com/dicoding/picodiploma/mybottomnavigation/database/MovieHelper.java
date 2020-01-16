package com.dicoding.picodiploma.mybottomnavigation.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.provider.BaseColumns;
import android.util.Log;

import com.dicoding.picodiploma.mybottomnavigation.model.Movie;

import java.util.ArrayList;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static android.support.constraint.Constraints.TAG;
import static com.dicoding.picodiploma.mybottomnavigation.database.DatabaseContract.MovieFav.COLOMN_BACKDROP_PATH;
import static com.dicoding.picodiploma.mybottomnavigation.database.DatabaseContract.MovieFav.COLOMN_MOVIEID;
import static com.dicoding.picodiploma.mybottomnavigation.database.DatabaseContract.MovieFav.COLOMN_OVERVIEW;
import static com.dicoding.picodiploma.mybottomnavigation.database.DatabaseContract.MovieFav.COLOMN_POSTER_PATH;
import static com.dicoding.picodiploma.mybottomnavigation.database.DatabaseContract.MovieFav.COLOMN_TITLE;
import static com.dicoding.picodiploma.mybottomnavigation.database.DatabaseContract.MovieFav.TABLE_NAME;


public class MovieHelper {

    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static MovieHelper INSTANCE;
    private static SQLiteDatabase database;

    private MovieHelper (Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new MovieHelper(context);
                }
            }
        }

        return INSTANCE;

    }

    public void open() throws SQLException{
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }
    public ArrayList<Movie> getALLMovi() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                        null,
                        null,
                        null,
                        null,
                        _ID + " ASC",
                        null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseContract.MovieFav.COLOMN_MOVIEID))));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseContract.MovieFav.COLOMN_TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(DatabaseContract.MovieFav.COLOMN_OVERVIEW)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(COLOMN_POSTER_PATH)));
                movie.setBackdroppath(cursor.getString(cursor.getColumnIndex(DatabaseContract.MovieFav.COLOMN_BACKDROP_PATH)));

                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());

        }
        cursor.close();
        return arrayList;

    }

    //berfungsi sebagai DML

    public long insertMovie(Movie movie) {
        ContentValues value = new ContentValues();
        value.put(COLOMN_MOVIEID, movie.getId());
        value.put(COLOMN_TITLE, movie.getTitle());
        value.put(COLOMN_OVERVIEW, movie.getOverview());
        value.put(COLOMN_POSTER_PATH, movie.getPosterPath());
        value.put(COLOMN_BACKDROP_PATH, movie.getBackdroppath());
        return database.insert(DATABASE_TABLE, null, value);
    }


    public void deleteMovie(int id) {
        database = databaseHelper.getWritableDatabase();
        database.delete(DatabaseContract.MovieFav.TABLE_NAME, DatabaseContract.MovieFav.COLOMN_MOVIEID + "=" + id, null);
    }



    public boolean movieCheck(String id) {
        database = databaseHelper.getWritableDatabase();
        String select = "SELECT * FROM " + DatabaseContract.MovieFav.TABLE_NAME + " WHERE " + DatabaseContract.MovieFav.COLOMN_MOVIEID + " =?";
        Cursor cursor = database.rawQuery(select, new String[]{id});
        boolean movieCheck = false;
        if (cursor.moveToFirst()) {
            movieCheck = true;
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
            Log.d(TAG, String.format("%d records found", count));
        }
        cursor.close();
        return movieCheck;
    }
}
