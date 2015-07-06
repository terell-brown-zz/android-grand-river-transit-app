package ca.simba.grtwaterloo;

import android.content.Context;
import android.content.SharedPreferences;

import ca.simba.grtwaterloo.util.FavouritesUtil;

/**
 * Singleton Design Pattern applied to Favourites Object
 */
public class Favourites {

    private static SharedPreferences favourites;

    // Constants
    private static final String SHARED_PREFS = "My Favourite Stops and Routes";

    // Favourite Stops and Routes on Initial Launch
    private static final String STOP1 = "1123 U.W. - Davis Centre";
    private static final String STOP2 = "5024 University / Seagram";
    private static final String STOP3 = "3620 Laurier";
    private static final String[] STOPS = {STOP1, STOP2, STOP3};
    private static final String ROUTE1 = "7 Mainline";
    private static final String ROUTE2 = "202 iXpress University";
    private static final String[] ROUTES = {ROUTE1, ROUTE2};

    public static SharedPreferences getInstance(Context context) {
        if (favourites == null) {
            favourites = context.getApplicationContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        }
        return favourites;
    }

    public static void onInitialLaunch(Context context) {

        getInstance(context);
        for (String stop : STOPS) {
            FavouritesUtil.addStopToFavourites(favourites, stop);
        }
        for (String route : ROUTES) {
            FavouritesUtil.addRouteToFavourites(favourites, route);
        }
    }
}
