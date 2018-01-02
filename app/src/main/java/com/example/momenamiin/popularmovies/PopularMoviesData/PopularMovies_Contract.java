package com.example.momenamiin.popularmovies.PopularMoviesData;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by momenamiin on 12/26/17.
 */

public class PopularMovies_Contract  {
    public static final String CONTENT_AUTHORITY = "com.example.momenamiin.popularmovies";
    public static final Uri Base_Uri = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    public static final class PopularMovies_Entry implements BaseColumns{

        public static final Uri CONTENT_URI = Base_Uri.buildUpon()
                .appendPath(PATH_MOVIES)
                .build();
        public static final String TABLE_NAME = "movies";
        public static final String Movie_title = "title";
        public static final String Movie_id = "id";
    }

}
