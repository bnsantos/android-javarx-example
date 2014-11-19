package com.bnsantos.movies.providers;

import com.bnsantos.movies.model.Movie;

import java.util.List;

import rx.Observable;

/**
 * Created by bruno on 19/11/14.
 */
public class LazyMovieProvider extends MovieProvider {
    public Observable<List<Movie>> subscribe() {
        return Observable.merge(retrieveCachedMovies(), cacheAndRetrieveMovies());
    }
}
