package com.example.momenamiin.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.momenamiin.popularmovies.PopularMoviesData.MovietralierData;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by momenamiin on 12/26/17.
 */

public class VideoTralierAdapter extends RecyclerView.Adapter<VideoTralierAdapter.VideoTralierAdapterViewHolder> {

    private String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    ArrayList<MovietralierData> Tralirs;

    /*
  I get the Handler to update the ui from other thread From stack over flow
  Link : https://stackoverflow.com/questions/33418232/okhttp-update-ui-from-enqueue-callback
   */

private Handler mHandler ;
    public VideoTralierAdapter( String Movie_id) {

        MovieTrailers(Movie_id);
    }
    private void MovieTrailers(String id){
        mHandler = new Handler(Looper.getMainLooper());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Netwokutils.Base_URL + id + "/videos" + Netwokutils.API_Key)
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
                    Tralirs = popularmoviesJson.getMoviesTralies(Response);
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
    public VideoTralierAdapter.VideoTralierAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int itemListid = R.layout.tralirs_list_item;
        boolean shouldAttachToParentImmediately = false;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(itemListid,parent ,shouldAttachToParentImmediately);
        return new VideoTralierAdapterViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(VideoTralierAdapter.VideoTralierAdapterViewHolder holder, int position) {
        if(Tralirs == null){
            holder.mtextview.setText("No Tralirs");
            holder.mtextview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            holder.mImageiew.setVisibility(View.INVISIBLE);
        }else {
            holder.mImageiew.setVisibility(View.VISIBLE);
            String TralirName = Tralirs.get(position).getmTralirName();
            holder.mtextview.setText(TralirName);
        }
    }
    @Override
    public int getItemCount() {
        if (Tralirs != null){
        return Tralirs.size();}
        else return 1;
    }

    public class VideoTralierAdapterViewHolder extends RecyclerView.ViewHolder  {
        public final TextView mtextview;
        public final ImageView mImageiew;
        public VideoTralierAdapterViewHolder(View itemView) {
            super(itemView);
            mtextview = itemView.findViewById(R.id.Tralir_Name);
            mImageiew = itemView.findViewById(R.id.Tralir_Play_Button);
            mImageiew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int postion = getAdapterPosition();
                    String Key = Tralirs.get(postion).getmTralirCode();
                    Intent intent = new Intent(Intent.ACTION_VIEW ,Uri.parse(YOUTUBE_BASE_URL + Key));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
