package com.bnsantos.movies;

import android.app.Application;

import com.bnsantos.movies.cache.MovieCaching;
import com.bnsantos.movies.providers.MovieProvider;
import com.bnsantos.movies.services.MovieService;
import com.bnsantos.movies.services.RestErrorHandler;

import java.lang.reflect.Type;

import retrofit.RestAdapter;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

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
        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(mEndPoint)
                .setErrorHandler(new RestErrorHandler())
                .setConverter(new GsonConverter(Utils.Json.releaseDatesGsonConverter()))
                .build();
        mMovieService = mRestAdapter.create(MovieService.class);
    }

    public String getApiToken() {
        return mApiToken;
    }

    public MovieCaching getMovieCaching() {
        return mMovieCaching;
    }

    public MovieProvider getProvider() {
        if (mProvider == null) {
            mProvider = new MovieProvider();
        }
        return mProvider;
    }

    public void clearProvider() {
        mProvider.unSubscribe();
        mProvider = null;
    }

    public MovieService getMovieService() {
        return mMovieService;
    }
}
