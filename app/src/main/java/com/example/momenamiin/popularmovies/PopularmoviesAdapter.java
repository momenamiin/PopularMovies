package com.example.momenamiin.popularmovies;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by momenamiin on 09/10/17.
 */

public class PopularmoviesAdapter extends RecyclerView.Adapter<PopularmoviesAdapter.PopularmoviesAdapterViewHolder>{

    private final String BASE_URL = "http://image.tmdb.org/t/p/" ;
    private final String Photo_Size = "w185/" ;

    private ArrayList<MoviesData> mMoviesDatas ;

    private final PopularMoviesAdapterOnclickHandler mClickHandler ;

    // Constractor of the class with Interface object


    public PopularmoviesAdapter(PopularMoviesAdapterOnclickHandler Clickhandler) {
        mClickHandler = Clickhandler;
    }

    // Click Handler Interface
    public interface PopularMoviesAdapterOnclickHandler{
        void oncick(MoviesData moviesDatas);
    }


    // View Holder Part
    public class PopularmoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Views Decleration
        public final ImageView mimageView ;

        public PopularmoviesAdapterViewHolder(View itemView) {
            super(itemView);
            mimageView = itemView.findViewById(R.id.item_image);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int adapterpostion = getAdapterPosition() ;
            MoviesData moviesData = mMoviesDatas.get(adapterpostion);
            mClickHandler.oncick(moviesData);
        }
    }
    @Override
    public PopularmoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int itemListid = R.layout.item;
        boolean shouldAttachToParentImmediately = false;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(itemListid,parent ,shouldAttachToParentImmediately);
        return new PopularmoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PopularmoviesAdapterViewHolder holder, int position) {
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int hight = Resources.getSystem().getDisplayMetrics().heightPixels;
        MoviesData moviesData = mMoviesDatas.get(position) ;
        Picasso.with(holder.mimageView.getContext()).load(BASE_URL+Photo_Size+moviesData.getmMovePosterURL()).resize(width/2 , hight/2).into(holder.mimageView);
    }

    @Override
    public int getItemCount() {
        if (mMoviesDatas == null){
            return 0 ;
        }else {
            return mMoviesDatas.size();
        }
    }
    public ArrayList<MoviesData> getmMoviesDatas(){
        return mMoviesDatas ;
    }

    // Geting data part

public void setmMoviesDatas(ArrayList<MoviesData> MoviesData){
    mMoviesDatas = MoviesData ;
    notifyDataSetChanged();
}

}
