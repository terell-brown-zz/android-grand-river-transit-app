package tbrown.com.woodbuffalotransitmockup;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

import tbrown.com.woodbuffalotransitmockup.database.DBHelper;
import tbrown.com.woodbuffalotransitmockup.database.DBUtils;
import tbrown.com.woodbuffalotransitmockup.util.DateTimeUtil;

public class Activity_StopsMap extends FragmentActivity implements GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Context activityContext;
    private DBHelper dbHelper;
    private String[] stopIds;
    private String stopInfo;
    private int stopId;
    private String routeInfo;
    private int routeNo;
    private int directionId;

    private static final String WEEKDAYS_ALL = "'15SPRI-All-Weekday-01'";
    private static final String SATURDAY = "'15SPRI-All-Saturday-01'";
    private static final String SUNDAY = "'15SPRI-All-Sunday-01'";

    private Intent stopDetailsIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        activityContext = getBaseContext();
        setup();
        setupDatabase(activityContext);
        setUpMapIfNeeded();
        addMarkers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setup() {
        getStops();
        getIntentInfo();
    }

    private void getStops() {
        stopIds = getIntent().getStringArrayExtra("STOP_IDS");
    }

    private void addMarkers() {
        // Adds markers to the map corresponding to the location of the stops
        Cursor stops = dbHelper.queryStopInfoForMap(stopIds);
        int noStops = stops.getCount();

        stops.moveToPosition(0);
        for (int i = 0; i < noStops; i++) {
            if(i == noStops/2) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(
                                Double.parseDouble(stops.getString(2)),
                                Double.parseDouble(stops.getString(3))), 13));
            }
            addNewMarker(stops.getString(1),
                    stops.getString(0),
                    Double.parseDouble(stops.getString(2)),
                    Double.parseDouble(stops.getString(3))
            );

            stops.moveToNext();
        }

    }

    private void setupDatabase(Context activityContext) {
        dbHelper = new DBHelper(activityContext);
    }

    private void setUpMap() {
        addMarkers();
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(this);
    }
    private void addNewMarker(String name,String descr,Double Lat, Double Lng) {
        // Adds a marker to the current map based on the parameters given

        LatLng location = new LatLng(Lat,Lng);

        mMap.addMarker(new MarkerOptions()
                        .title(name)
                        .snippet(descr)
                        .position(location)
        );
    }

    private void getIntentInfo() {
        // Imports important information from intent
        Intent intent = getIntent();

        routeNo = intent.getIntExtra("ROUTE_NO", 4000);
        routeInfo = intent.getStringExtra("ROUTE_INFO");
        directionId = intent.getIntExtra("DIRECTION_ID", 0);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        stopInfo = marker.getSnippet() + " " + marker.getTitle();
        stopId = Integer.parseInt(marker.getSnippet());
        showSnackBar();
        Runnable task = new Runnable() {
                @Override
                public void run() {
                    android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND); // make this thread the main one
                    stopDetailsIntent = new Intent("tbrown.com.woodbuffalotransitmockup.STOP_TIMES");
                    stopDetailsIntent.putExtra("ROUTE_INFO",routeInfo);
                    stopDetailsIntent.putExtra("ROUTE_NO",routeNo);
                    stopDetailsIntent.putExtra("STOP_INFO",stopInfo);
                    stopDetailsIntent.putExtra("STOP_ID",stopId);
                    stopDetailsIntent.putExtra("SERVICE_ID",WEEKDAYS_ALL);
                    stopDetailsIntent.putExtra("DIRECTION_ID",directionId);
                    stopDetailsIntent.putExtra("SPINNER_SELECTION",0);
                    stopDetailsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
            };
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_FOREGROUND); // return UI thread to main thread
            Thread markerLoaderThread = new Thread(task);
            markerLoaderThread.start();

        return false;
    }

    public void showStopDetails() {
        activityContext.startActivity(stopDetailsIntent);
    }

    private void showSnackBar() {
        SnackbarManager.show(
                Snackbar.with(getApplicationContext()) // context
                        .text("Tap for list of stop times") // text to display
                        .actionLabel("Go") // action button label
                        .actionListener(new ActionClickListener() {
                            @Override
                            public void onActionClicked(Snackbar snackbar) {
                                showStopDetails();
                            }
                        }) // action button's ActionClickListener
                , this); // activity where it is displayed
    }

}
