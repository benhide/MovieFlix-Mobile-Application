package com.hid16605093.movieflix;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hid16605093.movieflix.Cinema.CinemaSearchActivity;
import com.hid16605093.movieflix.Movies.FavouriteMoviesActivity;
import com.hid16605093.movieflix.Movies.MovieSearchActivity;
import com.hid16605093.movieflix.Utilities.ConnectionTest;
import com.hid16605093.movieflix.Utilities.JSONObjectMapper;
import com.hid16605093.movieflix.Utilities.MovieSearchResultsDataWrapper;
import com.hid16605093.movieflix.Utilities.TagsAndKeys;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import datamodels.Movies.MovieSearchResults;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

References:

PERMISSIONS AND SENSORS
- Mobile computing workshop 2 / 3 code

TAKING PHOTOS WITH CAMERA - ANDROID TUTORIAL
- https://developer.android.com/training/camera/photobasics

JACKSON OBJECT MAPPING TO JAVA CLASS
- https://www.baeldung.com/jackson-object-mapper-tutorial -

ANDROID VOLLEY TUTORIAL - ASYNC NETWORKING
- https://www.androidtutorialpoint.com/networking/android-volley-tutorial/

ANDROID SHARED PREFERENCES EXAMPLE TUTORIAL
- https://www.journaldev.com/9412/android-shared-preferences-example-tutorial
*/

// Main class
public class MainActivity extends AppCompatActivity
{
    // Image request int
    final static int REQUEST_IMAGE_CAPTURE = 1;
    private String imageFilePath = "";

    // When created activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Internet connection manager
        TextView connectionText = findViewById(R.id.connectionText);

        // Internet connection?
        if (ConnectionTest.isConnectedAndNetwork(this)) connectionText.setText(R.string.connected);
        else connectionText.setText(R.string.not_connected);

        // Shared preferences - load image
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String imageUri = appSharedPrefs.getString("PROFILE_PIC", "");
        final ImageView image = findViewById(R.id.profileImage);
        Glide.with(this).load(imageUri).into(image);
    }

    // Open movie search
    public void movieSearch(View view)
    {
        // Create an intent - run activity
        startActivity(new Intent(this, MovieSearchActivity.class));
    }

    // Create image file
    private File createImageFile() throws IOException
    {
        // Create an image file name
        String timeStamp = DateFormat.getDateTimeInstance().format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    // Change the background image
    public void changeBackgroundImage(View view)
    {
        // Check permissions
        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        // Request permission
        if (permissionCamera != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},1);

        // Permissions granted
        else
        {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null)
            {
                // Create the File where the photo should go
                File photoFile = null;
                try
                {
                    photoFile = createImageFile();
                }
                catch (IOException e)
                {
                    e.getStackTrace();
                }
                // Continue only if the File was successfully created
                if (photoFile != null)
                {
                    Uri photoURI = FileProvider.getUriForFile(this, TagsAndKeys.getAppTag(), photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }
    }

    // When picture taken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK)
        {
            ImageView image = findViewById(R.id.profileImage);
            Glide.with(this).load(imageFilePath).into(image);
            setProfileImage(imageFilePath);
        }
    }

    // User permission options
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        // Request code
        if (requestCode == 1)
        {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission granted for camera!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Can not add background photo with out camera permission", Toast.LENGTH_SHORT).show();
        }
    }

    // Open cinema search
    public void cinemaSearch(View view)
    {
        startActivity(new Intent(this, CinemaSearchActivity.class));
    }

    // Open favourites
    public void viewFavourites(View view)
    {
        // List of favourite movies
        ArrayList<MovieSearchResults> faveMovies = new ArrayList<>();

        // Shared preferences
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String json = appSharedPrefs.getString("FAVE_MOVIES", "");

        // Error handling
        try
        {
            // Check the string to see if its empty
            if (!json.isEmpty())
            {
                // Get the favourite movies
                faveMovies = JSONObjectMapper.getObjectMapper().readValue(json, new TypeReference<ArrayList<MovieSearchResults>>() {});

                // Check for favourite movies
                if (faveMovies.size() > 0)
                {
                    // Create an intent to start the activity
                    Intent intent = new Intent(MainActivity.this, FavouriteMoviesActivity.class);
                    intent.putExtra("FAVE_MOVIES", new MovieSearchResultsDataWrapper(faveMovies));

                    // Create an intent - run activity
                    startActivity(intent);
                }
                else Toast.makeText(this, R.string.no_favourites_saved, Toast.LENGTH_SHORT).show();
            }
        }

        // Error handling
        catch (IOException e)
        {
            if (faveMovies.isEmpty())
                Toast.makeText(this, R.string.no_favourites_saved, Toast.LENGTH_SHORT).show();
            else
            {
                Toast.makeText(this, R.string.cannot_load_favourites, Toast.LENGTH_SHORT).show();
                e.getStackTrace();
            }
        }
    }

    // Set profile image
    public void setProfileImage(String imgFilePath)
    {
        SharedPreferences.Editor prefsEditor = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext()).edit();
        prefsEditor.putString("PROFILE_PIC", imgFilePath);
        prefsEditor.apply();
    }
}