package tbrown.com.woodbuffalotransitmockup.util;

import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tmast_000 on 4/25/2015.
 */
public abstract class FavouritesUtil {

    SharedPreferences favourites;
    String sharedPrefs = "My Favourite Stops and Routes";

    abstract void setupSharedPrefrences();

    public void addStopToFavourites(String stopName) {
        // This method adds the given stop to favourites which is stored
        //   in a Shared Preferences Object

        if (favourites.contains(stopName)){
            return;
        } else {
            SharedPreferences.Editor editor = favourites.edit();
            editor.putString(stopName, stopName);
        }
    }

    public void addStopToFavourites(String stopName,String routeName) {
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
            editor.putStringSet(stopName,faveStop);
        }
    }
    private Set<String> addNewRouteToFavouriteStop(String stopName,String routeName){
        HashSet<String> faveStop = new HashSet<String>();
        faveStop.add(stopName);
        faveStop.add(routeName);
        return faveStop;
    }
}
