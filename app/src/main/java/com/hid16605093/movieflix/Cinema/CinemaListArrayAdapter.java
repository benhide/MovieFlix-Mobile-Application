package com.hid16605093.movieflix.Cinema;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hid16605093.movieflix.R;

import java.util.ArrayList;
import java.util.List;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

References:

USING LISTS IN ANDROID WITH LISTVIEW TUTORIAL
 - http://www.vogella.com/tutorials/AndroidListView/article.html
*/

// Cinema listing array adapter
public class CinemaListArrayAdapter extends ArrayAdapter<String>
{
    // Objects
    private Context context;
    private List<String> times;

    // Constructor
    CinemaListArrayAdapter(Context context, ArrayList<String> times)
    {
        super(context, 0 , times);
        this.context = context;
        this.times = times;
    }

    // Get the view
    @Override @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        // Assign a layout inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Views
        TextView showing;
        TextView showingTime;

        // If view is not null
        if (convertView == null && inflater != null)
        {
            convertView = inflater.inflate(R.layout.listview_cinema_movie_listings_times, parent, false);

            // Set the text views
            showing = convertView.findViewById(R.id.showingAt);
            showing.setText(R.string.showing_at);
            showingTime = convertView.findViewById(R.id.showingTime);

            if (times != null)
                showingTime.setText(times.get(position));
        }

        // return view
        if (convertView != null) return convertView;

        // Something went wrong
        else return new View(getContext());
    }
}