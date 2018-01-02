package com.example.momenamiin.popularmovies.PopularMoviesData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by momenamiin on 12/26/17.
 */

public class PopularMoviesdbhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "moviesDb.dp";
    private static final int  DATABASE_VERSION = 1 ;


    public PopularMoviesdbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_POPLAR_MOVIES_TABLE =
                "CREATE TABLE " +
                        PopularMovies_Contract.PopularMovies_Entry.TABLE_NAME +
                        " (" +
                        PopularMovies_Contract.PopularMovies_Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        PopularMovies_Contract.PopularMovies_Entry.Movie_title + " TEXT NOT NULL, " +
                        PopularMovies_Contract.PopularMovies_Entry.Movie_id + " INTEGER NOT NULL);" ;
        sqLiteDatabase.execSQL(SQL_CREATE_POPLAR_MOVIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PopularMovies_Contract.PopularMovies_Entry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
