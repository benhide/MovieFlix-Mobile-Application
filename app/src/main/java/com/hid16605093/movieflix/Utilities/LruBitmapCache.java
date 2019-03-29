package com.hid16605093.movieflix.Utilities;

import android.content.Context;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

References:

ANDROID VOLLEY TUTORIAL - ASYNC NETWORKING
- https://www.androidtutorialpoint.com/networking/android-volley-tutorial/
*/

// LruBitmapCache class
public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageCache
{
    // Constructor
    private LruBitmapCache(int maxSize)
    {
        super(maxSize);
    }
    LruBitmapCache(Context ctx)
    {
        this(getCacheSize(ctx));
    }

    // Returns a cache size
    private static int getCacheSize(Context ctx)
    {
        final DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;

        // 4 bytes per pixel
        final int screenBytes = screenWidth * screenHeight * 4;
        return screenBytes * 3;
    }

    // Get the size of the bitmap
    @Override
    protected int sizeOf(@NonNull String key, @NonNull Bitmap value)
    {
        return value.getRowBytes() * value.getHeight();
    }

    // Get the bitmap
    @Override
    public Bitmap getBitmap(String url)
    {
        return get(url);
    }

    // Put new bitmap
    @Override
    public void putBitmap(String url, Bitmap bitmap)
    {
        put(url, bitmap);
    }
}