package com.bnsantos.movies;

import com.bnsantos.movies.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruno on 20/11/14.
 */
public class TestUtils {
    public static List<Movie> movieListGenerator(int size) {
        List<Movie> movieList = new ArrayList<Movie>();
        for (int i = 0; i < size; i++) {
            movieList.add(movieGenerator(i));
        }
        return movieList;
    }

    public static Movie movieGenerator(int index) {
        Movie movie = new Movie();
        movie.setId(Integer.toString(index));
        movie.setTitle("MovieTest_" + index);
        movie.setRuntime((int) (Math.random() * 1000));
        movie.setSynopsis("MotiveTestSynopsis_" + index);
        movie.setYear((int) (Math.random() * 1000));
        movie.setCritics_consensus("MotiveTestCriticsConsensus_" + index);
        return movie;
    }
}
