package com.example.momenamiin.popularmovies.PopularMoviesData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import com.example.momenamiin.popularmovies.MainActivity;
import com.example.momenamiin.popularmovies.MoviesData;
import com.example.momenamiin.popularmovies.Netwokutils;
import com.example.momenamiin.popularmovies.popularmoviesJson;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by momenamiin on 12/27/17.
 */

public class Load_favourit_data  implements  android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {
    public MoviesData movedata ;
    Cursor cursor ;
    String[] Movies_id ;
    private ArrayList<MoviesData> MoviesData ;

    Context mContext ;
    public Load_favourit_data (Context context , android.support.v4.app.LoaderManager loaderManager){
        mContext = context ;
        loaderManager.initLoader(11 , null , this);
    }

    public void getfavourtMoviesData(){
        int count = 0 ;
        if (cursor != null) {
            count = cursor.getCount();
            Movies_id = new String[count];
            for (int i =0 ; i < count ; i++){
                cursor.moveToPosition(i);
                Movies_id[i] = cursor.getString(cursor.getColumnIndex(PopularMovies_Contract.PopularMovies_Entry.Movie_id));
                getmoviedata(Movies_id[i]);
            }
        }else {
        }
    }

    private void getmoviedata(String id){
        MoviesData = new ArrayList<MoviesData>();
        OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Netwokutils.Base_URL + id + Netwokutils.API_Key)
                    .get()
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    return;
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String Response = response.body().string() ;
                    try {
                        movedata = popularmoviesJson.getMovieData(Response);
                        MoviesData.add(movedata) ;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (MoviesData.size() == cursor.getCount()) {
                        Intent intent = new Intent();
                        intent.putParcelableArrayListExtra("Movies" , MoviesData);
                        intent.setAction(MainActivity.REFRESH_ACTIVITY);
                        mContext.sendBroadcast(intent);
                    }
                }
            });
    }

    @SuppressLint("StaticFieldLeak")
    public AsyncTaskLoader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<Cursor>(mContext) {
            Cursor mCursor = null;
            @Override
            protected void onStartLoading() {
                if (mCursor != null) {
                    deliverResult(mCursor);
                } else {
                    forceLoad();
                }
            }
            @Override
            public Cursor loadInBackground() {
                try {
                    return mContext.getContentResolver().query(PopularMovies_Contract.PopularMovies_Entry.CONTENT_URI,
                            null, null, null, null);

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            @Override
            public void deliverResult(Cursor data) {
                super.deliverResult(data);
            }
        };

    }
    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        cursor = data ;
        getfavourtMoviesData();
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
    }

}
