package com.bnsantos.movies.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by bruno on 14/11/14.
 */
public class Movie {
    @DatabaseField(canBeNull = false, id = true)
    private String id;
    @DatabaseField()
    private String title;
    @DatabaseField()
    private int year;
    @DatabaseField()
    private String mpaa_rating;
    @DatabaseField()
    private int runtime;
    @DatabaseField()
    private String critics_consensus;
    @DatabaseField(foreign = true, canBeNull = false, foreignAutoCreate = true)
    private ReleaseDates release_dates;
    @DatabaseField(foreign = true, canBeNull = false, foreignAutoCreate = true)
    private Ratings ratings;
    @DatabaseField()
    private String synopsis;
    @DatabaseField(foreign = true, canBeNull = false, foreignAutoCreate = true)
    private Posters posters;
    @DatabaseField(foreign = true, canBeNull = false, foreignAutoCreate = true)
    private Links links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMpaa_rating() {
        return mpaa_rating;
    }

    public void setMpaa_rating(String mpaa_rating) {
        this.mpaa_rating = mpaa_rating;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getCritics_consensus() {
        return critics_consensus;
    }

    public void setCritics_consensus(String critics_consensus) {
        this.critics_consensus = critics_consensus;
    }

    public ReleaseDates getRelease_dates() {
        return release_dates;
    }

    public void setRelease_dates(ReleaseDates release_dates) {
        this.release_dates = release_dates;
    }

    public Ratings getRatings() {
        return ratings;
    }

    public void setRatings(Ratings ratings) {
        this.ratings = ratings;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Posters getPosters() {
        return posters;
    }

    public void setPosters(Posters posters) {
        this.posters = posters;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
}
