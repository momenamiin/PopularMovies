package com.example.momenamiin.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by momenamiin on 10/10/17.
 */

/*
The toutorial of Parcel & Parcelable
http://shri.blog.kraya.co.uk/2010/04/26/android-parcel-data-to-pass-between-activities-using-parcelable-classes/
 */

public class MoviesData implements Parcelable {

    private String mMoveName ;
    private String mMovePosterURL ;
    private String mMoveVoteAvrg ;
    private String mMovereleaseDate ;
    private String mMoveplot ;
    private String mMoveid ;

    public MoviesData(String MoveName , String MovePosterURL , String MoveVoteAvrg , String MovereleaseDate,
                      String Moveplot , String Moveid ){

        mMoveName = MoveName ;
        mMovePosterURL = MovePosterURL ;
        mMoveVoteAvrg = MoveVoteAvrg ;
        mMovereleaseDate = MovereleaseDate ;
        mMoveplot = Moveplot ;
        mMoveid = Moveid;
    }
    public MoviesData(Parcel in){
        readFromParcel(in);
    }
    // geter Methods
    public String getmMoveName (){return mMoveName ;}
    public String getmMovePosterURL(){return mMovePosterURL ;}
    public String getmMoveVoteAvrg(){return mMoveVoteAvrg;}
    public String getmMovereleaseDate(){return mMovereleaseDate ;}
    public String getmMoveplot(){ return mMoveplot;}
    public String getmMoveid(){ return mMoveid;}

    // SeterMethods
    public void setmMoveName(String name ){mMoveName = name;}
    public void setmMovePosterURL(String poster){mMovePosterURL = poster;}
    public void setmMoveVoteAvrg(String avrg ) {mMoveVoteAvrg = avrg ;}
    public void setmMovereleaseDate (String data) {mMovereleaseDate = data ;}
    public void setmMoveplot (String plot) {mMoveplot = plot ;}
    public void setmMoveid (String id) {mMoveid = id ;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mMoveName);
        parcel.writeString(mMoveplot);
        parcel.writeString(mMovePosterURL);
        parcel.writeString(mMoveVoteAvrg);
        parcel.writeString(mMovereleaseDate);
        parcel.writeString(mMoveid);



    }
    private void readFromParcel(Parcel in){
        mMoveName = in.readString();
        mMoveplot = in.readString();
        mMovePosterURL = in.readString();
        mMoveVoteAvrg = in.readString();
        mMovereleaseDate = in.readString();
        mMoveid = in.readString();

    }
    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator(){
                public MoviesData createFromParcel(Parcel in){
                    return new MoviesData(in);
                }
                public MoviesData[] newArray(int size){
                    return new MoviesData[size];
                }

            };
}
