package ca.simba.grtwaterloo;

/**
 * Created by tmast_000 on 6/22/2015.
 */
public class Constants {

    // Package Name for Intents
    public static final String PACKAGE_NAME = "ca.simba.grtwaterloo";

    // Toolbar titles for activities
    public static final String TITLE_FAVOURITES = "Favourites";
    public static final String TITLE_NEARBY = "Find A Nearby Stop";
    public static final String TITLE_PLANNER = "Trip Planner";
    public static final String TITLE_ALL_ROUTES = "Find A Route";

    // IDs for navigation drawer items
    public static final int FAVOURITES = 0;
    public static final int NEARBY = 1;
    public static final int PLANNER = 2;
    public static final int ALL_ROUTES = 3;

    // Keys for favourites shared preference
    public final static String KEY_ROUTE = "Route -";
    public static final String KEY_SUBROUTE = "SubRoute -";
    public static final String KEY_STOP = "Stop -";

    // Transit Service IDs
    public static final String WEEKDAYS_ALL = "'15SUMM-All-Weekday-01'";
    public static final String SATURDAY = "'15SUMM-All-Saturday-01'";
    public static final String SUNDAY = "'15SUMM-All-Sunday-01'";
    public static final String[] SERVICES = {WEEKDAYS_ALL,SATURDAY,SUNDAY};

    // Google Maps Trip Planner URL
    public static final String urlGoogleTripPlanner = "https://www.google.com/maps/dir///@43.4418282,-80.4909075,13z/data=!4m2!4m1!3e3";
    public static final double WATERLOO_LAT = 43.467;
    public static final double WATERLOO_LONG = -80.517;
    public static final double DISTANCE_CUTTOFF = 35000; // meters


    // Transit Directions
    public static String INBOUND = "Inbound";
    public static String OUTBOUND = "Outbound";
    public static final int INBOUND_ID = 0;
    public static final int OUTBOUND_ID = 1;
    public static String NUM_DIRECTIONS = "NUM_DIRECTIONS";

    // Spinner Items
    public static final int SPINNER_WEEKDAYS_ALL = 0;
    public static final int SPINNER_SATURDAY = 1;
    public static final int SPINNER_SUNDAY = 2;
}

