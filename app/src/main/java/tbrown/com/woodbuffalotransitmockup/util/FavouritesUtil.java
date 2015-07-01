package tbrown.com.woodbuffalotransitmockup.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tbrown.com.woodbuffalotransitmockup.Constants;

/**
 * Provides methods used for dealing with favourite stops and routes (stored in SharedPreferences)
 */
public class FavouritesUtil {
    // TODO: rename to FavouritesHelper
    // TODO: implement singleton design pattern to create one instance of SharedPreferences for entire app

    // Constants
    private static final String KEY_ROUTE = Constants.KEY_ROUTE;
    private static final String KEY_SUBROUTE = Constants.KEY_SUBROUTE;
    private static final String KEY_STOP = Constants.KEY_STOP;


    public static void addRouteToFavourites(SharedPreferences favourites, String routeName) {
        if (favourites.contains(KEY_ROUTE + routeName)) {
            // do not add to favourites
            return;
        } else {
            SharedPreferences.Editor editor = favourites.edit();
            editor.putString(KEY_ROUTE + routeName, routeName);
            editor.apply();
        }
    }

    public static void addSubRouteToFavourite(SharedPreferences favourites, String subRouteName) {
        String key = KEY_SUBROUTE + subRouteName;
        if (favourites.contains(key)) {
            // do not add to favourites
            return;
        } else {
            SharedPreferences.Editor editor = favourites.edit();
            editor.putString(key, subRouteName);
            editor.apply();
        }
    }

    public static void addStopToFavourites(SharedPreferences favourites, String stopInfo) {
        if (favourites.contains(KEY_STOP + stopInfo)) {
            // do not add to favourites
            return;
        } else {
            SharedPreferences.Editor editor = favourites.edit();
            editor.putString(KEY_STOP + stopInfo, stopInfo);
            editor.apply();
        }
    }

    public static void removeRouteFromFavourite(SharedPreferences favourites, String routeName) {
        SharedPreferences.Editor editor = favourites.edit();
        editor.remove(KEY_ROUTE + routeName);
        editor.apply();
    }

    public static void removeSubRouteFromFavourite(SharedPreferences favourites, String subRouteName) {
        SharedPreferences.Editor editor = favourites.edit();
        editor.remove(KEY_ROUTE + subRouteName);
        editor.apply();
    }

    public static void removeStopFromFavourites(SharedPreferences favourites, String stopInfo) {
            SharedPreferences.Editor editor = favourites.edit();
            editor.remove(KEY_STOP + stopInfo);
            editor.apply();
    }



    public static String[] getFavouritesArray(SharedPreferences favourites) {
    /*
     * getFavouritesArray converts shared preferences containing favourite stops and routes into string array
     * Conversion
     * favourites:
     * {("Stop - 1010 Stop Name","1010 Stop Name"),...,("Route - 1 Route Name","1 Route Name"),...}
     * -> string array:
     * {"Stops","1010 - Stop Name",...,"Routes","1 Route Name,...}
     *
     */

        // write K-V pairs in shared preferences to map
        Map<String, ?> copiedPrefs = favourites.getAll();

        // list where favourite stops/routes will be written to
        List<String> favesList = new ArrayList<String>();

        addFavouriteStopsToList(copiedPrefs, favesList);
        addFavouriteRoutesToList(copiedPrefs, favesList);
        addFavouriteSubRoutesToList(copiedPrefs, favesList);

        return favesList.toArray(new String[favesList.size()]);
    }

    private static void addFavouriteRoutesToList(Map<String, ?> favouritesMap, List<String> faveList) {
        // add routes stored in map to list

        faveList.add("Routes"); // indicates list items to follow are stops

        for (Map.Entry<String, ?> entry: favouritesMap.entrySet()) {
            String key = entry.getKey(); // either a name of stop or route
            if (isRoute(key)) {
                faveList.add(key.substring(KEY_ROUTE.length()));
            }
        }
    }
    private static void addFavouriteSubRoutesToList(Map<String, ?> favouritesMap, List<String> faveList) {
        // add routes stored in map to list

        faveList.add("Sub Routes"); // indicates list items to follow are stops

        for (Map.Entry<String, ?> entry: favouritesMap.entrySet()) {
            String key = entry.getKey(); // either a name of stop or route
            if (isSubRoute(key)) {
                faveList.add(key.substring(KEY_SUBROUTE.length()));
            }
        }
    }


    private static void addFavouriteStopsToList(Map<String, ?> favouritesMap, List<String> faveList) {
        // add stops stored in map to list

        faveList.add("Stops"); // indicates list items to follow are stops

        for (Map.Entry<String, ?> entry: favouritesMap.entrySet()) {
            String key = entry.getKey(); // either a name of stop or route
            if (isStop(key)) {
                faveList.add(key.substring(KEY_STOP.length()));
            }
        }
    }

    private static boolean isRoute(String key) {
        // returns true if key refers to route, otherwise false
        String temp = key.substring(0, KEY_ROUTE.length());
        return temp.equals(KEY_ROUTE);
    }

    private static boolean isSubRoute(String key) {
        // returns true if key refers to route, otherwise false
        String temp = key.substring(0, KEY_SUBROUTE.length());
        return temp.equals(KEY_SUBROUTE);
    }

    private static boolean isStop(String key) {
        // returns true if key refers to a stop, otherwise false
        String temp = key.substring(0, KEY_STOP.length());
        return temp.equals(KEY_STOP);
    }


}



