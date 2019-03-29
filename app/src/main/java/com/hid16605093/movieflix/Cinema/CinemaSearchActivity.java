package com.hid16605093.movieflix.Cinema;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.hid16605093.movieflix.R;
import com.hid16605093.movieflix.Utilities.CachedSearches;
import com.hid16605093.movieflix.Utilities.ConnectionTest;
import com.hid16605093.movieflix.Utilities.JSONObjectMapper;
import com.hid16605093.movieflix.Utilities.LocationTest;
import com.hid16605093.movieflix.Utilities.TagsAndKeys;
import com.hid16605093.movieflix.Utilities.URLBuilder;
import com.hid16605093.movieflix.Utilities.VolleySingleton;

import org.json.JSONObject;

import java.io.IOException;

import datamodels.Cinema.CinemaSearch;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

References:

PERMISSIONS AND SENSORS
- Mobile computing workshop 2 / 3 code

JACKSON OBJECT MAPPING TO JAVA CLASS
- https://www.baeldung.com/jackson-object-mapper-tutorial -

ANDROID VOLLEY TUTORIAL - ASYNC NETWORKING
- https://www.androidtutorialpoint.com/networking/android-volley-tutorial/

ANDROID SHARED PREFERENCES EXAMPLE TUTORIAL
- https://www.journaldev.com/9412/android-shared-preferences-example-tutorial

ANDROID CHECK AND ENABLE LOCATION SERVICES
- http://hmkcode.com/android-check-enable-location-service/
*/

// Cinema search activity class
public class CinemaSearchActivity extends AppCompatActivity
{
    // Location
    private double lat = 0;
    private double lon = 0;

    // When created activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_search);
    }

    // Check location permission
    public void nearbySearch(View view)
    {
        // Check permissions
        int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        // Request permission
        if (permissionLocation != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        // Get the cinemas from current location
        getCinemas();
    }

    // Search for cinema by location name
    public void searchCinemas(View view)
    {
        // Set the search string
        String searchText;
        EditText searchInput = findViewById(R.id.cinemaSearch);
        searchText = searchInput.getText().toString().trim();
        lat = 0;
        lon = 0;

        if (!searchText.isEmpty())
            cinemaObjectRequest(URLBuilder.buildURICinema(searchText), searchText);
        else
            Toast.makeText(getApplicationContext(), R.string.enter_search_term_cinema, Toast.LENGTH_SHORT).show();
    }

    // Api movie search - uses Volley JSON object request - passed the url endpoint
    public void cinemaObjectRequest(String url, final String searchText)
    {
        // If connected to a network
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
                        CinemaSearch cinemaSearch = JSONObjectMapper.getObjectMapper().readValue(response.toString(), CinemaSearch.class);

                        // Create an intent to start the activity
                        Intent intent = new Intent(CinemaSearchActivity.this, CinemaListingsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("CINEMA_SEARCH", cinemaSearch);
                        intent.putExtra("LAT", lat);
                        intent.putExtra("LON", lon);
                        intent.putExtra("SEARCH_TERM", searchText);
                        intent.putExtras(bundle);

                        // Start the activity
                        startActivity(intent);

                    }
                    // Error handling
                    catch (IOException e)
                    {
                        // When clicked, show a toast error help
                        Toast.makeText(CinemaSearchActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), R.string.no_search_results, Toast.LENGTH_SHORT).show();
                    error.getStackTrace();
                }
            });

            // Adding JsonObject request to request queue
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq, TagsAndKeys.getRequestTag(), getApplicationContext());
        }
        else Toast.makeText(CinemaSearchActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
    }

    // Location permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        // Request permissions
        switch (requestCode)
        {
            case 1:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // Notification stating permission granted - start activity
                    Toast.makeText(this, R.string.location_permission_granted, Toast.LENGTH_SHORT).show();

                    //startActivity(new Intent(this, CinemaListingsActivity.class));
                }
                else Toast.makeText(this, R.string.location_permission_needed, Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Get the cinemas from current location
    public void getCinemas()
    {
        // Check permissions
        int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        // Request permission
        if (permissionLocation == PackageManager.PERMISSION_GRANTED)
        {
            // location
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            // Get location
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>()
            {
                @Override
                public void onSuccess(Location location)
                {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null)
                    {
                        cinemaObjectRequest(URLBuilder.buildURICinema(location.getLatitude(), location.getLongitude()), null);
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                        String latlon = "Last known location\nLatitude: " + lat + " / longitude: " + lon;
                        Toast.makeText(CinemaSearchActivity.this, latlon, Toast.LENGTH_SHORT).show();
                    }

                   // Enable location
                    else
                    {
                        // Is location available
                        if (!LocationTest.isLocationServiceEnabled(getApplicationContext()))
                        {
                            // Send user to location settings
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }

                        // For emulator debugging
                        else Toast.makeText(CinemaSearchActivity.this, R.string.no_search_results_location, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
