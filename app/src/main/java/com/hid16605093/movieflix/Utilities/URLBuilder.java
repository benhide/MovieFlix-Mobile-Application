package com.hid16605093.movieflix.Utilities;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

// URL Builder class
public class URLBuilder
{
    // URL builder - cinema
    @NonNull
    public static String buildURICinema(double lat, double lon)
    {
        // If there is a search query
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.cinelist.co.uk")
                .appendPath("search")
                .appendPath("cinemas")
                .appendPath("coordinates")
                .appendPath(Double.toString(lat))
                .appendPath(Double.toString(lon));

        return builder.build().toString();
    }

    // URL builder - movie
    @NonNull
    public static String buildURIMovie()
    {
        // If there is a search query
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("popular")
                .appendQueryParameter("api_key", TagsAndKeys.getMovieKey())
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("page", "1");

        return builder.build().toString();
    }

    // URL builder - movie
    @NonNull
    public static String buildURIMovie(String query)
    {
        // If there is a search query
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("search")
                .appendPath("movie")
                .appendQueryParameter("include_adult", "false")
                .appendQueryParameter("page", "1")
                .appendQueryParameter("query", query)
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("api_key", TagsAndKeys.getMovieKey());
        return builder.build().toString();
    }

    // URL builder - movie
    @NonNull
    public static String buildURIMovie(String query, String year)
    {
        // If there is a search query
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("search")
                .appendPath("movie")
                .appendQueryParameter("api_key", TagsAndKeys.getMovieKey())
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("query", query)
                .appendQueryParameter("include_adult", "false")
                .appendQueryParameter("page", "1")
                .appendQueryParameter("primary_release_year", year);
        return builder.build().toString();
    }

    // URL builder - movie poster
    @NonNull
    public static String buildURIMoviePoster(String path)
    {
        // If there is a search query
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w500");
        return builder.build().toString() + path;
    }

    // URL builder - cinema times
    @NonNull
    public static String buildURICinemaTimes(String path)
    {
        // If there is a search query
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.cinelist.co.uk")
                .appendPath("get")
                .appendPath("times")
                .appendPath("cinema")
                .appendPath(path);
        return builder.build().toString();
    }

    // URL builder - cinema
    @NonNull
    public static String buildURICinema(String query)
    {
        // If there is a search query
        Uri.Builder builder = new Uri.Builder();
        try
        {
            builder.scheme("https")
                    .authority("api.cinelist.co.uk")
                    .appendPath("search")
                    .appendPath("cinemas")
                    .appendPath("location")
                    .appendPath(URLEncoder.encode(query, "UTF-8"));
        }
        catch (UnsupportedEncodingException e)
        {
            e.getStackTrace();
        }
        return builder.build().toString();
    }
}