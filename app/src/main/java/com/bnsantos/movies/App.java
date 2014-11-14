package com.bnsantos.movies;

import android.app.Application;

import com.bnsantos.movies.controller.RestController;

/**
 * Created by bruno on 14/11/14.
 */
public class App extends Application {
    private static App sInstance;
    private final String mEndPoint = "http://api.rottentomatoes.com/api/public/v1.0/";
    private final String mApiToken = ""; //Your token here
    private RestController mRestController;

    public static App getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRestController = new RestController(this);
        sInstance = this;
    }

    public String getEndPoint() {
        return mEndPoint;
    }

    public RestController getRestController() {
        return mRestController;
    }


    public String getApiToken() {
        return mApiToken;
    }
}
