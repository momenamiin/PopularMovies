package com.example.momenamiin.popularmovies;



import com.example.momenamiin.popularmovies.PopularMoviesData.MovietralierData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by momenamiin on 10/10/17.
 */

public final class popularmoviesJson {

    public static ArrayList<MoviesData> getMoviesData(String Jsondata)throws JSONException{
        // Strings decleration for Parsing Json
        final String Movie_Name = "title";
        final String Movie_Poster = "poster_path";
        final String Vote_avrg = "vote_average" ;
        final String Release_Data = "release_date" ;
        final String Movie_Polt = "overview" ;
        final String Movie_id = "id" ;

        // Variables to put the data In
        int  NumOfMovies ;

        ArrayList<MoviesData> moviesData ;


        // arrayList to store data and return it
        JSONObject jsonObject = new JSONObject(Jsondata);
        /* Is there an error? */
        if (jsonObject.has("cod")) {
            int errorCode = jsonObject.getInt("cod");
            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

            JSONArray array = jsonObject.getJSONArray("results");
            NumOfMovies = array.length();
        moviesData = new ArrayList<MoviesData>();

        for (int i = 0 ; i < NumOfMovies ; i++){
            String MoviePosterURL ;
            String MovieName ;
            String MovieVoteAvrg ;
            String MovieReleaseDate;
            String MoviePlot ;
            String MovieId ;

                JSONObject object = array.getJSONObject(i) ;
                MovieName  = object.getString(Movie_Name) ;
                MoviePlot = object.getString(Movie_Polt) ;
                MoviePosterURL = object.getString(Movie_Poster) ;
                MovieId = object.getString(Movie_id);
                MovieReleaseDate = object.getString(Release_Data) ;
                MovieVoteAvrg = object.getString(Vote_avrg) ;
                moviesData.add(new MoviesData(MovieName , MoviePosterURL ,MovieVoteAvrg , MovieReleaseDate ,MoviePlot , MovieId)) ;
            }
        return moviesData ;
    }

    public static String[] getMoviesReviews(String Jsondata)throws JSONException{
        String reviews[];
        int NumOfReviews ;
            JSONObject jsonObject = new JSONObject(Jsondata);
                    /* Is there an error? */
            if (jsonObject.has("cod")) {
                int errorCode = jsonObject.getInt("cod");
                switch (errorCode) {
                    case HttpURLConnection.HTTP_OK:
                        break;
                    case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                        return null;
                    default:
                    /* Server probably down */
                        return null;
                }
            }
            JSONArray array = jsonObject.getJSONArray("results");
            NumOfReviews = array.length();
            reviews = new String[NumOfReviews];
            for (int i = 0 ; i < NumOfReviews ; i++){
                String Review ;
                JSONObject object = array.getJSONObject(i) ;
                Review  = object.getString("content") ;
                reviews[i] = Review;
            }
        return reviews ;
    }

    public static ArrayList<MovietralierData> getMoviesTralies(String Jsondata)throws JSONException{
        ArrayList<MovietralierData> Tralirs ;
        int NumOfTralirs ;
            JSONObject jsonObject = new JSONObject(Jsondata);
        /* Is there an error? */
            if (jsonObject.has("cod")) {
                int errorCode = jsonObject.getInt("cod");
                switch (errorCode) {
                    case HttpURLConnection.HTTP_OK:
                        break;
                    case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                        return null;
                    default:
                    /* Server probably down */
                        return null;
                }
            }
            JSONArray array = jsonObject.getJSONArray("results");
            NumOfTralirs = array.length();
            Tralirs = new ArrayList<MovietralierData>();
            for (int i = 0 ; i < NumOfTralirs ; i++){
                String TralirCode ;
                String TralirName ;
                JSONObject object = array.getJSONObject(i) ;
                TralirName = object.getString("name");
                TralirCode  = object.getString("key") ;
                Tralirs.add(new MovietralierData(TralirName , TralirCode));
            }
        return Tralirs ;
    }

    public static MoviesData getMovieData(String Jsondata)throws JSONException{
        // Strings decleration for Parsing Json
        final String Movie_Name = "title";
        final String Movie_Poster = "poster_path";
        final String Vote_avrg = "vote_average" ;
        final String Release_Data = "release_date" ;
        final String Movie_Polt = "overview" ;
        final String Movie_id = "id" ;

        // Variables to put the data In

        MoviesData movieData ;

        // arrayList to store data and return it
        JSONObject object = new JSONObject(Jsondata);
        /* Is there an error? */
        if (object.has("cod")) {
            int errorCode = object.getInt("cod");
            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;}}

            String MoviePosterURL ;
            String MovieName ;
            String MovieVoteAvrg ;
            String MovieReleaseDate;
            String MoviePlot ;
            String MovieId ;

            MovieName  = object.getString(Movie_Name) ;
            MoviePlot = object.getString(Movie_Polt) ;
            MoviePosterURL = object.getString(Movie_Poster) ;
            MovieId = object.getString(Movie_id);
            MovieReleaseDate = object.getString(Release_Data) ;
            MovieVoteAvrg = object.getString(Vote_avrg) ;
            movieData = new MoviesData(MovieName , MoviePosterURL ,MovieVoteAvrg , MovieReleaseDate ,MoviePlot , MovieId) ;
        return movieData ;
    }

}
