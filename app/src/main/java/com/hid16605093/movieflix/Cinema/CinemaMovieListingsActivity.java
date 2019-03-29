package com.hid16605093.movieflix.Cinema;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hid16605093.movieflix.Movies.MovieResultsFragment;
import com.hid16605093.movieflix.R;
import com.hid16605093.movieflix.Utilities.ConnectionTest;
import com.hid16605093.movieflix.Utilities.JSONObjectMapper;
import com.hid16605093.movieflix.Utilities.TagsAndKeys;
import com.hid16605093.movieflix.Utilities.URLBuilder;
import com.hid16605093.movieflix.Utilities.VolleySingleton;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import datamodels.Cinema.Listing;
import datamodels.Movies.MovieSearch;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

References:

JACKSON OBJECT MAPPING TO JAVA CLASS
- https://www.baeldung.com/jackson-object-mapper-tutorial -

ANDROID VOLLEY TUTORIAL - ASYNC NETWORKING
- https://www.androidtutorialpoint.com/networking/android-volley-tutorial/

HANDLING ANDROID LISTVIEW ONITEMCLICK EVENT
- http://ezzylearning.com/tutorial/handling-android-listview-onitemclick-event
*/

// Cinema movie listing class
public class CinemaMovieListingsActivity extends AppCompatActivity
{
    // Cinema listing
    private Listing listing;

    // When activity created
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_movie_listings);

        // Get the data from the intent
        Intent intent = this.getIntent();

        // Error handling
        try
        {
            // Get the bundle
            Bundle bundle = intent.getExtras();
            if (bundle != null)
                listing = (Listing) bundle.getSerializable("LISTINGS");
        }
        catch (NullPointerException e)
        {
            Toast.makeText(CinemaMovieListingsActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            e.getStackTrace();
        }

        // Cinema name
        String cinemaName = intent.getStringExtra("CINEMA_NAME");

        // Text view and list view
        TextView title  = findViewById(R.id.cinemaListingTitle);
        TextView cinema = findViewById(R.id.cinemaListingName);
        TextView viewDescription = findViewById(R.id.viewDescriptionTextview);
        ListView times  = findViewById(R.id.cinemaListingTimes);

        // Set the title
        title.setText(listing.getTitle());
        cinema.setText(cinemaName);
        ArrayList<String> showTimes = new ArrayList<>(listing.getTimes());

        // Bind the values of the ArrayList to the ListView
        CinemaListArrayAdapter cinemaListingsTimes = new CinemaListArrayAdapter(this, showTimes);
        times.setAdapter(cinemaListingsTimes);

        // On click view description
        viewDescription.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Set the search string
                movieObjectRequest(URLBuilder.buildURIMovie(listing.getTitle(), String.valueOf(Calendar.getInstance().get(Calendar.YEAR))));
            }
        });
    }

    // Search for movie
    public void movieDescription(View view)
    {
        // Set the search string
        movieObjectRequest(URLBuilder.buildURIMovie(listing.getTitle(), String.valueOf(Calendar.getInstance().get(Calendar.YEAR))));
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
                            // Fragment
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            MovieResultsFragment movie = new MovieResultsFragment();
                            fragmentTransaction.add(R.id.movieSelectedFragment, movie);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                            // Create an intent to start the activity
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("MOVIE", movieSearch.getResults().get(0));
                            movie.setArguments(bundle);
                        }

                        // When clicked, show a toast error help
                        else Toast.makeText(CinemaMovieListingsActivity.this, R.string.no_search_results, Toast.LENGTH_SHORT).show();
                    }

                    // Error handling
                    catch (IOException e)
                    {
                        // When clicked, show a toast error help
                        Toast.makeText(CinemaMovieListingsActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(CinemaMovieListingsActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                }
            });

            // Adding JsonObject request to request queue
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq, TagsAndKeys.getRequestTag(), getApplicationContext());
        }
        else Toast.makeText(CinemaMovieListingsActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
    }
}
