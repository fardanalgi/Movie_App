package com.dicoding.picodiploma.mybottomnavigation.database;

import android.provider.BaseColumns;

public class DatabaseContract {



    static final class MovieFav implements BaseColumns {

        static String TABLE_NAME = "movie";
        public static final String COLOMN_MOVIEID = "movieid";
        static String COLOMN_TITLE = "title";
        static String COLOMN_OVERVIEW = "overview";
        static String COLOMN_POSTER_PATH = "posterpath";
        static String COLOMN_BACKDROP_PATH = "backdroppath";

    }



    static final class TvFav implements BaseColumns {

        static String TABLE_NAME_TV = "tv";
        static String COLOMN_TVID = "tv_id";
        static String COLOMN_TITLE_TV = "title";
        static String COLOMN_OVERVIEW_TV = "overview";
        static String COLOMN_POSTER_PATH_TV = "posterpath";
        static String COLOMN_BACKDROP_PATH_TV = "backdroppath";

    }

}
