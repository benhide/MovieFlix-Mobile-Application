package com.hid16605093.movieflix.Utilities;

import android.content.Context;
import android.widget.Toast;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.hid16605093.movieflix.R;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

References:

CREATE A YOUTUBE CLIENT ON ANDROID
- https://code.tutsplus.com/tutorials/create-a-youtube-client-on-android--cms-22858
*/

// Youtube search class
public class YouTubeSearch
{
    // Search list
    private YouTube.Search.List query;

    //Constructor to properly initialize Youtube's object
    public YouTubeSearch(Context context)
    {
        // Youtube object
        YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer()
        {
            // Initialise
            @Override
            public void initialize(HttpRequest request) {}}).setApplicationName("MovieFlix").build();

        // Error handling
        try
        {
            // Define the API request
            query = youtube.search().list("id, snippet");
            query.setKey(TagsAndKeys.getYouTubeKey());
            query.setType("video");
            query.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
        }

        // Error handling
        catch (IOException e)
        {
            Toast.makeText(context, R.string.youtube_error, Toast.LENGTH_SHORT).show();
            e.getStackTrace();
        }
    }

    // Search for keyword in youtube api
    public YouTubeVideo search(Context context, String keywords, String date)
    {
        // Search term
        query.setQ(keywords + " movie trailer " + date);

        // Max results
        query.setMaxResults((long)5);

        // Error handling
        try
        {
            // Call youtube api
            SearchListResponse response = query.execute();

            // Get the results list
            List<SearchResult> results = response.getItems();

            // List of videos
            YouTubeVideo video = new YouTubeVideo();

            // Check the results exist update the items
            if (results != null) video = getVideo(context, results.iterator());

            // Return results
            return video;
        }

        // Error handling
        catch (IOException e)
        {
            e.getStackTrace();
            return null;
        }
    }

    // Populate the list of youtube videos
    private static YouTubeVideo getVideo(Context context, Iterator<SearchResult> iteratorSearchResults)
    {
        //temporary list to store the raw data from the returned results
        YouTubeVideo video = new YouTubeVideo();

        //if no result then printing appropriate output
        if (!iteratorSearchResults.hasNext())
            Toast.makeText(context, R.string.no_trailer, Toast.LENGTH_SHORT).show();

        else
        {
            // The next search result
            SearchResult singleVideo = iteratorSearchResults.next();

            // The resource ID of video
            ResourceId resourceId = singleVideo.getId();

            // If the resource id is a youtube video
            if (resourceId.getKind().equals("youtube#video"))
            {
                // Get the id, title and description
                video.setId(singleVideo.getId().getVideoId());
                video.setTitle(singleVideo.getSnippet().getTitle());
                video.setDescription(singleVideo.getSnippet().getDescription());
            }
        }

        // Get the video
        return video;
    }
}