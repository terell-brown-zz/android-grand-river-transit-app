package ca.simba.grtwaterloo;

import android.content.Context;

import ca.simba.grtwaterloo.database.DBHelper;

/**
 * Created by tmast_000 on 7/4/2015.
 */
public class InitialLaunch {

    private static Context context;

    public static void onInitialLaunch(Context c) {
        context = c;
        new Runnable() {
            @Override
            public void run() {
                if (Settings.isFirstLaunch(context)) {
                    Favourites.onInitialLaunch(context);
                    DBHelper.getInstance(context).onInitialLaunch(context);
                }
            }
        }.run();
    }
}
