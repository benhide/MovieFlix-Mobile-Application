package com.hid16605093.movieflix.Utilities;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

References:

ANDROID VOLLEY TUTORIAL - ASYNC NETWORKING
- https://www.androidtutorialpoint.com/networking/android-volley-tutorial/
*/

// Volley singleton class
public class VolleySingleton
{
    // Static objects
    private static VolleySingleton instance;

    // Request an image objects
    private RequestQueue requestQueue;

    // Image loader
    private ImageLoader imageLoader;

    // Constructor
    private VolleySingleton(Context context)
    {
        requestQueue = getRequestQueue(context);
        imageLoader = new ImageLoader(requestQueue, new LruBitmapCache(context));
    }

    // Singleton instance
    public static synchronized VolleySingleton getInstance(Context context)
    {
        if (instance == null) instance = new VolleySingleton(context);
        return instance;
    }

    // Get the volley request queue
    private RequestQueue getRequestQueue(Context context)
    {
        if (requestQueue == null) requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        return requestQueue;
    }

    // Add to the volley request queue
    public <T> void addToRequestQueue(Request<T> req, String tag, Context context)
    {
        req.setTag(tag);
        getRequestQueue(context).add(req);
    }

    // Get the image loader
    public ImageLoader getImageLoader()
    {
        return imageLoader;
    }

    // Cancel pending volley requests
    public void cancelPendingRequests(Object tag)
    {
        if (requestQueue != null) requestQueue.cancelAll(tag);
    }
}