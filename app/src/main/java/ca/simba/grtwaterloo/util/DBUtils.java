package ca.simba.grtwaterloo.util;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

/**
 * Provides methods used throughout the app to convert db query results to useable objects
 */
public class DBUtils {

    public static String arrayToString(String[] array) {
        // Converts string array to a single string, each value separated by ","
        String result = "";

        for (int i = 0; i < array.length; i++) {
            result = result + array[i] + " , ";
        }
        return result.substring(0, result.length() - 3);
    }

    public static String[] queryToAllRoutes(Cursor cRoutes) {
        int noRoutes = cRoutes.getCount();
        String[] result = new String[noRoutes];

        cRoutes.moveToPosition(0);
        for (int i = 0; i < noRoutes; i++) {
            String routeId = cRoutes.getString(0);
            String routeName = cRoutes.getString(1);
            result[i] = routeId + " " + routeName;
            cRoutes.moveToNext();
        }
        return result;
    }

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
        // TODO: Add comments for clarity
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
                exception.getCause();
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
        int numTimes = c.getCount();
        String[] result = new String[numTimes];

        c.moveToPosition(0);
        for (int i = 0; i < numTimes; i++) {
            String time = DateTimeUtil.apply12HrTimeFormat(c.getString(1));
            result[i] = time;
            c.moveToNext();
        }
        return result;
    }

    public static String[] twoDToOneDArray(String[][] array, int col) throws ArrayIndexOutOfBoundsException {
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
