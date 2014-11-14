package com.bnsantos.movies.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by bruno on 14/11/14.
 */
public class Links {
    @DatabaseField()
    private String self;
    @DatabaseField()
    private String alternate;
    @DatabaseField()
    private String cast;
    @DatabaseField()
    private String reviews;
    @DatabaseField()
    private String similar;

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getAlternate() {
        return alternate;
    }

    public void setAlternate(String alternate) {
        this.alternate = alternate;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getSimilar() {
        return similar;
    }

    public void setSimilar(String similar) {
        this.similar = similar;
    }
}
