package com.example.momenamiin.popularmovies;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.momenamiin.popularmovies.PopularMoviesData.PopularMovies_Contract;
import com.squareup.picasso.Picasso;

public class DetalisActivity extends AppCompatActivity implements  android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {
    private final String BASE_URL = "http://image.tmdb.org/t/p/" ;
    private final String Photo_Size = "w185/" ;
    private MoviesData moviesData ;
    private Bundle bundle ;
    private TextView Movie_name ;
    private TextView Release_Date ;
    private TextView Plot ;
    private TextView Avrg_Rate ;
    private ImageView Poster_Image ;
    private String Movie_id ;
    private RecyclerView TralirsRecyclerView ;
    private RecyclerView reviewsRecyclerView ;
    private VideoTralierAdapter mVideoTralierAdapter ;
    private MovieReviewsAdapter mMovieReviewsAdapter ;
    private Button favourit_Button ;
    private boolean favorit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalis);
        getSupportLoaderManager().initLoader(10 , null , this);
        favourit_Button = (Button)findViewById(R.id.favoritbutton);
        TralirsRecyclerView = (RecyclerView)findViewById(R.id.Tralirs_RecyclerView);
        reviewsRecyclerView = (RecyclerView)findViewById(R.id.Reviews_RecyclerView);
        Movie_name = (TextView)findViewById(R.id.Movie_Name);
        Release_Date = (TextView)findViewById(R.id.ReleaseDate_Text);
        Plot = (TextView)findViewById(R.id.Plot_Text);
        Avrg_Rate = (TextView)findViewById(R.id.AvrgRate_Text);
        Poster_Image = (ImageView)findViewById(R.id.Poster_Image);
        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra("Movies data")){
                bundle = intent.getExtras();
                moviesData = bundle.getParcelable("Movies data");
                Movie_name.setText(moviesData.getmMoveName());
                Release_Date.setText(moviesData.getmMovereleaseDate());
                Plot.setText(moviesData.getmMoveplot());
                Movie_id = moviesData.getmMoveid();
                Avrg_Rate.setText(moviesData.getmMoveVoteAvrg());
                Picasso.with(this).load(BASE_URL+Photo_Size+moviesData.getmMovePosterURL()).into(Poster_Image);
                mVideoTralierAdapter = new VideoTralierAdapter(Movie_id);
                mMovieReviewsAdapter = new MovieReviewsAdapter(Movie_id);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL ,false);
                LinearLayoutManager layoutManager2 = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL ,false);
                TralirsRecyclerView.setAdapter(mVideoTralierAdapter);
                TralirsRecyclerView.setLayoutManager(layoutManager);
                TralirsRecyclerView.setHasFixedSize(true);
                TralirsRecyclerView.setNestedScrollingEnabled(false);
                reviewsRecyclerView.setAdapter(mMovieReviewsAdapter);
                reviewsRecyclerView.setLayoutManager(layoutManager2);
                reviewsRecyclerView.setHasFixedSize(true);
                reviewsRecyclerView.setNestedScrollingEnabled(false);
            }
        }
        favourit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favorit == false) {
                    ContentValues Movie_Content_Value = new ContentValues();
                    Movie_Content_Value.put(PopularMovies_Contract.PopularMovies_Entry.Movie_title, moviesData.getmMoveName());
                    Movie_Content_Value.put(PopularMovies_Contract.PopularMovies_Entry.Movie_id, moviesData.getmMoveid());
                    Uri uri = getContentResolver().insert(PopularMovies_Contract.PopularMovies_Entry.CONTENT_URI, Movie_Content_Value);
                    if (uri != null) {
                        favorit = true;
                        favourit_Button.setBackgroundColor(getResources().getColor(R.color.buttonGold));
                    }

                }else {
                    Uri uri = PopularMovies_Contract.PopularMovies_Entry.CONTENT_URI ;
                    uri = uri.buildUpon().appendPath(moviesData.getmMoveName()).build();
                    int rows_deleted = getContentResolver().delete(uri , null , null);
                    if (rows_deleted != 0){
                        favorit = false ;
                        favourit_Button.setBackgroundColor(getResources().getColor(R.color.buttonGray));

                    }
                }
            }
        });

    }
    @SuppressLint("StaticFieldLeak")
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<Cursor>(this) {
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

                String Projection[] = {PopularMovies_Contract.PopularMovies_Entry.Movie_title};
                String selection = PopularMovies_Contract.PopularMovies_Entry.Movie_title +"='"+moviesData.getmMoveName()+"'";
                try {
                    return getContentResolver().query(PopularMovies_Contract.PopularMovies_Entry.CONTENT_URI,
                            Projection, selection, null, null);
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
        if (data.getCount() != 0){
            favorit = true ;
                favourit_Button.setBackgroundColor(getResources().getColor(R.color.buttonGold));
        }else {
            favorit = false ;
            favourit_Button.setBackgroundColor(getResources().getColor(R.color.buttonGray));
        }
    }
    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {}
}