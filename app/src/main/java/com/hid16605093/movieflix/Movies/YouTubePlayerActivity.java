package com.hid16605093.movieflix.Movies;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.hid16605093.movieflix.R;
import com.hid16605093.movieflix.Utilities.TagsAndKeys;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

References:

USING THE YOUTUBE API TO EMBED VIDEO IN AN ANDROID APP
- https://www.sitepoint.com/using-the-youtube-api-to-embed-video-in-an-android-app/
*/

// Video player class
public class YouTubePlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener
{
    // Recovery request int
    private static final int RECOVERY_REQUEST = 1;

    // YouTube player view
    private YouTubePlayerView youTubePlayerView;

    // Youtube search id
    private String movieID;

    // When created activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_player);

        // Set the views
        youTubePlayerView = findViewById(R.id.youtubePlayerView);
        youTubePlayerView.initialize(TagsAndKeys.getYouTubeKey(), this);

        // Get the data from the intent
        Intent intent = this.getIntent();

        // Error handling
        try
        {
            // Get the movie ID
            movieID = intent.getStringExtra("MOVIE_ID");
        }

        // Error handling
        catch (NullPointerException e)
        {
            Toast.makeText(YouTubePlayerActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            e.getStackTrace();
        }
    }

    // Initialisation was successful
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored)
    {
        // If not restored
        if (!wasRestored)
        {
            // Cue the video
            if (movieID != null)
                player.cueVideo(movieID);
        }
    }

    // Initialisation failure
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error)
    {
        // Determines whether this error is user-recoverable
        if (error.isUserRecoverableError())
            error.getErrorDialog(this, RECOVERY_REQUEST).show();

        // Show the error
        else
        {
            String errorString = String.format(getString(R.string.player_error), error.toString());
            Toast.makeText(this, errorString, Toast.LENGTH_LONG).show();
        }
    }

    // Get result from another activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // If it is a recovery request
        if (requestCode == RECOVERY_REQUEST)
        {
            // Retry initialization
            getYouTubePlayerProvider().initialize(TagsAndKeys.getYouTubeKey(), this);
        }
    }

    // Get the youtube player provider
    protected YouTubePlayer.Provider getYouTubePlayerProvider()
    {
        return youTubePlayerView;
    }
}
