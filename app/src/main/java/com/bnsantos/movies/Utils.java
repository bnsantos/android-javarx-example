package com.bnsantos.movies;

import com.bnsantos.movies.model.Movie;
import com.bnsantos.movies.model.ReleaseDates;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Created by bruno on 14/11/14.
 */
public class Utils {
    public static class Json {
        public static final String DATE_FORMAT = "yyyy-MM-dd";

        public static Gson releaseDatesGsonConverter() {
            return new GsonBuilder()
                    .setDateFormat(DATE_FORMAT)
                    .create();
        }
    }

    public static class StringUtils {
        public static boolean stringCompare(String s1, String s2) {
            if (s1 == null && s2 == null) {
                return true;
            } else if (s1 == null) {
                return false;
            } else {
                return s1.equals(s2);
            }
        }
    }

    public static class MovieUtils {
        public static boolean movieListCompare(List<Movie> m1, List<Movie> m2) {
            if (m1 == null && m2 == null) {
                return true;
            } else if (m1 == null) {
                return false;
            } else {
                return m1.equals(m2);
            }
        }
    }
}
