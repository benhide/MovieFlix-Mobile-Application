package com.hid16605093.movieflix.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

References:

CONNECTIVITY MANAGER getNetworkInfo(int) DEPRECATED
- https://stackoverflow.com/questions/32547006/connectivitymanager-getnetworkinfoint-deprecated
*/

// Connection test class
public class ConnectionTest
{
    // Network type
    private static Integer networkType;

    // Check connection
    private static boolean isConnected(Context context)
    {
        // Network
        NetworkCapabilities capabilities;
        NetworkInfo networkInfo;

        // Error handling
        try
        {
            // Internet connection manager
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            // More error handling
            try
            {
                // Connection manager exists
                if (cm != null)
                {   Network network = cm.getActiveNetwork();
                    capabilities = cm.getNetworkCapabilities(network);
                    networkInfo = cm.getActiveNetworkInfo();
                }
                else return false;
            }
            catch (NullPointerException e)
            {
                e.getStackTrace();
                return false;
            }

            // If network available
            if (capabilities != null)
            {
                // If wifi
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                {
                    networkType = NetworkCapabilities.TRANSPORT_WIFI;
                    return networkInfo.isConnected();
                }

                // If mobile data
                else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                {
                    networkType = NetworkCapabilities.TRANSPORT_CELLULAR;
                    return networkInfo.isConnected();
                }
            }
        }

        // Error handling
        catch (NullPointerException e)
        {
            e.getStackTrace();
            return false;
        }

        // Not connected
        return false;
    }

    // Check connection and internet
    public static boolean isConnectedAndNetwork(Context context)
    {
        return isConnected(context);
    }

    // Get the network type
    @Nullable
    public static Integer connectionType(Context context)
    {
        if (isConnected(context)) return networkType;
        else return null;
    }
}
