package tbrown.com.woodbuffalotransitmockup.util;

import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tmast_000 on 4/25/2015.
 */
public class FavouritesUtil {
    
    String sharedPrefs = "Favourite Stops and Routes";
    static String routePrefix = "Route -";
    static String stopPrefix = "Stop -";


    public static void addStowpToFavourites(SharedPreferences favourites, String stopName) {
        // This method adds the given stop to favourites which is stored
        //   in a Shared Preferences Object

        if (favourites.contains(stopName)){
            return;
        } else {
            SharedPreferences.Editor editor = favourites.edit();
            editor.putString(stopName, stopName);
        }
    }

    public void addStopToFavouerites(SharedPreferences favourites, String stopName,String routeName) {
        // This method adds the given stop along with an associated route
        //    to favourites which is stored in a Shared Preferences Object
        SharedPreferences.Editor editor = favourites.edit();
        if (favourites.contains(stopName)) {

            try {
                Set<String> set = new HashSet<String>();
                favourites.getStringSet(stopName, set);
            } catch (ClassCastException e) {
                editor.remove(stopName);
                Set<String> faveStop = addNewRouteToFavouriteStop(stopName, routeName);
                editor.putStringSet(stopName, faveStop);
            }
        } else {
            Set<String> faveStop = addNewRouteToFavouriteStop(stopName, routeName);
            editor.putStringSet(stopName, faveStop);
        }
    }
    private Set<String> addNewRouteToFavouriteStop(String stopName,String routeName){
        HashSet<String> faveStop = new HashSet<String>();
        faveStop.add(stopName);
        faveStop.add(routeName);
        return faveStop;
    }

    public static void addRouteToFavourites(SharedPreferences favourites, String routeName) {
        // This method adds the given route to favourites which is
        //   stored in a Shared Preferences Object

        if (favourites.contains(routePrefix + routeName)) {
            return;
        } else {
            SharedPreferences.Editor editor = favourites.edit();
            editor.putString(routePrefix + routeName, routeName);
            editor.apply();
        }
        
    }

    public static void removeRouteFromFavourite(SharedPreferences favourites, String routeName) {
        SharedPreferences.Editor editor = favourites.edit();
        editor.remove(routePrefix + routeName);
        editor.apply();
        //isFavourited = false;
    }

    public static void addStopToFavourites(SharedPreferences favourites, String stopName) {
        // This method adds the given route to favourites which is
        //   stored in a Shared Preferences Object

        if (favourites.contains(stopPrefix + stopName)) {
            return;
        } else {
            SharedPreferences.Editor editor = favourites.edit();
            editor.putString(stopPrefix + stopName, stopName);
            editor.apply();
        }

    }

    public static void removeStopFromFavourites(SharedPreferences favourites, String stopName) {
            SharedPreferences.Editor editor = favourites.edit();
            editor.remove(stopPrefix + stopName);
            editor.apply();
    }

    public static void removeRouteFromFavourites(SharedPreferences favourites, String stopName) {
        SharedPreferences.Editor editor = favourites.edit();
        editor.remove(stopPrefix + stopName);
        editor.apply();
    }
}
