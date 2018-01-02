package com.example.momenamiin.popularmovies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.momenamiin.popularmovies.PopularMoviesData.Load_favourit_data;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PopularmoviesAdapter.PopularMoviesAdapterOnclickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();
    ArrayList<MoviesData> MoviesData ;
    private RecyclerView mRecyclerView ;
    private PopularmoviesAdapter madapter ;
    private TextView merrorMassage ;
    private ProgressBar mprogressBar ;
    private String type = "popular" ;


    public static String REFRESH_ACTIVITY = "com.domain.action.REFRESH_UI" ;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            MoviesData = intent.getParcelableArrayListExtra("Movies");
            madapter.setmMoviesDatas(MoviesData);
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("Movies", madapter.getmMoviesDatas());
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        merrorMassage = (TextView)findViewById(R.id.error_text);
        mprogressBar = (ProgressBar)findViewById(R.id.progress_bar);
        madapter = new PopularmoviesAdapter(this);
        mRecyclerView.setAdapter(madapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this , 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("Movies")) {
                loadMoviesData(1);
                ArrayList<MoviesData> movies = savedInstanceState.getParcelableArrayList("Movies");
                madapter.setmMoviesDatas(movies);
            }
        }else {
            loadMoviesData(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(REFRESH_ACTIVITY);
        this.registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(broadcastReceiver);
    }

    private void loadMoviesData(int i) {
        if (i == 0) {
            ShowMoviesdataView();
            new FetchMovies().execute(type);
        }else {
            ShowMoviesdataView();
            mprogressBar.setVisibility(View.INVISIBLE);
        }
    }
    private void ShowMoviesdataView(){
        merrorMassage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
    private void ShowErrorMassage(){
        merrorMassage.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }
    @Override
    public void oncick(MoviesData moviesDatas) {
        Context contect = this ;
        Class destiationclass = DetalisActivity.class;
        Intent intenttostartactivity = new Intent(contect , destiationclass);
        intenttostartactivity.putExtra("Movies data",moviesDatas);
        startActivity(intenttostartactivity);

    }
    // AsyncTask Work
    public class FetchMovies extends AsyncTask<String,Void,ArrayList<MoviesData>>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            mprogressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected ArrayList<MoviesData> doInBackground(String... prams) {
            if (prams.length == 0){
                return null ;
            }
            String type = prams[0];
            URL url = Netwokutils.URLbuilder(type);
            try{
                String Jsonresponse = Netwokutils.getResponseFromHttpURL(url) ;
                ArrayList<MoviesData> MoviesData = popularmoviesJson.getMoviesData(Jsonresponse);
                return MoviesData ;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(ArrayList<MoviesData> movedata){
            mprogressBar.setVisibility(View.INVISIBLE);
            MoviesData = movedata ;
            if (movedata != null){
                ShowMoviesdataView();
                madapter.setmMoviesDatas(movedata);
            }else {
                ShowErrorMassage();
            }
        }
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.popular){
            type = "popular" ;
            loadMoviesData(0);
        }if(id == R.id.rated){
            type = "top_rated";
            loadMoviesData(0);
        }
        if(id == R.id.favourit){
            Load_favourit_data load_favourit_data = new Load_favourit_data(this , getSupportLoaderManager());
        }
        return super.onOptionsItemSelected(item);
    }
}
