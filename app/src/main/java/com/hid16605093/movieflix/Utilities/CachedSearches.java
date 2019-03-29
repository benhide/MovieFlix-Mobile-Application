package com.hid16605093.movieflix.Utilities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hid16605093.movieflix.Movies.MovieSearchResultsActivity;

import java.io.IOException;


import datamodels.Movies.MovieSearch;

// Class for caching search results to display when no connectivity
public class CachedSearches
{
    // Cache movie search results
    public static void cacheMovieSearch(Context context, MovieSearch movieSearch)
    {
        // Shared preferences
        SharedPreferences.Editor prefsEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        String jsonInString;

        // Error handling
        try
        {
            // Convert object to JSON string
            jsonInString = JSONObjectMapper.getObjectMapper().writeValueAsString(movieSearch);

            // Put in to shared preferences
            prefsEditor.putString("MOVIE_SEARCH_CACHE", jsonInString);
            prefsEditor.apply();
        }

        // Error handling
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // Get cached movie search results
    public static void getCachedMovieSearch(Context context)
    {
        // List of favourite movies
        MovieSearch movieSearch;

        // Shared preferences
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = appSharedPrefs.getString("MOVIE_SEARCH_CACHE", "");

        // Error handling
        try
        {
            // Get the favourite movies
            movieSearch = JSONObjectMapper.getObjectMapper().readValue(json, new TypeReference<MovieSearch>(){});

            // Create an intent to start the activity
            Intent intent = new Intent(context, MovieSearchResultsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("MOVIE_SEARCH", movieSearch);
            intent.putExtras(bundle);

            // Create an intent - run activity
            context.startActivity(intent);
        }

        // Error handling
        catch (IOException e)
        {
            e.getStackTrace();
        }
    }
}
