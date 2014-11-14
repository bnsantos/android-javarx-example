package com.bnsantos.movies.controller.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.bnsantos.movies.MovieListType;
import com.bnsantos.movies.Utils;
import com.bnsantos.movies.model.Movie;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruno on 14/11/14.
 */
public class RetrieveMoviesRequest extends Request<List<Movie>> {
    private final Response.Listener<List<Movie>> mListener;

    public RetrieveMoviesRequest(MovieListType movieListType, int limit, Response.ErrorListener errorListener, Response.Listener<List<Movie>> listener) {
        super(Method.GET, Utils.Url.getMoviesListUrl(movieListType, Utils.UrlParams.pageLimit(limit)), errorListener);
        mListener = listener;
    }

    @Override
    protected Response<List<Movie>> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            //For now ignoring other fields from response
            String raw = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            List<Movie> movieList = Utils.Json.fromJson(Utils.Json.extractJsonField(raw, "movies"), new TypeToken<List<Movie>>() {
            }.getType());
            return Response.success(movieList, HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(networkResponse));
        }
    }

    @Override
    protected void deliverResponse(List<Movie> movies) {
        mListener.onResponse(movies);
    }
}
