package com.bnsantos.movies.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by bruno on 14/11/14.
 */
public class Posters {
    @DatabaseField()
    private String thumbnail;
    @DatabaseField()
    private String profile;
    @DatabaseField()
    private String detailed;
    @DatabaseField()
    private String original;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getDetailed() {
        return detailed;
    }

    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
}


