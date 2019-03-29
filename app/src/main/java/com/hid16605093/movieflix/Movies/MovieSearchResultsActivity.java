package com.hid16605093.movieflix.Movies;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hid16605093.movieflix.R;

import java.util.ArrayList;

import datamodels.Movies.MovieSearch;
import datamodels.Movies.MovieSearchResults;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

References:

HANDLING ANDROID LISTVIEW ONITEMCLICK EVENT
- http://ezzylearning.com/tutorial/handling-android-listview-onitemclick-event
*/

// Movie search results activity
public class MovieSearchResultsActivity extends AppCompatActivity
{
    // Copy movie search results to arraylist
    private ArrayList<MovieSearchResults> movies = new ArrayList<>();

    // When activity created
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search_results);

        // Get the data from the intent
        Intent intent = this.getIntent();
        MovieSearch movieSearch = new MovieSearch();

        // Error handling
        try
        {
            // Get the bundle
            Bundle bundle = intent.getExtras();
            if (bundle != null)
                movieSearch = (MovieSearch) bundle.getSerializable("MOVIE_SEARCH");
        }
        catch (NullPointerException e)
        {
            Toast.makeText(MovieSearchResultsActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            e.getStackTrace();
        }

        // Error handling
        try
        {
            // Movie exists
            if (movieSearch != null)
                movies.addAll(movieSearch.getResults());
        }

        // Error handling
        catch (NullPointerException e)
        {
            Toast.makeText(MovieSearchResultsActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            e.getStackTrace();
        }

        // Capture the layout's TextView and set the string as its text
        ListView resultsList = findViewById(R.id.movieSearchList);

        // Bind the values of the ArrayList to the ListView
        MoviesArrayAdapter movieSearchResultsArrayAdapter = new MoviesArrayAdapter(this, movies);
        resultsList.setAdapter(movieSearchResultsArrayAdapter);

        // Try to open activity to show movie details
        try
        {
            // Clicked list item
            resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                // on click
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
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
                    bundle.putSerializable("MOVIE", movies.get(position));
                    movie.setArguments(bundle);
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