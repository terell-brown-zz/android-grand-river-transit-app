package tbrown.com.woodbuffalotransitmockup.database;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
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

        String result = "";

        for (int i = 0; i < array.length; i++) {
            result = result + array[i] + ", ";
        }
        result = result.substring(0,result.length()); // remove excess comma as last character
        return result.substring(0,result.length()-2);
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

    public static String[] queryToAllRouteIds(Cursor c) {
        // Consumes a cursor (ie. query result) containing a list of routes
        //   and returns a string array with the same information
        int noRoutes = c.getCount();
        String[] result = new String[noRoutes];

        c.moveToFirst();
        for (int i = 0; i < noRoutes; i++) {
            //String routeId = c.getString(0);
            //String routeName = c.getString(1);
            result[i] = c.getString(0);
            c.moveToNext();
        }
        return result;
    };

    public static String[][] queryToStringArray(Cursor c) {
        int noEntries = c.getCount();
        int i = 0;
        int j = 0;
        String[][] result = new String[noEntries][2];

        c.moveToFirst();

        //while (c.isAfterLast() == false ) {
          //  String route;
            //String times;


         //   c.moveToNext();
        //}




        while (i < noEntries) {
            String routeInfo = c.getString(1);
            String times = c.getString(2);
            c.moveToNext();
            i++;
            while (true) {
                if (routeInfo.equals(c.getString(1))) {
                    times = times + ", " + c.getString(2);
                    c.moveToNext();
                } else {
                    result[j][0] = routeInfo;
                    result[j][1] = times;
                    j++;
                    break;
                }
            }
        }
        return result;
    }

    public static String[][] queryToStringArray2(Cursor c,int numRoutes) {

        if (c.getCount() == 0) {
            return new String[][] {{"Sorry, there is no service to this stop today",""}};
        }

        int i = 0;
        int j = 1;

        String currentRoute;
        String nextRoute;
        // For size of 2D string array
        c.moveToFirst();
        currentRoute = c.getString(0);

        while (c.isAfterLast() == false ) {
        try {
            c.moveToNext();
            nextRoute = c.getString(0);
            if(!currentRoute.equals(nextRoute)) {
                currentRoute = nextRoute;
                j++;
            }
        } catch (CursorIndexOutOfBoundsException exception) {

        }
        }

        //String[][] result = new String[numRoutes][2];
        String[][] result = new String[j][2];

        c.moveToFirst();
        String times;

        while (c.isAfterLast() == false ) {
            currentRoute = c.getString(0);
            times = c.getString(1);
            c.moveToNext();
            nextRoute = c.getString(0);
            while (currentRoute.equals(nextRoute)) {
                times = times + ", " + c.getString(1);
                c.moveToNext();
                try {
                    nextRoute = c.getString(0);
                } catch (CursorIndexOutOfBoundsException exception) {
                    result[i][0] = currentRoute;
                    result[i][1] = times;
                    return result;
                }
            }
            result[i][0] = currentRoute;
            result[i][1] = times;
            i++;

            }
        return result;
    }


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
