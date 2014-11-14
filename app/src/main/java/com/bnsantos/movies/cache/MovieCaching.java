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
        for (Movie movie : movieList) {
            cache(movie);
        }
    }

    private void cache(Movie movie) {
        try {
            Log.d(TAG, "Caching movie[" + movie.getId() + "]" + mPersistentManager.getMovieDAO().create(movie));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> fetch() {
        //TODO
        return null;
    }
}
