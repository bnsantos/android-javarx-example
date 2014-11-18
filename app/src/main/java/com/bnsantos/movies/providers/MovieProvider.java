package com.bnsantos.movies.providers;

import android.util.Log;

import com.bnsantos.movies.App;
import com.bnsantos.movies.model.Movie;
import com.bnsantos.movies.model.MovieListType;
import com.bnsantos.movies.model.MovieResponse;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by bruno on 17/11/14.
 */
public class MovieProvider {
    private final String TAG = MovieProvider.class.getName();
    private final BehaviorSubject<List<Movie>> mMoviesSubject;
    private final Subscription mServerSubscription;
    private final Subscription mCacheSubscription;

    public MovieProvider() {
        mMoviesSubject = BehaviorSubject.create();

        mCacheSubscription = retrieveCachedMovies().subscribeOn(Schedulers.newThread()).subscribe(new Action1<List<Movie>>() {
            @Override
            public void call(List<Movie> movies) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "Cache got movies");
                mMoviesSubject.onNext(movies);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.d(TAG, "Cache error", throwable);
                mMoviesSubject.onError(throwable);
            }
        }, new Action0() {
            @Override
            public void call() {
                Log.d(TAG, "Cache completed");
            }
        });

        mServerSubscription = retrieveServerMovies().subscribeOn(Schedulers.newThread()).subscribe(new Action1<List<Movie>>() {
            @Override
            public void call(List<Movie> movieList) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "Server got movies");
                mMoviesSubject.onNext(movieList);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.d(TAG, "Server error", throwable);
                mMoviesSubject.onError(throwable);
            }
        }, new Action0() {
            @Override
            public void call() {
                Log.d(TAG, "Server completed");
            }
        });
    }

    private Observable<List<Movie>> retrieveServerMovies() {
        return App.getInstance().getMovieService().retrieveMovies(MovieListType.IN_THEATERS.name().toLowerCase(), App.getInstance().getApiToken(), 10, 1, "us").map(new Func1<MovieResponse, List<Movie>>() {
            @Override
            public List<Movie> call(MovieResponse movieResponse) {
                Log.d(TAG, "Mapping server response");
                return movieResponse.getMovies();
            }
        });
    }

    private Observable<List<Movie>> retrieveCachedMovies() {
        return Observable.create(new Observable.OnSubscribe<List<Movie>>() {
            @Override
            public void call(Subscriber<? super List<Movie>> subscriber) {
                Log.d(TAG, "Retrieving movies from cache");
                subscriber.onNext(App.getInstance().getMovieCaching().fetch());
                subscriber.onCompleted();
            }
        });
    }

    public void unSubscribe() {
        mCacheSubscription.unsubscribe();
        mServerSubscription.unsubscribe();
    }

    public Observable<List<Movie>> subscribe() {
        return Observable.merge(retrieveCachedMovies(), retrieveServerMovies());
        //return mMoviesSubject;
    }
}
