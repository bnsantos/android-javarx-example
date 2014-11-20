package com.bnsantos.movies.model;

import com.bnsantos.movies.Utils;

import java.util.List;

/**
 * Created by bruno on 17/11/14.
 */
public class MovieResponse {
    private int total;
    private List<Movie> movies;
    private String link_template;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public String getLink_template() {
        return link_template;
    }

    public void setLink_template(String link_template) {
        this.link_template = link_template;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MovieResponse
                && ((MovieResponse) o).total == total
                && Utils.StringUtils.stringCompare(((MovieResponse) o).link_template, link_template)
                && Utils.MovieUtils.movieListCompare(((MovieResponse) o).movies, movies);
    }
}
