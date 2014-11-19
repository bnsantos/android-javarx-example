package com.bnsantos.movies.providers;

import android.util.Log;

import com.bnsantos.movies.App;
import com.bnsantos.movies.model.Movie;
import com.bnsantos.movies.model.MovieListType;
import com.bnsantos.movies.model.MovieResponse;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by bruno on 17/11/14.
 */
public abstract class MovieProvider {
    protected final String TAG = MovieProvider.class.getName();

    private Observable<List<Movie>> retrieveServerMovies() {
        return App.getInstance().getMovieService().retrieveMovies(MovieListType.IN_THEATERS.name().toLowerCase(), App.getInstance().getApiToken(), 10, 1, "us")
                .map(new Func1<MovieResponse, List<Movie>>() {
                    @Override
                    public List<Movie> call(MovieResponse movieResponse) {
                        Log.d(TAG, "Mapping server response");
                        return movieResponse.getMovies();
                    }
                });
    }

    protected Observable<List<Movie>> cacheAndRetrieveMovies() {
        return retrieveServerMovies().flatMap(new Func1<List<Movie>, Observable<List<Movie>>>() {
            @Override
            public Observable<List<Movie>> call(List<Movie> movieList) {
                Log.d(TAG, "Caching movies fetched from server");
                App.getInstance().getMovieCaching().cache(movieList);
                return Observable.create(new Observable.OnSubscribe<List<Movie>>() {
                    @Override
                    public void call(Subscriber<? super List<Movie>> subscriber) {
                        Log.d(TAG, "Emitting movies cached");
                        subscriber.onNext(App.getInstance().getMovieCaching().fetch());
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }

    protected Observable<List<Movie>> retrieveCachedMovies() {
        return Observable.create(new Observable.OnSubscribe<List<Movie>>() {
            @Override
            public void call(Subscriber<? super List<Movie>> subscriber) {
                Log.d(TAG, "Retrieving movies from cache");
                subscriber.onNext(App.getInstance().getMovieCaching().fetch());
                subscriber.onCompleted();
            }
        });
    }

    public abstract Observable<List<Movie>> subscribe();
}
