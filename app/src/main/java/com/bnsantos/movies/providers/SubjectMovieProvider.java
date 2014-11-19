package com.bnsantos.movies.providers;

import android.util.Log;

import com.bnsantos.movies.model.Movie;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by bruno on 19/11/14.
 */
public class SubjectMovieProvider extends MovieProvider {
    private final BehaviorSubject<List<Movie>> mMoviesSubject;
    private final Subscription mServerSubscription;
    private final Subscription mCacheSubscription;

    public SubjectMovieProvider() {
        mMoviesSubject = BehaviorSubject.create();

        mServerSubscription = cacheAndRetrieveMovies().subscribeOn(Schedulers.newThread()).subscribe(new Action1<List<Movie>>() {
            @Override
            public void call(List<Movie> movieList) {
                Log.d(TAG, "ServerObservable received movies");
                mMoviesSubject.onNext(movieList);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, "Fetching movies from server error", throwable);
                mMoviesSubject.onError(throwable);
            }
        }, new Action0() {
            @Override
            public void call() {
                Log.d(TAG, "ServerObservable completed");
                mMoviesSubject.onCompleted();
            }
        });

        mCacheSubscription = retrieveCachedMovies().subscribeOn(Schedulers.newThread()).subscribe(new Action1<List<Movie>>() {
            @Override
            public void call(List<Movie> movieList) {
                Log.d(TAG, "CacheObservable received movies");
                mMoviesSubject.onNext(movieList);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, "Fetching movies from cache error", throwable);
                mMoviesSubject.onError(throwable);
            }
        }, new Action0() {
            @Override
            public void call() {
                Log.d(TAG, "CacheObservable completed");
                mMoviesSubject.onCompleted();
            }
        });

    }

    public void unSubscribe() {
        mCacheSubscription.unsubscribe();
        mServerSubscription.unsubscribe();
    }

    public Observable<List<Movie>> subscribe() {
        return mMoviesSubject;
    }
}
