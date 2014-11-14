package com.bnsantos.movies;

import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bruno on 14/11/14.
 */
public class Utils {
    public static void logVolleyError(String className, VolleyError volleyError) {
        Log.e(className, "Error ", volleyError);
        try {
            Log.e(className, " - " + new String(volleyError.networkResponse.data, HttpHeaderParser.parseCharset(volleyError.networkResponse.headers)));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static class Url {
        public static String getEndPoint() {
            return App.getInstance().getEndPoint();
        }

        public static String getMoviesListUrl(MovieListType movieListType, String... params) {
            return getEndPoint() + "lists/movies/" + movieListType.name().toLowerCase() + ".json?" + addParams(params);
        }

        public static String addParams(String... params) {
            if (params != null && params.length > 0) {
                StringBuffer buffer = new StringBuffer(UrlParams.getTokenParam());
                for (int i = 0; i < params.length; i++) {
                    buffer.append(params[i]);
                }
                return buffer.toString();
            } else {
                return UrlParams.getTokenParam();
            }
        }
    }

    public static class UrlParams {
        public static String getTokenParam() {
            return "apiKey=" + App.getInstance().getApiToken();
        }

        public static String pageLimit(int limit) {
            return "&page_limit=" + limit;
        }

        public static String page(int page) {
            return "&page=" + page;
        }

        public static String country(String countryCode) {
            return "&country=" + countryCode;
        }
    }

    public static class Json {
        public static final String DATE_FORMAT = "yyyy-MM-dd";
        private static JsonDeserializer<Date> dateJsonDeserializer = new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
                try {
                    return json == null ? null : df.parse(json.getAsString());
                } catch (ParseException e) {
                    Log.e(Json.class.getName(), "Error while parsing date from json " + json.getAsString(), e);
                    return null;
                }
            }
        };

        public static String extractJsonField(String rawJson, String field) {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(rawJson).getAsJsonObject();
            JsonElement jsonElement = jsonObject.get(field);
            if (jsonElement != null) {
                return jsonElement.toString();
            } else {
                return null;
            }
        }

        public static <T> T fromJson(String json, Type type) {
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, dateJsonDeserializer).create();
            T obj = gson.fromJson(json, type);
            return obj;
        }
    }
}
