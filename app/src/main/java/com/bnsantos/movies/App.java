package com.bnsantos.movies;

import android.app.Application;

import com.bnsantos.movies.cache.MovieCaching;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by bruno on 14/11/14.
 */
public class App extends Application {
    private static App sInstance;
    private final String mEndPoint = "http://api.rottentomatoes.com/api/public/v1.0";
    private final String mApiToken = ""; //Your token here
    private MovieCaching mMovieCaching;
    private MovieProvider mProvider;

    private RestAdapter mRestAdapter;
    private MovieService mMovieService;

    public static App getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mMovieCaching = new MovieCaching(this);

        Gson gson = new GsonBuilder()
                .setDateFormat(Utils.Json.DATE_FORMAT)
                .create();

        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(mEndPoint)
                .setErrorHandler(new RestErrorHandler())
                .setConverter(new GsonConverter(gson))
                .build();
        mMovieService = mRestAdapter.create(MovieService.class);

        mProvider = new MovieProvider();
    }

    public String getEndPoint() {
        return mEndPoint;
    }

    public String getApiToken() {
        return mApiToken;
    }

    public MovieCaching getMovieCaching() {
        return mMovieCaching;
    }

    public MovieProvider getProvider() {
        return mProvider;
    }

    public MovieService getMovieService() {
        return mMovieService;
    }
}
