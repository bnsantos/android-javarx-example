package com.bnsantos.movies.services;

import com.bnsantos.movies.model.MovieResponse;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by bruno on 17/11/14.
 */
public interface MovieService {
    @GET("/lists/movies/{type}.json")
    Observable<MovieResponse> retrieveMovies(@Path("type") String type,
                                             @Query("apikey") String token,
                                             @Query("page_limit") int limit,
                                             @Query("page") int page,
                                             @Query("country") String country);
}
