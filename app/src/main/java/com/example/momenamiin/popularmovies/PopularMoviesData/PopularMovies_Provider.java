package com.example.momenamiin.popularmovies.PopularMoviesData;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by momenamiin on 12/26/17.
 */

public class PopularMovies_Provider extends ContentProvider {

    public static final int CODE_MOVES = 200;
    public static final int CODE_MOVES_WITH_DATA = 201;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private PopularMoviesdbhelper mOpenhelper;


    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = PopularMovies_Contract.CONTENT_AUTHORITY;
        matcher.addURI(authority, PopularMovies_Contract.PATH_MOVIES, CODE_MOVES);
        matcher.addURI(authority, PopularMovies_Contract.PATH_MOVIES + "/*", CODE_MOVES_WITH_DATA);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mOpenhelper = new PopularMoviesdbhelper(context);
        return true;
    }
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("We are not implementing getType in Sunshine.");
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case CODE_MOVES: {
                cursor = mOpenhelper.getReadableDatabase().query(
                        PopularMovies_Contract.PopularMovies_Entry.TABLE_NAME,
                        strings,
                        s,
                        strings1,
                        null,
                        null,
                        s1);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mOpenhelper.getWritableDatabase();
        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case CODE_MOVES: {
                long id = db.insert(
                        PopularMovies_Contract.PopularMovies_Entry.TABLE_NAME,
                        null,
                        contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(PopularMovies_Contract.PopularMovies_Entry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        String Name = null ;
        int num_rows_deleted = 0;
        try {
            Name = uri.getPathSegments().get(1);
        }catch (Exception e){

        }
        switch (sUriMatcher.match(uri)) {
            case CODE_MOVES_WITH_DATA:
                num_rows_deleted = mOpenhelper.getWritableDatabase().delete(
                        PopularMovies_Contract.PopularMovies_Entry.TABLE_NAME,
                        "title=?",
                        new String[]{Name});
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return num_rows_deleted;    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
