package com.bnsantos.movies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by bruno on 14/11/14.
 */
public class Utils {
    public static class Json {
        public static final String DATE_FORMAT = "yyyy-MM-dd";

        public static Gson releaseDatesGsonConverter() {
            return new GsonBuilder()
                    .setDateFormat(Utils.Json.DATE_FORMAT)
                    .create();

        }
    }
}
