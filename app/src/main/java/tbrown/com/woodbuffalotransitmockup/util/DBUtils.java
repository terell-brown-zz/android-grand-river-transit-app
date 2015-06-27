package tbrown.com.woodbuffalotransitmockup.util;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.util.Log;

import tbrown.com.woodbuffalotransitmockup.util.DateTimeUtil;

/**
 * Created by tmast_000 on 5/22/2015.
 */
public class DBUtils {

    private static final String TAG = "MyActivity";

    public static void checkCursor(Cursor c) {
        Log.e(TAG, "Validating the cursor");
        if (c.getCount() < 1) {
            Log.i(TAG, "Query returned zero results.");
        } else {
            Log.i(TAG, "Query was successful.");
            Log.i(TAG, "" + c.getCount() + " rows returned...");
            Log.i(TAG, "" + c.getColumnCount() + " columns returned...");
        }
    }

    public static String arrayToString(String[] array) {
        // Converts string array to a single string, each value seperated by "|"

        String result = "";

        for (int i = 0; i < array.length; i++) {
            result = result + array[i] + " , ";
        }
        //result = result.substring(0,result.length()); // remove excess comma as last character
        return result.substring(0, result.length() - 3);
    }

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
    }

    ;

    public static String[] queryToAllRouteIds(Cursor cRoutes) {
        // Consumes a cursor (ie. query result) containing a list of routes
        //   and returns a string array with the same information
        int noRoutes = cRoutes.getCount();
        String[] result = new String[noRoutes];

        cRoutes.moveToFirst();
        for (int i = 0; i < noRoutes; i++) {
            result[i] = cRoutes.getString(0);
            cRoutes.moveToNext();
        }
        return result;
    }

    ;

    public static String[] queryToRoutes(Cursor cTimes) {
        // Consumes a cursor (ie. query result) containing a list of times
        //   and returns a string array with the same information
        int noRoutes = cTimes.getCount();
        String[] result = new String[noRoutes];

        cTimes.moveToFirst();
        for (int i = 0; i < noRoutes; i++) {
            result[i] = cTimes.getString(0);
            cTimes.moveToNext();
        }
        return result;
    }


    public static String[][] queryToStringArray(Cursor c, int numRoutes) {

        if (c.getCount() == 0) {
            return new String[][]{{"No buses to this stop in the next 4 hrs."}};
        }

        int i = 0;
        int j = 1;

        String currentRoute;
        String nextRoute;

        // For size of 2D string array
        c.moveToFirst();
        currentRoute = c.getString(0);

        while (c.isAfterLast() == false) {
            try {
                c.moveToNext();
                nextRoute = c.getString(0);
                if (!currentRoute.equals(nextRoute)) {
                    currentRoute = nextRoute;
                    j++;
                }
            } catch (CursorIndexOutOfBoundsException exception) {
                c.moveToPrevious();
            }
        }

        String[][] result = new String[j][2];

        c.moveToFirst();
        String times;

        while (c.isAfterLast() == false) {
            currentRoute = c.getString(0);
            times = DateTimeUtil.apply12HrTimeFormat(c.getString(1));
            c.moveToNext();
            nextRoute = c.getString(0);
            while (currentRoute.equals(nextRoute)) {
                times = times + ", " + DateTimeUtil.apply12HrTimeFormat(c.getString(1));
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
            String time = DateTimeUtil.apply12HrTimeFormat(c.getString(1));
            result[i] = time;
            c.moveToNext();
        }
        return result;
    }

    public static String[] twoDToOneDArray(String[][] array, int col) {
        // Converts 2D String Array into 1D String array based on column number provided
        String[] result = new String[array.length];
        int i = 0;
        for (String[] row : array) {
            result[i] = row[col];
            i++;
        }
        return result;
    }
}
