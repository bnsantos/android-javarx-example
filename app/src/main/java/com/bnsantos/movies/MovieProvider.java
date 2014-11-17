package com.bnsantos.movies;

import android.util.Log;

import com.bnsantos.movies.model.Movie;
import com.bnsantos.movies.model.MovieResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.internal.operators.OnSubscribeDelay;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by bruno on 17/11/14.
 */
public class MovieProvider {
    private final String TAG = MovieProvider.class.getName();
    private final BehaviorSubject<List<Movie>> mMoviesSubject;
    private final Subscription mServerSubscription;


    public MovieProvider() {
        mMoviesSubject = BehaviorSubject.create(App.getInstance().getMovieCaching().fetch());
        mMoviesSubject.subscribeOn(Schedulers.io());

        mServerSubscription = getMovieList();
    }

    public Observable<List<Movie>> subscribe() {
        observe();
        return mMoviesSubject;
    }

    private Subscription getMovieList() {
        return App.getInstance().getMovieService().retrieveMovies(
                MovieListType.IN_THEATERS.name().toLowerCase(),
                App.getInstance().getApiToken(),
                10,
                1,
                "us").subscribe(new Action1<MovieResponse>() {
            @Override
            public void call(MovieResponse response) {
                Log.i(TAG, "Rest received response");
                App.getInstance().getMovieCaching().cache(response.getMovies());
                mMoviesSubject.onNext(App.getInstance().getMovieCaching().fetch());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, "Error", throwable);
                mMoviesSubject.onError(throwable);
            }
        }, new Action0() {
            @Override
            public void call() {
                Log.i(TAG, "Rest completed");
                mMoviesSubject.onCompleted();
            }
        });
    }

    private void observe() {
        mMoviesSubject.observeOn(Schedulers.io()).subscribe();
    }
}
