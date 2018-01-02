package com.example.momenamiin.popularmovies.PopularMoviesData;

/**
 * Created by momenamiin on 27/12/17.
 */

public class MovietralierData {

    private String mTralirName ;
    private String mTralirCode ;


    public MovietralierData(String TralirName , String TralirCode){
        mTralirName = TralirName ;
        mTralirCode = TralirCode ;
         }

    // geter Methods
    public String getmTralirName (){return mTralirName ;}
    public String getmTralirCode(){return mTralirCode ;}

    // SeterMethods
    public void setmTralirName(String name ){mTralirName = name;}
    public void setmTralirCode(String code){mTralirCode = code;}


}
