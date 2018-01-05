package com.example.momenamiin.popularmovies;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by momenamiin on 10/10/17.
 */

public class Netwokutils {

    public final static String Base_URL = "http://api.themoviedb.org/3/movie/" ;
    public final static String API_Key = "?api_key=47eb7c60a8dbdad32e028554514dd977";
public static URL URLbuilder (String type){
    URL url = null ;
    try {
        url = new URL(Base_URL + type + API_Key);
    }catch (MalformedURLException e){
        e.printStackTrace();
    }
    return url ;
}
public static String getResponseFromHttpURL(URL url) throws IOException{
    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
    try {
        InputStream in = httpURLConnection.getInputStream();
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter("\\A");
        boolean hasinput = scanner.hasNext();
        if (hasinput){
            return scanner.next();
        }else{
            return null;
        }
    }finally {
        httpURLConnection.disconnect();
    }
}
}
