package com.hid16605093.movieflix.Movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.hid16605093.movieflix.R;
import com.hid16605093.movieflix.Utilities.CachedSearches;
import com.hid16605093.movieflix.Utilities.ConnectionTest;
import com.hid16605093.movieflix.Utilities.JSONObjectMapper;
import com.hid16605093.movieflix.Utilities.TagsAndKeys;
import com.hid16605093.movieflix.Utilities.URLBuilder;
import com.hid16605093.movieflix.Utilities.VolleySingleton;

import org.json.JSONObject;
import java.io.IOException;

import datamodels.Movies.MovieSearch;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

References:

JACKSON OBJECT MAPPING TO JAVA CLASS
- https://www.baeldung.com/jackson-object-mapper-tutorial -

ANDROID VOLLEY TUTORIAL - ASYNC NETWORKING
- https://www.androidtutorialpoint.com/networking/android-volley-tutorial/
*/

// Movie search class
public class MovieSearchActivity extends AppCompatActivity
{
    // When created activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
    }

    // Get the current popular movies
    public void popularMovies(View view)
    {
        movieObjectRequest(URLBuilder.buildURIMovie());
    }

    // Search for movie
    public void movieSearch(View view)
    {
        // Set the search string
        String searchText;
        EditText searchInput = findViewById(R.id.movieSearchEditText);
        searchText = searchInput.getText().toString().trim();

        if (!searchText.isEmpty())
            movieObjectRequest(URLBuilder.buildURIMovie(searchText));
        else
            Toast.makeText(getApplicationContext(), R.string.enter_search_term_movie, Toast.LENGTH_SHORT).show();
    }

    // Api movie search - uses Volley JSON object request - passed the url endpoint
    public void movieObjectRequest(String url)
    {
        // If connected to network
        if (ConnectionTest.isConnectedAndNetwork(this))
        {
            // JSON object request
            JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>()
            {
                // Response listener
                @Override
                public void onResponse(JSONObject response)
                {
                    // Error handling
                    try
                    {
                        // Get the search results - map to java object
                        MovieSearch movieSearch = JSONObjectMapper.getObjectMapper().readValue(response.toString(), MovieSearch.class);

                        // If there are search results start the activity
                        if (movieSearch.getResults().size() > 0)
                        {
                            // Create an intent to start the activity
                            Intent intent = new Intent(MovieSearchActivity.this, MovieSearchResultsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("MOVIE_SEARCH", movieSearch);
                            intent.putExtras(bundle);

                            // Cache the search
                            CachedSearches.cacheMovieSearch(getApplicationContext(), movieSearch);

                            // Start the activity
                            startActivity(intent);
                        }

                        // When clicked, show a toast error help
                        else Toast.makeText(MovieSearchActivity.this, R.string.no_search_results, Toast.LENGTH_SHORT).show();
                    }

                    // Error handling
                    catch (IOException e)
                    {
                        // When clicked, show a toast error help
                        Toast.makeText(MovieSearchActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                        e.getStackTrace();
                    }
                }
            },

            // Error listener
            new Response.ErrorListener()
            {
                // Error response
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    // When clicked, show a toast error help
                    Toast.makeText(MovieSearchActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                }
            });

            // Adding JsonObject request to request queue
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq, TagsAndKeys.getRequestTag(), getApplicationContext());
        }
        else
        {
            // Cache the search
            CachedSearches.getCachedMovieSearch(getApplicationContext());
            Toast.makeText(MovieSearchActivity.this, R.string.no_connection_show_cached, Toast.LENGTH_SHORT).show();
        }
    }
}
