package com.hid16605093.movieflix.Cinema;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hid16605093.movieflix.R;

import java.util.ArrayList;

import datamodels.Cinema.Cinema;
import datamodels.Cinema.CinemaSearch;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

References:

HANDLING ANDROID LISTVIEW ONITEMCLICK EVENT
- http://ezzylearning.com/tutorial/handling-android-listview-onitemclick-event
*/


// Cinema listings class
public class CinemaListingsActivity extends AppCompatActivity
{
    // Copy movie search results to arraylist
    private ArrayList<Cinema> cinemas = new ArrayList<>();

    // When activity created
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_listings);

        // Get the data from the intent
        Intent intent = this.getIntent();
        CinemaSearch cinemaSearch = new CinemaSearch();

        double lat = 0;
        double lon = 0;
        String searchText = "";

        // Error handling
        try
        {
            // Get the bundle
            Bundle bundle = intent.getExtras();
            if (bundle != null)
                cinemaSearch = (CinemaSearch) bundle.getSerializable("CINEMA_SEARCH");

            lat = intent.getDoubleExtra("LAT", 0);
            lon = intent.getDoubleExtra("LON", 0);
            searchText = "Searched for cinemas near " + intent.getStringExtra("SEARCH_TERM");

        }
        catch (NullPointerException e)
        {
            Toast.makeText(CinemaListingsActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            e.getStackTrace();
        }

        // Error handling
        try
        {
            // Movie exists
            if (cinemaSearch != null)
                cinemas.addAll(cinemaSearch.getCinemas());
        }

        // Error handling
        catch (NullPointerException e)
        {
            Toast.makeText(CinemaListingsActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            e.getStackTrace();
        }

        // Cinema list
        ArrayList<String> cinemaNames = new ArrayList<>();

        // Get the cinema names
        for (int i = 0; i < cinemas.size(); i++)
        {
            String name = cinemas.get(i).getName();
            int space = name.indexOf(" ", name.indexOf(" ") + 1);
            final String result = name.substring(0, space);
            cinemaNames.add(result.replace(",", ""));
        }

        // Capture the layout's TextView and set the string as its text
        ListView resultsList = findViewById(R.id.cinemaLocations);

        // Text view
        TextView location = findViewById(R.id.locationText);
        // Show the lat and lon
        if (lat != 0 && lon != 0)
        {
            String latlon = "Searched for cinemas at current location\nLatitude: " + lat + " / longitude: " + lon;
            location.setText(latlon);
        }
        else
        {
            location.setText(searchText);
        }

        // Bind the values of the ArrayList to the ListView
        ArrayAdapter<String> cinemaArrayAdapter = new ArrayAdapter<>(CinemaListingsActivity.this, R.layout.simple_list_item_1, cinemaNames);
        resultsList.setAdapter(cinemaArrayAdapter);

        // Try to open activity to show cinema details
        try
        {
            // Clicked list item
            resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                // On click
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    // When clicked, show a toast with cinema name
                    Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();

                    // Create an intent to start the activity
                    Intent intent = new Intent(CinemaListingsActivity.this, SelectedCinemaActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CINEMA", cinemas.get(position));
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
}