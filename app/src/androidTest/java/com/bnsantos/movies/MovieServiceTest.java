package com.bnsantos.movies;

import com.bnsantos.movies.model.Movie;
import com.bnsantos.movies.model.MovieListType;
import com.bnsantos.movies.model.MovieResponse;
import com.bnsantos.movies.services.MovieService;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by bruno on 19/11/14.
 */
@Config(emulateSdk = 18)
@RunWith(MoviesTestRunner.class)
public class MovieServiceTest {
    MovieResponse mMovieResponse;
    private MovieService mMovieService;

    @Before
    public void setUp() throws Exception {
        mMovieService = Mockito.mock(MovieService.class);
        mMovieResponse = new MovieResponse();
        mMovieResponse.setLink_template("LinkTemplate");
        mMovieResponse.setMovies(TestUtils.movieListGenerator(10));
    }

    @Test
    public void testFetchingMovies() throws Exception {
        Observable<MovieResponse> responseObservable = Observable.create(new Observable.OnSubscribe<MovieResponse>() {
            @Override
            public void call(Subscriber<? super MovieResponse> subscriber) {
                subscriber.onNext(mMovieResponse);
                subscriber.onCompleted();
            }
        });
        Mockito.when(mMovieService.retrieveMovies(MovieListType.IN_THEATERS.name().toLowerCase(), "token", 10, 1, "us")).thenReturn(responseObservable);
        MovieResponse response = mMovieService.retrieveMovies(MovieListType.IN_THEATERS.name().toLowerCase(), "token", 10, 1, "us").toBlocking().single();
        Assert.assertEquals(response, mMovieResponse);
    }

    @Test
    public void testFetchingEmptyMovies() throws Exception {
        mMovieResponse.setMovies(new ArrayList<Movie>());
        Observable<MovieResponse> responseObservable = Observable.create(new Observable.OnSubscribe<MovieResponse>() {
            @Override
            public void call(Subscriber<? super MovieResponse> subscriber) {
                subscriber.onNext(mMovieResponse);
                subscriber.onCompleted();
            }
        });
        Mockito.when(mMovieService.retrieveMovies(MovieListType.IN_THEATERS.name().toLowerCase(), "token", 10, 1, "us")).thenReturn(responseObservable);

        MovieResponse response = mMovieService.retrieveMovies(MovieListType.IN_THEATERS.name().toLowerCase(), "token", 10, 1, "us").toBlocking().single();
        Assert.assertEquals(response, mMovieResponse);
    }

    @Test
    public void testFetchingMoviesError() throws Exception {
        Observable<MovieResponse> responseObservable = Observable.create(new Observable.OnSubscribe<MovieResponse>() {
            @Override
            public void call(Subscriber<? super MovieResponse> subscriber) {
                subscriber.onError(new Exception("InvalidToken"));
            }
        });
        Mockito.when(mMovieService.retrieveMovies(MovieListType.IN_THEATERS.name().toLowerCase(), "", 10, 1, "us")).thenReturn(responseObservable);
        Observable<MovieResponse> movieResponseObservable = mMovieService.retrieveMovies(MovieListType.IN_THEATERS.name().toLowerCase(), "", 10, 1, "us");
        Assert.assertNotNull(movieResponseObservable);
        movieResponseObservable.subscribe(new Action1<MovieResponse>() {
            @Override
            public void call(MovieResponse movieResponse) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Assert.assertNotNull(throwable);
                Assert.assertEquals(throwable.getMessage(), "InvalidToken");
            }
        }, new Action0() {
            @Override
            public void call() {
                Assert.assertEquals(true, true);
            }
        });
    }
}
