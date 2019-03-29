package com.hid16605093.movieflix.Cinema;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hid16605093.movieflix.Utilities.ConnectionTest;
import com.hid16605093.movieflix.Utilities.JSONObjectMapper;
import com.hid16605093.movieflix.R;
import com.hid16605093.movieflix.Utilities.TagsAndKeys;
import com.hid16605093.movieflix.Utilities.URLBuilder;
import com.hid16605093.movieflix.Utilities.VolleySingleton;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import datamodels.Cinema.Cinema;
import datamodels.Cinema.CinemaListings;

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

GOOGLE MAPS INTENTS FOR ANDROID
- https://developers.google.com/maps/documentation/urls/android-intents
*/

// Selected cinema activity
public class SelectedCinemaActivity extends AppCompatActivity
{
    // Cinema listings
    private CinemaListings cinemaListings;
    private Cinema cinema;

    // Views
    private ListView listingResults;

    // When activity created
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_cinema);

        // Get the data from the intent
        Intent intent = this.getIntent();

        // Error handling
        try
        {
            // Get the bundle
            Bundle bundle = intent.getExtras();
            if (bundle != null)
                cinema = (Cinema) bundle.getSerializable("CINEMA");
        }
        catch (NullPointerException e)
        {
            Toast.makeText(SelectedCinemaActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            e.getStackTrace();
        }

        // Capture the layout's TextView and set the string as its text
        listingResults = findViewById(R.id.movieList);

        // Get the cinema times
        cinemaListingsObjectRequest(URLBuilder.buildURICinemaTimes(cinema.getId()));

        // Text view
        TextView cinemaName = findViewById(R.id.cinemaName);
        String name = cinema.getName();
        int space = name.indexOf(" ", name.indexOf(" ") + 1);
        final String result = name.substring(0, space);
        cinemaName.setText(result.replace(",", ""));

        // Try to open activity to show cinema listings details
        try
        {
            // Clicked list item
            listingResults.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                // on click
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    // When clicked, show a toast with cinema listings name
                    Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();

                    // Create an intent to start the activity
                    Intent intent = new Intent(SelectedCinemaActivity.this, CinemaMovieListingsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("LISTINGS", cinemaListings.getListings().get(position));

                    intent.putExtra("CINEMA_NAME", result.replace(",", ""));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        // Catch errors
        catch (Exception e)
        {
            // When clicked, show a toast with movie title
            Toast.makeText(getApplicationContext(), R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // Api movie search - uses Volley JSON object request - passed the url endpoint
    public void cinemaListingsObjectRequest(String url)
    {
        // If connected
        if (ConnectionTest.isConnectedAndNetwork(this))
        {
            // JSON object request
            JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>()
            {
                // Response listener
                @Override
                public void onResponse(JSONObject response)
                {
                    // Try to map the JSON response to CinemaSearch class and set the list view
                    try
                    {
                        // Get the search results - map to java object
                        cinemaListings = JSONObjectMapper.getObjectMapper().readValue(response.toString(), CinemaListings.class);
                        ArrayList<String> listings = new ArrayList<>();

                        // If results
                        if (cinemaListings.getListings().size() > 0)
                        {
                            // Get the cinema names
                            for (int i = 0; i < cinemaListings.getListings().size(); i++)
                                listings.add(cinemaListings.getListings().get(i).getTitle());

                            // List views
                            ArrayAdapter<String> cinemaListingsSearchResults = new ArrayAdapter<>(SelectedCinemaActivity.this, R.layout.simple_list_item_1, listings);
                            listingResults.setAdapter(cinemaListingsSearchResults);
                        }
                        // When clicked, show a toast error help
                        else Toast.makeText(SelectedCinemaActivity.this, R.string.no_search_results, Toast.LENGTH_SHORT).show();
                    }

                    // Error handling
                    catch (IOException e)
                    {
                        // When clicked, show a toast error help
                        Toast.makeText(SelectedCinemaActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                        e.getStackTrace();
                    }
                }
            },
            new Response.ErrorListener()
            {
                // Error response
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    // When clicked, show a toast error help
                    Toast.makeText(SelectedCinemaActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                }
            });

            // Adding JsonObject request to request queue
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq, TagsAndKeys.getRequestTag(), getApplicationContext());
        }
        else
        {
            Toast.makeText(SelectedCinemaActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
        }
    }

    // See the cinema on the map
    public void seeOnMap(View view)
    {
        // Search for the cinema on map
        String name = cinema.getName();
        int space = name.indexOf(" ", name.indexOf(" ") + 1);
        String result = name.substring(0, space);
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + result.replace(",", ""));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
