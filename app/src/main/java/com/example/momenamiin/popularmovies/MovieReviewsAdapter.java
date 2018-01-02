package com.example.momenamiin.popularmovies;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.json.JSONException;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by momenamiin on 12/26/17.
 */

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewsAdapterViewHolder> {
    String[] Reviews;
    /*
    I get the Handler to update the ui from other thread From stack over flow
    Link : https://stackoverflow.com/questions/33418232/okhttp-update-ui-from-enqueue-callback
     */
    private Handler mHandler ;


    public MovieReviewsAdapter (String Movie_id){
        MovieReview(Movie_id);
    }

    private void MovieReview(String id){
        mHandler = new Handler(Looper.getMainLooper());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Netwokutils.Base_URL + id + "/reviews" + Netwokutils.API_Key)
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
                    Reviews = popularmoviesJson.getMoviesReviews(Response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }
    @Override
    public MovieReviewsAdapter.MovieReviewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int itemListid = R.layout.reviews_list_item;
        boolean shouldAttachToParentImmediately = false;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(itemListid,parent ,shouldAttachToParentImmediately);
        return new MovieReviewsAdapterViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(MovieReviewsAdapter.MovieReviewsAdapterViewHolder holder, int position) {
        if (Reviews == null){
            holder.mtextView.setText("No Reviews to View");
            holder.mtextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            holder.mtextView.setTextSize(22);
        }else {
            String Review = Reviews[position];
            holder.mtextView.setText(Review);
        }
    }

    @Override
    public int getItemCount() {
        if (Reviews != null){
            return Reviews.length;}
        else return 1 ;
    }

    public class MovieReviewsAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView mtextView ;
        public MovieReviewsAdapterViewHolder(View itemView) {
            super(itemView);
            mtextView = itemView.findViewById(R.id.Reviews_text_view);
        }
    }
}
