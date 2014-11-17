package com.bnsantos.movies.cache;

import android.content.Context;
import android.util.Log;

import com.bnsantos.movies.model.Movie;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by bruno on 14/11/14.
 */
public class MovieCaching {
    private final String TAG = MovieCaching.class.getName();
    private final PersistentManager mPersistentManager;

    public MovieCaching(Context context) {
        this.mPersistentManager = new PersistentManager(context);
    }

    public void cache(List<Movie> movieList) {
        //TODO Do in another thread
        for (Movie movie : movieList) {
            cache(movie);
        }
    }

    private void cache(Movie movie) {
        try {
            Log.d(TAG, "Caching movie[" + movie.getId() + "]" + mPersistentManager.getMovieDAO().create(movie));
            Log.d(TAG, "Caching movie release dates" + mPersistentManager.getReleaseDatesDAO().create(movie.getRelease_dates()));
            Log.d(TAG, "Caching movie ratings" + mPersistentManager.getRatingsDAO().create(movie.getRatings()));
            Log.d(TAG, "Caching movie posters" + mPersistentManager.getPostersDAO().create(movie.getPosters()));
            Log.d(TAG, "Caching movie link" + mPersistentManager.getLinksDAO().create(movie.getLinks()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> fetch() {
        try {
            return mPersistentManager.getMovieDAO().queryForAll();
        } catch (SQLException e) {
            return null;
        }
    }
}
