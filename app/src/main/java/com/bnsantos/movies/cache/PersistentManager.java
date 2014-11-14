package com.bnsantos.movies.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bnsantos.movies.model.Links;
import com.bnsantos.movies.model.Movie;
import com.bnsantos.movies.model.Posters;
import com.bnsantos.movies.model.Ratings;
import com.bnsantos.movies.model.ReleaseDates;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by bruno on 14/11/14.
 */
public class PersistentManager extends OrmLiteSqliteOpenHelper {
    private static final String TAG = PersistentManager.class.getName();
    private static final String DATABASE_NAME = "com.constructlatam.persistence.db";
    private static final int DATABASE_VERSION = 1;

    private Dao mMovieDao = null;
    private Dao mLinksDao = null;
    private Dao mPostersDao = null;
    private Dao mRatingsDao = null;
    private Dao mReleasesDao = null;

    public PersistentManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade");
        dropTables();
        // after we drop the old databases, we create the new ones
        onCreate(database, connectionSource);
    }

    public Dao getLinksDAO() throws SQLException {
        if (mLinksDao == null) {
            mLinksDao = getDao(Links.class);
        }
        return mLinksDao;
    }

    public Dao getMovieDAO() throws SQLException {
        if (mMovieDao == null) {
            mMovieDao = getDao(Movie.class);
        }
        return mMovieDao;
    }

    public Dao getPostersDAO() throws SQLException {
        if (mPostersDao == null) {
            mPostersDao = getDao(Posters.class);
        }
        return mPostersDao;
    }

    public Dao getRatingsDAO() throws SQLException {
        if (mRatingsDao == null) {
            mRatingsDao = getDao(Ratings.class);
        }
        return mRatingsDao;
    }

    public Dao getReleaseDatesDAO() throws SQLException {
        if (mReleasesDao == null) {
            mReleasesDao = getDao(ReleaseDates.class);
        }
        return mReleasesDao;
    }

    private void createTables() {
        try {
            Log.i(TAG, "createTables");
            TableUtils.createTable(connectionSource, Movie.class);
            TableUtils.createTable(connectionSource, Links.class);
            TableUtils.createTable(connectionSource, Posters.class);
            TableUtils.createTable(connectionSource, Ratings.class);
            TableUtils.createTable(connectionSource, ReleaseDates.class);
        } catch (SQLException e) {
            Log.e(TAG, "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    private void dropTables() {
        try {
            Log.i(TAG, "dropTables");
            TableUtils.dropTable(connectionSource, Movie.class, true);
            TableUtils.dropTable(connectionSource, Movie.class, true);
            TableUtils.dropTable(connectionSource, Links.class, true);
            TableUtils.dropTable(connectionSource, Posters.class, true);
            TableUtils.dropTable(connectionSource, Ratings.class, true);
            TableUtils.dropTable(connectionSource, ReleaseDates.class, true);
        } catch (SQLException e) {
            Log.e(TAG, "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }
}
