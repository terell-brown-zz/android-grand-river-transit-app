package tbrown.com.woodbuffalotransitmockup.database;

import android.database.Cursor;
import android.util.Log;

/**
 * Created by tmast_000 on 5/22/2015.
 */
public class DBUtils {

    private static final String TAG = "MyActivity";

    public static void checkCursor(Cursor c) {
        Log.e(TAG, "Validating the cursor");
        if (c.getCount()<1) {
            Log.i(TAG,"Query returned zero results.");
        } else {
            Log.i(TAG,"Query was successful.");
            Log.i(TAG,"" + c.getCount() + " rows returned...");
            Log.i(TAG, "" + c.getColumnCount() + " columns returned...");
        }
    }

    public static String arrayToString(String[] array) {
        // Converts string array to a single string, each value seperated by comma

        String result = "=";

        for (int i = 0; i < array.length; i++) {
            result = result + array[i] + ", ";
        }
        result = result.substring(0,result.length()); // remove excess comma as last character
        return result.substring(0,result.length()-1);
    }

    public static String queryToString(Cursor c) {
        String result = "";

        for (int i = 0; i < c.getCount(); i++) {
            result = result + c.getString(0) + ", ";
            c.moveToNext();
        }
        return result;
    };

    public static String[] queryToAllRoutes(Cursor c) {
        int noRoutes = c.getCount();
        String[] result = new String[noRoutes];

        c.moveToPosition(0);
        for (int i = 0; i < noRoutes; i++) {
            String routeId = c.getString(0);
            String routeName = c.getString(1);
            result[i] = routeId + " " + routeName;
            c.moveToNext();
        }
        return result;
    };

    public static String[] queryToTimes(Cursor c) {
        int noTimes = c.getCount();
        String[] result = new String[noTimes];

        c.moveToPosition(0);
        for (int i = 0; i < noTimes; i++) {
            //String stopID = c.getString(0);
            String time = c.getString(1);
            result[i] = time;
            c.moveToNext();
        }
        return result;
    };
}
