package com.bnsantos.movies.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by bruno on 14/11/14.
 */
public class Ratings {
    @DatabaseField()
    private String critics_rating;
    @DatabaseField()
    private int critics_score;
    @DatabaseField()
    private String audience_rating;
    @DatabaseField()
    private int audience_score;

    public String getCritics_rating() {
        return critics_rating;
    }

    public void setCritics_rating(String critics_rating) {
        this.critics_rating = critics_rating;
    }

    public int getCritics_score() {
        return critics_score;
    }

    public void setCritics_score(int critics_score) {
        this.critics_score = critics_score;
    }

    public String getAudience_rating() {
        return audience_rating;
    }

    public void setAudience_rating(String audience_rating) {
        this.audience_rating = audience_rating;
    }

    public int getAudience_score() {
        return audience_score;
    }

    public void setAudience_score(int audience_score) {
        this.audience_score = audience_score;
    }
}
