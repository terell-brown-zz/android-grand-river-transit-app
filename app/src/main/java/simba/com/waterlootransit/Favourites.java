package simba.com.waterlootransit;

import android.content.Context;
import android.content.SharedPreferences;

import simba.com.waterlootransit.util.FavouritesUtil;

/**
 * Created by tmast_000 on 6/26/2015.
 */
public class Favourites {

    private static SharedPreferences favourites;

    // Constants
    private static final String SHARED_PREFS = "My Favourite Stops and Routes";
    private static final String KEY_ROUTE = Constants.KEY_ROUTE;
    private static final String KEY_SUBROUTE = Constants.KEY_SUBROUTE;
    private static final String KEY_STOP = Constants.KEY_STOP;

    // Favourite Stops and Routes on First Use
    private static final String STOP1 = "2519 U.W. - B.C. Matthews Hall";
    private static final String STOP2 = "1123 U.W. - Davis Centre";
    private static final String STOP3 = "5024 University / Seagram";
    private static final String STOP4 = "3620 Laurier";
    private static final String STOP5 = "3943 University / Philip";
    private static final String[] STOPS = {STOP2,STOP3,STOP4};
    private static final String ROUTE1 = "7 Mainline";
    private static final String ROUTE2 = "8 University / Fairview Park";
    private static final String ROUTE3 = "9 Lakeshore";
    private static final String ROUTE4 = "202 iXpress University";
    private static final String[] ROUTES = {ROUTE1,ROUTE4};

    public static SharedPreferences getInstance(Context context) {
        if (favourites == null) {
            favourites = context.getApplicationContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        }

        if (Settings.isFirstLaunch(context)) {
            for (String stop: STOPS) {
                FavouritesUtil.addStopToFavourites(favourites, stop);
            }
            for (String route: ROUTES) {
                FavouritesUtil.addRouteToFavourites(favourites, route);
            }
        }
        return favourites;
    }
}
