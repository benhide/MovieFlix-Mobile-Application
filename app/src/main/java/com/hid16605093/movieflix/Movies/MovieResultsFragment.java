package com.hid16605093.movieflix.Movies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hid16605093.movieflix.R;
import com.hid16605093.movieflix.Utilities.Firebase;
import com.hid16605093.movieflix.Utilities.JSONObjectMapper;
import com.hid16605093.movieflix.Utilities.URLBuilder;
import com.hid16605093.movieflix.Utilities.VolleySingleton;
import com.hid16605093.movieflix.Utilities.YouTubeSearch;
import com.hid16605093.movieflix.Utilities.YouTubeVideo;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Nullable;

import datamodels.Movies.MovieSearchResults;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

References:

FRAGMENTS - PART OF THE ANDROID JETPACK
- https://developer.android.com/guide/components/fragments#java
*/

public class MovieResultsFragment extends Fragment
{
    // Selected movie
    private MovieSearchResults movie = new MovieSearchResults();

    @Override
    public void onCreate(@Nullable Bundle bundle)
    {
        super.onCreate(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_movie_results, container, false);

        // Error handling
        try
        {
            if (getArguments() != null)
                movie = (MovieSearchResults) getArguments().getSerializable("MOVIE");
        }
        catch (NullPointerException e)
        {
            e.getStackTrace();
        }

        // Views
        TextView title = view.findViewById(R.id.movieTitle);
        TextView year = view.findViewById(R.id.movieYear);
        TextView description = view.findViewById(R.id.description);
        TextView voteAverage = view.findViewById(R.id.voteAverage);
        NetworkImageView poster = view.findViewById(R.id.moviePoster);

        // Buttons
        Button save = view.findViewById(R.id.addToFavourite);
        Button remove = view.findViewById(R.id.removeFromFavourites);
        Button watch = view.findViewById(R.id.watchTrailer);

        // Movie strings
        String overview = "";
        String movieYear = "";
        String rating = "";
        String posterPath = "";

        // Error handling
        try
        {
            // Movie exists
            if (movie != null)
            {
                title.setText(movie.getTitle());
                if (movie.getOverview() != null)
                    overview = "Description:\n" +  movie.getOverview();
                if (movie.getVoteAverage() != null)
                    rating = "Average Rating: " + String.valueOf(movie.getVoteAverage()) + "/10";
                if (movie.getPosterPath() != null)
                    posterPath = URLBuilder.buildURIMoviePoster(movie.getPosterPath());
                String date;
                if (movie.getReleaseDate() != null && !movie.getReleaseDate().isEmpty())
                {
                    date = movie.getReleaseDate().substring(0, movie.getReleaseDate().length() - 6);
                    movieYear = "Release date: " + date;
                }
            }
        }

        // Error handling
        catch (NullPointerException e)
        {
            Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            e.getStackTrace();
        }

        // Array of favourite movies
        ArrayList<MovieSearchResults> faveMovies = getFavouriteList();

        // Search the saved movies
        for (int i = 0; i < faveMovies.size(); i++)
        {
            // If the fave movies has been saved already
            if (faveMovies.get(i).getTitle().equals(movie.getTitle()))
            {
                // Hide the save button and show the remove button
                save.setVisibility(View.GONE);
                remove.setVisibility(View.VISIBLE);
            }
        }

        // Set the views
        description.setText(overview);
        year.setText(movieYear);
        voteAverage.setText(rating);

        // Error handling
        try
        {
            if (getActivity() != null)
            {
                ImageLoader imageLoader = VolleySingleton.getInstance(getActivity().getApplicationContext()).getImageLoader();
                imageLoader.get(posterPath, ImageLoader.getImageListener(poster, android.R.drawable.gallery_thumb, android.R.drawable.ic_dialog_alert));
                poster.setImageUrl(posterPath, imageLoader);
            }
        }
        catch (NullPointerException e)
        {
            e.getStackTrace();
        }

        // Save to favourites on click listener
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Array of favourite movies
                ArrayList<MovieSearchResults> faveMovies = getFavouriteList();

                if (faveMovies != null)
                {
                    boolean alreadyInList = false;
                    for (int i = 0; i < faveMovies.size(); i++)
                    {
                        if (movie.getId().equals(faveMovies.get(i).getId()))
                            alreadyInList = true;
                    }

                    // Add movie to favourites
                    if (!alreadyInList)
                    {
                        faveMovies.add(movie);

                        // Shared preferences
                        SharedPreferences.Editor prefsEditor = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit();
                        String jsonInString = "";

                        // Error handling
                        try
                        {
                            // Convert object to JSON string
                            jsonInString = JSONObjectMapper.getObjectMapper().writeValueAsString(faveMovies);
                        }

                        // Error handling
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }

                        // Put in to shared preferences
                        Toast.makeText(getActivity(), movie.getTitle() + " saved", Toast.LENGTH_SHORT).show();
                        prefsEditor.putString("FAVE_MOVIES", jsonInString);
                        prefsEditor.apply();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), movie.getTitle() + " already saved to favourites!", Toast.LENGTH_LONG).show();
                    }
                }

                // Push data to firebase
                pushToFirebase(movie);
            }
        });

        // Remove from faves
        remove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Array of favourite movies
                ArrayList<MovieSearchResults> faveMovies = getFavouriteList();

                int index = -1;

                if (faveMovies != null)
                {
                    boolean alreadyInList = false;
                    for (int i = 0; i < faveMovies.size(); i++)
                    {
                        if (movie.getId().equals(faveMovies.get(i).getId()))
                        {
                            alreadyInList = true;
                            index = i;
                        }
                    }

                    // Add movie to favourites
                    if (alreadyInList)
                    {
                        //faveMovies.remove(movie);
                        faveMovies.remove(index);

                        // Shared preferences
                        SharedPreferences.Editor prefsEditor = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit();
                        String jsonInString = "";

                        // Error handling
                        try
                        {
                            // Convert object to JSON string
                            jsonInString = JSONObjectMapper.getObjectMapper().writeValueAsString(faveMovies);
                        }

                        // Error handling
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }

                        // Put in to shared preferences
                        Toast.makeText(getActivity(), movie.getTitle() + " removed", Toast.LENGTH_SHORT).show();
                        prefsEditor.putString("FAVE_MOVIES", jsonInString);
                        prefsEditor.apply();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), movie.getTitle() + " already removed from favourites!", Toast.LENGTH_LONG).show();
                    }
                }

                // Remove from firebase
                removeFromFirebase(movie);
            }
        });

        // Watch trailer
        watch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Start a new thread
                new Thread()
                {
                    // Run the thread
                    public void run()
                    {
                        // Youtube search object
                        YouTubeSearch youTubeSearch = new YouTubeSearch(getActivity());

                        // Get the release date
                        String date = "";
                        if (movie.getReleaseDate() != null && !movie.getReleaseDate().isEmpty())
                            date = movie.getReleaseDate().substring(0, movie.getReleaseDate().length() - 6);

                        // List of youtube videos
                        YouTubeVideo searchResult = youTubeSearch.search(getActivity(), movie.getTitle(), date);

                        // Error Handling
                        try
                        {
                            // If search returned results
                            if (searchResult != null)
                            {
                                // Get the search id
                                String id = searchResult.getId();

                                // Create an intent to start the activity
                                Intent intent = new Intent(getActivity(), YouTubePlayerActivity.class);
                                if (id != null) intent.putExtra("MOVIE_ID", id);

                                // Start the activity
                                startActivity(intent);
                            }
                        }
                        catch (NullPointerException e)
                        {
                            Toast.makeText(getActivity(), R.string.no_trailer, Toast.LENGTH_LONG).show();
                            e.getStackTrace();
                        }
                    }
                }.start();
            }
        });

        return view;
    }

    // Get the favourites movie list
    public ArrayList<MovieSearchResults> getFavouriteList()
    {
        // Array of favourite movies
        ArrayList<MovieSearchResults> faveMovies = new ArrayList<>();

        String json = "";

        // Shared preferences
        // Error handling
        try
        {
            if (getActivity() != null)
            {
                SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                json = appSharedPrefs.getString("FAVE_MOVIES", "");
            }
        }
        catch (NullPointerException e)
        {
            e.getStackTrace();
        }

        // Error handling
        try
        {
            // Convert from JSON string
            faveMovies = JSONObjectMapper.getObjectMapper().readValue(json, new TypeReference<ArrayList<MovieSearchResults>>(){});
        }

        // Error handling
        catch (IOException e)
        {
            e.getStackTrace();
        }

        // Return the movies
        return faveMovies;
    }

    // Remove from firebase
    public void removeFromFirebase(MovieSearchResults movie)
    {
        Firebase firebase = new Firebase();
        firebase.removeFromDatabase(movie, getContext());
    }

    // Push to firebase
    public void pushToFirebase(MovieSearchResults movie)
    {
        Firebase firebase = new Firebase();
        firebase.pushToDatabase(movie, getContext());
    }
}