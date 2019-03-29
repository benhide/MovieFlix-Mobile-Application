package com.hid16605093.movieflix.Utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import datamodels.Movies.MovieSearchResults;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

References:

FIREBASE SETUP AND TUTORIAL
- https://firebase.google.com/docs/database/android/start
*/

// Firebase helper class
public class Firebase
{
    // Firebase connection and database reference
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl(TagsAndKeys.getFirebaseDB());

    // Push to database
    public void pushToDatabase(MovieSearchResults movie, final Context context)
    {
        databaseReference.child("MOVIES").child(movie.getTitle()).setValue(movie).addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid)
            {
                // Write was successful!
                Toast.makeText(context, "Saved to remote storage", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                // Write failed
                Toast.makeText(context, "Failed to write to remote storage", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Remove from database
    public void removeFromDatabase(MovieSearchResults movie, final Context context)
    {
        databaseReference.child("MOVIES").child(movie.getTitle()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid)
            {
                // Remove was successful!
                Toast.makeText(context, "Removed from remote storage", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                // Remove failed
                Toast.makeText(context, "Failed to remove from remote storage", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
