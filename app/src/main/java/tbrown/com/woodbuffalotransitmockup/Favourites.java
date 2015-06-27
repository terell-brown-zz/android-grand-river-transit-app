package tbrown.com.woodbuffalotransitmockup;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tmast_000 on 6/26/2015.
 */
public class Favourites {

    private static SharedPreferences favourites;
    private static final String sharedPrefs = "My Favourite Stops and Routes";

    public static SharedPreferences getInstance(Context context) {
        if (favourites == null) {
            favourites = context.getApplicationContext().getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE);
        }

        return favourites;
    }
}
