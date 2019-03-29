package com.hid16605093.movieflix.Movies;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.hid16605093.movieflix.R;
import com.hid16605093.movieflix.Utilities.URLBuilder;
import com.hid16605093.movieflix.Utilities.VolleySingleton;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import datamodels.Movies.MovieSearchResults;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

References:

USING AN ARRAYADAPTER WITH LISTVIEW
https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
*/

// Movies array adapter class
public class MoviesArrayAdapter extends ArrayAdapter<MovieSearchResults>
{
    // Images
    private Bitmap[] bitmapList;

    // Context
    private Context context;

    // Movie search results
    private ArrayList<MovieSearchResults> movies;

    // Constructor
    MoviesArrayAdapter(Context context, ArrayList<MovieSearchResults> movies)
    {
        super(context, 0, movies);
        this.context = context;
        this.movies = movies;
        initBitmapListWithPlaceholders();
    }

    // Initialise the place holders
    private void initBitmapListWithPlaceholders()
    {
        int count = movies.size();
        bitmapList = new Bitmap[count];
        for(int i = 0; i < count; i++)
        {
            bitmapList[i] = null;
        }
    }

    // When the bitmap is loaded
    private void onBitmapLoaded(int position, Bitmap bmp)
    {
        bitmapList[position] = bmp;
    }

    // Get the view
    @Override @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent)
    {
        // Set views
        View view = convertView;
        TextView title;
        TextView releaseDate;
        final ImageView image;

        // If convertView is null
        if (convertView == null)
        {
            // Assign a layout inflater
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Set the view
            view = inflater.inflate(R.layout.listview_movie_search, parent, false);
        }

        //
        MovieSearchResults movie = movies.get(position);
        final ImageLoader imageLoader = VolleySingleton.getInstance(context).getImageLoader();

        // Year
        String year = "";
        if (movie.getReleaseDate() != null && !movie.getReleaseDate().isEmpty())
            year = "Release date: " + movie.getReleaseDate().substring(0, movie.getReleaseDate().length() - 6);

        // Check the view
        if (view != null)
        {
            // Get the views
            title = view.findViewById(R.id.firstLine);
            releaseDate = view.findViewById(R.id.secondLine);
            image = view.findViewById(R.id.icon);

            // Set the views
            title.setText(movie.getTitle());
            releaseDate.setText(year);

            // Load the image
            final String url;
            if (movie.getPosterPath() != null)
            {
                url = URLBuilder.buildURIMoviePoster(movie.getPosterPath());
                imageLoader.get(url, ImageLoader.getImageListener(image, android.R.drawable.gallery_thumb, android.R.drawable.ic_dialog_alert));

                // Load the image
                imageLoader.get(url, new ImageLoader.ImageListener()
                {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate)
                    {
                        if (response.getBitmap() != null)
                        {
                            onBitmapLoaded(position, response.getBitmap());
                            image.setImageBitmap(bitmapList[position]);
                        }
                    }
                    @Override
                    public void onErrorResponse(VolleyError e)
                    {
                        e.getStackTrace();
                    }
                });
            }
        }

        // Return view
        return view;
    }
}