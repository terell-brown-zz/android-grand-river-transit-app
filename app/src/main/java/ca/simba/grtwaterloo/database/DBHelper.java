package ca.simba.grtwaterloo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import ca.simba.grtwaterloo.Debug;
import ca.simba.grtwaterloo.util.DBUtils;
import ca.simba.grtwaterloo.util.DateTimeUtil;

public class DBHelper extends SQLiteAssetHelper {

    private static DBHelper sDBHelper;

    // Constants
    private static final String DATABASE_NAME = "grt.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DBHelperClass";

    public DBHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHelper getInstance(Context context) {
        if (sDBHelper == null) {
            sDBHelper = new DBHelper(context);
        }

        return sDBHelper;
    }

    private Cursor queryRoutes() {
        // Queries the routes table returning a cursor pointing to all bus routes

        SQLiteDatabase db = getReadableDatabase(); // Create open database in read mode

        // Setup query parameters
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder(); // class to help build queries
        String sqlTables = "routes"; // search the table called routes
        qb.setTables(sqlTables);
        String[] sqlSelect = {"route_id", "route_long_name"}; // return route_id and name from query
        String orderBy = "route_id ASC";

        // Run query
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, orderBy);
        c.moveToFirst();
        checkCursor(c); // indicates details of query in log under MyActivity TAG

        db.close();
        return c;
    }

    public String[] getAllRoutes() {
        // Returns a String Array of all bus routes where
        // each elmement contains the id and name of a route
        Cursor queryRoutes = queryRoutes();
        String[] result = DBUtils.queryToAllRoutes(queryRoutes);
        return result;
    }

    public Cursor queryStopsbyRoute(String routeId, int directionId) {
        // Returns
        SQLiteDatabase db = getReadableDatabase();

        // Build query
        String query =
                "SELECT DISTINCT stops.stop_id, stops.stop_name FROM stops " +
                        "INNER JOIN " +
                        "(SELECT stop_id AS my_stops FROM stop_times WHERE trip_id " +
                        "IN (SELECT trip_id FROM trips WHERE route_id = " + routeId +
                        " AND direction_id = " + directionId + ")) the_stops " +
                        "ON the_stops.my_stops = stops.stop_id" +
                        " ORDER BY stops.stop_name";
        Cursor c = db.rawQuery(query, null);
        if (Debug.LOG_ON) {Log.i("MyActivity", query);}
        c.moveToFirst();
        checkCursor(c); // indicates details of query in log under MyActivity TAG
        db.close();
        return c;
    }

    public Cursor queryStopsbySubRoute(String routeName, int directionId) {
        // Returns
        SQLiteDatabase db = getReadableDatabase();

        // Build query
        String query =
                "SELECT DISTINCT stops.stop_id, stops.stop_name FROM stops " +
                        "INNER JOIN " +
                        "(SELECT stop_id AS my_stops FROM stop_times WHERE trip_id " +
                        "IN (SELECT trip_id FROM trips WHERE trips.trip_headsign = '" + routeName +
                        "' AND direction_id = " + directionId + ")) the_stops " +
                        "ON the_stops.my_stops = stops.stop_id" +
                        " ORDER BY stops.stop_name";
        Cursor c = db.rawQuery(query, null);
        if (Debug.LOG_ON) {Log.i("MyActivity", query);}
        c.moveToFirst();
        checkCursor(c); // indicates details of query in log under MyActivity TAG
        db.close();
        return c;
    }


    public String[] getStopsByRoute(String routeId, int directionId) {
        Cursor queryStops = queryStopsbyRoute(routeId, directionId);
        return DBUtils.queryToAllRoutes(queryStops);
    }

    public String[] getStopsForMap(String routeId, int directionId) {
        Cursor queryStops = queryStopsbyRoute(routeId, directionId);
        return DBUtils.queryToAllRouteIds(queryStops);
    }

    public Cursor queryStopInfoForMap(String[] stops) {
        // Consumes an array of stop ids and returns a query result (ie. cursor)
        //   containing key information about each stop including name and location (long,lat)
        String inStatement = DBUtils.arrayToString(stops);

        SQLiteDatabase db = getReadableDatabase();

        // Build query
        String query =
                "SELECT stop_id, stop_name, stop_lat, stop_lon FROM stops" +
                        " WHERE stop_id IN( " + inStatement + ")";
        Cursor c = db.rawQuery(query, null);
        if (Debug.LOG_ON) {Log.i("MyActivity", query);}
        c.moveToFirst();
        checkCursor(c); // indicates details of query in log under MyActivity TAG
        db.close();
        return c;
    }


    public Cursor queryTimesByStop(String routeId, String serviceId, int directionId, int stopId) {
        // Return departure times at a given stop based on the route, time of week (service_id)
        // and direction of service (direction_id)

        SQLiteDatabase db = getReadableDatabase();


        // Build query
        String query = "SELECT * FROM " +
                "(SELECT stop_times.stop_id AS my_stops, stop_times.departure_time AS my_times FROM stop_times " +
                "INNER JOIN " +
                "(SELECT trip_id AS trips FROM trips WHERE route_id = " + routeId +
                " AND service_id =" + serviceId +
                ")" +
                " my_trips" +
                " ON stop_times.trip_id = my_trips.trips)" +
                " WHERE my_stops =" + stopId +
                " ORDER BY my_times ";

        if (Debug.LOG_ON) {Log.i("MyActivity", query);}

        // Run query
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        checkCursor(c); // indicates details of query in log under MyActivity TAG

        db.close();
        return c;
    }

    public String[] getTimes(String routeOrTripid, String serviceId, int directionId, int stopId, boolean isSubroute) {
        Cursor queryTimes;
        if (isSubroute) {
            queryTimes = queryTimesByTripName(routeOrTripid, serviceId, directionId, stopId);
        } else {
            queryTimes = queryTimesByStop(routeOrTripid, serviceId, directionId, stopId);
        }
        checkCursor(queryTimes);
        return DBUtils.queryToTimes(queryTimes);
    }

    public Cursor queryTimesByTripName(String tripName, String serviceId, int directionId, int stopId) {
        // Return departure times at a given stop based on the route, time of week (service_id)
        // and direction of service (direction_id)

        SQLiteDatabase db = getReadableDatabase();


        // Build query
        String query = "SELECT * FROM " +
                "(SELECT stop_times.stop_id AS my_stops, stop_times.departure_time AS my_times FROM stop_times " +
                "INNER JOIN " +
                "(SELECT trip_id AS trips FROM trips WHERE trip_headsign = '" + tripName +
                "' AND service_id =" + serviceId +
                ")" +
                " my_trips" +
                " ON stop_times.trip_id = my_trips.trips)" +
                " WHERE my_stops =" + stopId +
                " ORDER BY my_times ";

        if (Debug.LOG_ON) {Log.i("MyActivity", query);}

        // Run query
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        checkCursor(c); // indicates details of query in log under MyActivity TAG

        db.close();
        return c;
    }

    public Cursor queryNearbyStops(Double lat, Double lon) {
        // Based on the coordinates provided, returns the 8 nearest bus stops
        SQLiteDatabase db = getReadableDatabase();

        // Build query
        String query = "" +
                "SELECT stop_id, stop_name, stop_lat, stop_lon, " +
                "((stop_lon - " + lon + ")*(stop_lon - " + lon + ") +" +
                " (stop_lat - " + lat + ")*(stop_lat - " + lat +
                ")) as distance FROM stops WHERE distance < 0.00003 ORDER BY distance" +
                " LIMIT 8";

        if (Debug.LOG_ON) {Log.i("MyActivity", query);}

        // Run query
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        checkCursor(c); // indicates details of query in log under MyActivity TAG

        db.close();
        return c;
    }

    public String[] getNearbyStops(Double lat, Double lon) {
        Cursor queryStops = queryNearbyStops(lat, lon);
        checkCursor(queryStops);
        return DBUtils.queryToTimes(queryStops);
    }

    public Cursor queryRoutesByStop(String stopId) {
        // Based the stop and service time provided return the associated routes
        SQLiteDatabase db = getReadableDatabase();

        // Build query
        String query = "" +
                "SELECT DISTINCT route_id FROM trips WHERE trip_id" +
                " IN (SELECT trip_id FROM stop_times" +
                " WHERE stop_id = " + stopId + ")";

        if (Debug.LOG_ON) {Log.i("MyActivity", query);}

        // Run query
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        checkCursor(c); // indicates details of query in log under MyActivity TAG

        db.close();
        return c;
    }

    public String[] getRoutesByStop(String stopId) {
        Cursor queryRoutes = queryRoutesByStop(stopId);
        checkCursor(queryRoutes);
        return DBUtils.queryToRoutes(queryRoutes);
    }

    public Cursor queryNearbyStopInfo(String stopId, String serviceId, String routeIds) {
        // Returns query results containing upcoming stop times for the given stop
        //   basede on the routes and service provided

        SQLiteDatabase db = getReadableDatabase();

        // Process the arguments provided so they may be used for query
        String startTime = DateTimeUtil.getCurrentTime();
        String endTime = DateTimeUtil.setEndTime(3);

        // Build Query
        String query = "" +
                "SELECT DISTINCT trips.trip_headsign, stop_times.departure_time FROM trips" +
                //"SELECT DISTINCT trips.trip_headsign, (stop_times.departure_time - '" + startTime + "') AS remainingTime FROM trips" +
                " JOIN stop_times ON trips.trip_id = stop_times.trip_id" +
                " WHERE stop_id = " + stopId + " AND service_id = " + serviceId +
                " AND stop_times.departure_time BETWEEN '" + startTime + "' AND '" + endTime +
                //" AND remainingTime <= '1:30:00' " +
                "' AND trips.route_id IN(" + routeIds + ")" +
                " ORDER BY trips.route_id, trips.trip_headsign, stop_times.departure_time";

        if (Debug.LOG_ON) {Log.i("MyActivity", query);}
        // Run raw query
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        checkCursor(c);

        db.close();
        return c;
    }

    public String[][] getUpcomingTimes(String stopId, String serviceId, String[] routeIds) {
        String routes = DBUtils.arrayToString(routeIds);
        if (Debug.LOG_ON) {Log.i("MyActivity", routes);}
        Cursor queryUpcomingTimes = queryNearbyStopInfo(stopId, serviceId, routes);
        checkCursor(queryUpcomingTimes);
        return DBUtils.queryToStringArray(queryUpcomingTimes, routeIds.length);
    }

    private void checkCursor(Cursor c) {
        if (Debug.LOG_ON) {Log.i(TAG, "Validating the cursor");}
        if (c.getCount() < 1) {
            if (Debug.LOG_ON) {Log.i(TAG, "Query returned zero results.");}
        } else {
            if (Debug.LOG_ON) {
                Log.i(TAG, "Query was successful.");
                Log.i(TAG, "" + c.getCount() + " rows returned...");
                Log.i(TAG, "" + c.getColumnCount() + " columns returned...");
            }
        }
    }

    private Cursor querySubRoutes(String routeNo) {
        SQLiteDatabase db = getReadableDatabase();

        // Build Query
        String query = "" +
                "SELECT DISTINCT trip_headsign FROM trips WHERE route_id = '" + routeNo + "'" +
                "ORDER BY trip_headsign ASC;";

        if (Debug.LOG_ON) {Log.i("SubRoutes", query);}
        // Run raw query
        Cursor cSubRoutes = db.rawQuery(query, null);
        cSubRoutes.moveToFirst();
        checkCursor(cSubRoutes);

        db.close();
        return cSubRoutes;
    }

    public String[] getSubRoutes(String routeNo) {
        return DBUtils.queryToRoutes(querySubRoutes(routeNo));
    }

    private Cursor queryDirectionsByTripName(String tripName) {

        // TODO: use COUNT function instead
        SQLiteDatabase db = getReadableDatabase();

        // Build Query
        String query = "" +
                "SELECT DISTINCT direction_id FROM trips WHERE trip_headsign = '" + tripName + "';";

        if (Debug.LOG_ON) {Log.i("Directions", query);}
        // Run raw query
        Cursor cSubRoutes = db.rawQuery(query, null);
        cSubRoutes.moveToFirst();
        checkCursor(cSubRoutes);

        db.close();
        return cSubRoutes;
    }

    private Cursor queryDirectionsByRouteNo(String routeNo) {
        SQLiteDatabase db = getReadableDatabase();

        // Build Query
        String query = "" +
                "SELECT DISTINCT direction_id FROM trips WHERE route_id = '" + routeNo + "';";

        if (Debug.LOG_ON) {Log.i("Directions", query);}
        // Run raw query
        Cursor cSubRoutes = db.rawQuery(query, null);
        cSubRoutes.moveToFirst();
        checkCursor(cSubRoutes);

        db.close();
        return cSubRoutes;
    }

    public int getNumDirectionsByTripName(String tripName) {
        return queryDirectionsByTripName(tripName).getCount();
    }

    public int getNumDirectionsByRouteNo(String routeNo) {
        return queryDirectionsByRouteNo(routeNo).getCount();
    }


    public int queryDirectionByTripName(String tripName) {

        SQLiteDatabase db = getReadableDatabase();

        // Build Query
        String query = "" +
                "SELECT DISTINCT direction_id from trips WHERE trip_headsign = '" + tripName + "';";

        if (Debug.LOG_ON) {Log.i("Directions", query);}
        // Run raw query
        Cursor cDirection = db.rawQuery(query, null);
        cDirection.moveToFirst();
        checkCursor(cDirection);

        db.close();

        return Integer.parseInt(cDirection.getString(0));
    }

    public int queryDirectionByRouteNo(String routeNo) {

        SQLiteDatabase db = getReadableDatabase();

        // Build Query
        String query = "" +
                "SELECT DISTINCT direction_id from trips WHERE route_id = '" + routeNo + "';";
        if (Debug.LOG_ON) {Log.i("Directions", query);}

        // Run raw query
        Cursor cDirection = db.rawQuery(query, null);
        cDirection.moveToFirst();
        checkCursor(cDirection);

        db.close();

        return Integer.parseInt(cDirection.getString(0));
    }
}
