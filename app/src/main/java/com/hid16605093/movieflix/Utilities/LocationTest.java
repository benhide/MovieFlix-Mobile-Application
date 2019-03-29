package com.hid16605093.movieflix.Utilities;

import android.content.Context;
import android.location.LocationManager;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

References:

ANDROID CHECK AND ENABLE LOCATION SERVICES
- http://hmkcode.com/android-check-enable-location-service/
*/

// Location test class
public class LocationTest
{
    // Is location available
    public static boolean isLocationServiceEnabled(Context context)
    {
        // Location manager
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = ConnectionTest.isConnectedAndNetwork(context);

        // Error handling
        try
        {
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }

        // Error handling
        catch (Exception e)
        {
            e.getStackTrace();
        }

        // Return the location service availability
        return gpsEnabled || networkEnabled;
    }
}