package com.bnsantos.movies.model;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created by bruno on 14/11/14.
 */
public class ReleaseDates {
    @DatabaseField()
    private Date theater;
    @DatabaseField()
    private Date dvd;

    public Date getTheater() {
        return theater;
    }

    public void setTheater(Date theater) {
        this.theater = theater;
    }

    public Date getDvd() {
        return dvd;
    }

    public void setDvd(Date dvd) {
        this.dvd = dvd;
    }
}
