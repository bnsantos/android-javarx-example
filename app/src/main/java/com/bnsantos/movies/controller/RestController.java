package com.bnsantos.movies.controller;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by bruno on 14/11/14.
 */
public class RestController {
    private RequestQueue mRequestQueue;

    public RestController(Context context) {
        this.mRequestQueue = Volley.newRequestQueue(context);
    }

    public <T> void addRequest(Request<T> request, String tag) {
        request.addMarker(tag);
        mRequestQueue.add(request);
    }

    public void cancelPendingRequest(String tag) {
        if (mRequestQueue != null && tag != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
