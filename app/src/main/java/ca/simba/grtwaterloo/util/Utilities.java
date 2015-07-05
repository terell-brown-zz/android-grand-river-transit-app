package ca.simba.grtwaterloo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ca.simba.grtwaterloo.Constants;

/**
 * Provides general purpose methods for use by other classes
 */
public class Utilities {

    public static int getSeparatingIndex(String info) {
        return info.indexOf(" ");
    }

   public static int getCurrentSpinnerItem(String serviceId) {
       switch(serviceId) {
           case Constants.WEEKDAYS_ALL:
               return Constants.SPINNER_WEEKDAYS_ALL;
           case Constants.SATURDAY:
               return Constants.SPINNER_SATURDAY;
           case Constants.SUNDAY:
               return Constants.SPINNER_SUNDAY;
           default:
               return Constants.SPINNER_WEEKDAYS_ALL;
       }
   }

    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
