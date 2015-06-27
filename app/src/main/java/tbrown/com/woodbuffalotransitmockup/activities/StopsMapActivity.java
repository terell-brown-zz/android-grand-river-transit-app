package tbrown.com.woodbuffalotransitmockup.activities;

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

import tbrown.com.woodbuffalotransitmockup.Constants;
import tbrown.com.woodbuffalotransitmockup.R;
import tbrown.com.woodbuffalotransitmockup.database.DBHelper;
import tbrown.com.woodbuffalotransitmockup.util.MapUtils;

/*
  Displays all stops allong a selected route on a map
 */

public class StopsMapActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener {

    // Backend Components
    private Context activityContext;
    private DBHelper dbHelper;

    private Intent stopDetailsIntent;

    private GoogleMap mMap;

    // Business Logic
    private String[] stopIds;
    private String stopInfo;
    private int stopId;
    private String routeInfo;
    private int routeNo;
    private int directionId;

    // Constants
    private static final String WEEKDAYS_ALL = Constants.WEEKDAYS_ALL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        activityContext = getBaseContext();
        getTransitInfo();
        setupDatabase(activityContext);
        setUpMapIfNeeded();
    }
    private void setupDatabase(Context activityContext) {
        dbHelper = DBHelper.getInstance(activityContext);
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

    private void setUpMap() {
        MapUtils.addStopMarkersByRoute(dbHelper,stopIds,mMap);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(this);
    }

    private void getTransitInfo() {
        Intent intent = getIntent();
        stopIds = intent.getStringArrayExtra("STOP_IDS");
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
                    android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

                    stopDetailsIntent = new Intent("tbrown.com.woodbuffalotransitmockup.STOP_TIMES");
                    stopDetailsIntent.putExtra("ROUTE_INFO",routeInfo);
                    stopDetailsIntent.putExtra("ROUTE_NO",routeNo);
                    stopDetailsIntent.putExtra("STOP_INFO",stopInfo);
                    stopDetailsIntent.putExtra("STOP_ID",stopId);
                    stopDetailsIntent.putExtra("SERVICE_ID",WEEKDAYS_ALL);
                    stopDetailsIntent.putExtra("DIRECTION_ID",directionId);
                    stopDetailsIntent.putExtra("SPINNER_SELECTION",0);
                    stopDetailsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_FOREGROUND);
                }
            };

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
                        .actionLabel("GO") // action button label
                        .actionListener(new ActionClickListener() {
                            @Override
                            public void onActionClicked(Snackbar snackbar) {
                                showStopDetails();
                            }
                        })
                , this);
    }
}
