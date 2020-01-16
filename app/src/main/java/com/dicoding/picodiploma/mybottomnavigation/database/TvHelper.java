package com.dicoding.picodiploma.mybottomnavigation.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import com.dicoding.picodiploma.mybottomnavigation.model.TvShow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static android.support.constraint.Constraints.TAG;
import static com.dicoding.picodiploma.mybottomnavigation.database.DatabaseContract.TvFav.COLOMN_BACKDROP_PATH_TV;
import static com.dicoding.picodiploma.mybottomnavigation.database.DatabaseContract.TvFav.COLOMN_OVERVIEW_TV;
import static com.dicoding.picodiploma.mybottomnavigation.database.DatabaseContract.TvFav.COLOMN_POSTER_PATH_TV;
import static com.dicoding.picodiploma.mybottomnavigation.database.DatabaseContract.TvFav.COLOMN_TITLE_TV;
import static com.dicoding.picodiploma.mybottomnavigation.database.DatabaseContract.TvFav.COLOMN_TVID;
import static com.dicoding.picodiploma.mybottomnavigation.database.DatabaseContract.TvFav.TABLE_NAME_TV;

public class TvHelper {

    private static final String DATABASE_TABLE = TABLE_NAME_TV;
    private static DatabaseHelper dbHelperTv;
    private static TvHelper INSTANCE;
    private static SQLiteDatabase databaseTv;

    private TvHelper(Context context) {
        dbHelperTv = new DatabaseHelper(context);
    }

    public static TvHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvHelper(context);
                }
            }
        }return INSTANCE;
    }

    public void open() throws SQLException {
        databaseTv = dbHelperTv.getWritableDatabase();
    }

    public void close() {
        dbHelperTv.close();
        if (databaseTv.isOpen())
            databaseTv.close();
    }

    public ArrayList<TvShow> getAllTv() {
        ArrayList<TvShow> arrayList = new ArrayList<>();
        Cursor cursor = databaseTv.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);

        cursor.moveToFirst();
        TvShow tvShow;
        if (cursor.getCount() > 0) {
            do {
                tvShow = new TvShow();
                tvShow.setIdTv(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseContract.TvFav.COLOMN_TVID))));
                tvShow.setTitleTv(cursor.getString(cursor.getColumnIndex(DatabaseContract.TvFav.COLOMN_TITLE_TV)));
                tvShow.setOverviewTv(cursor.getString(cursor.getColumnIndex(DatabaseContract.TvFav.COLOMN_OVERVIEW_TV)));
                tvShow.setPosterpathTv(cursor.getString(cursor.getColumnIndex(COLOMN_POSTER_PATH_TV)));
                tvShow.setBackdroppathTv(cursor.getString(cursor.getColumnIndex(DatabaseContract.TvFav.COLOMN_BACKDROP_PATH_TV)));

                arrayList.add(tvShow);

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertTv(TvShow tvShow){
        ContentValues value = new ContentValues();
        value.put(COLOMN_TVID, tvShow.getIdTv());
        value.put(COLOMN_TITLE_TV, tvShow.getTitleTv());
        value.put(COLOMN_OVERVIEW_TV, tvShow.getOverviewTv());
        value.put(COLOMN_POSTER_PATH_TV, tvShow.getPosterpathTv());
        value.put(COLOMN_BACKDROP_PATH_TV, tvShow.getBackdroppathTv());
        return databaseTv.insert(DATABASE_TABLE, null, value);

    }

    public void deleteTv(int id) {
        databaseTv = dbHelperTv.getWritableDatabase();
        databaseTv.delete(DatabaseContract.TvFav.TABLE_NAME_TV, DatabaseContract.TvFav.COLOMN_TVID + "=" + id, null);
    }

    public boolean checkTv(String id) {
        databaseTv = dbHelperTv.getWritableDatabase();
        String select = "SELECT * FROM " + DatabaseContract.TvFav.TABLE_NAME_TV + " WHERE " + DatabaseContract.TvFav.COLOMN_TVID + " =?";
        Cursor cursor = databaseTv.rawQuery(select, new String[]{id});
        boolean checkTv = false;
        if (cursor.moveToFirst()) {
            checkTv = true;
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
            Log.d(TAG, String.format("%d records found", count));
        }
        cursor.close();
        return checkTv;
    }

}
