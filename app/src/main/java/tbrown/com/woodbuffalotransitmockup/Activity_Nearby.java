package tbrown.com.woodbuffalotransitmockup;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

import tbrown.com.woodbuffalotransitmockup.activities.fragments.StopInfoFragment;
import tbrown.com.woodbuffalotransitmockup.database.DBHelper;
import tbrown.com.woodbuffalotransitmockup.database.DBUtils;
import tbrown.com.woodbuffalotransitmockup.util.DateTimeUtil;
import tbrown.com.woodbuffalotransitmockup.util.MapUtils;


public class Activity_Nearby extends Activity_Base implements GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraChangeListener  {
    private static final int NAV_ID = 2;

    // Fragment that is a part of a Sliding Tab Activity.
    // Fragment contains a map which shows the closest bus stops according
    //   to the users current location. Closest stops are updated as the
    //   camera moves around.

    private FragmentManager fm;
    private StopInfoFragment dialog;


    private GoogleMap map;
    private LatLng lastKnownLocation;
    boolean wasMarkerClicked = true;

    private String[] routes;
    private String[][] times;
    private String serviceId;
    private String stopSelected;
    private String stopSelectedName;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        // called when creating the fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        activityContext = getBaseContext();
        setupDatabase(activityContext);
        setupToolbar("Find Nearby Stops");
        setupNavDrawer(NAV_ID);
        addMap();
        setupMap();
    }

    @Override
    public void onResume() {
        super.onResume();
        activityContext = getBaseContext();

        if (dbHelper == null) {
            setupDatabase(activityContext);
        }
    }

    private void addMap() {
        // adds the map fragment to the activity
        fm = getSupportFragmentManager(); // used to interact with multiple child fragments within parent fragment
        map = ((SupportMapFragment) fm.findFragmentById(R.id.map_nearby_stops)).getMap(); // a google map fragment
    }

    private void setupMap() {
        // applies specific settings to the map
        map.setMyLocationEnabled(true);
        map.setOnMarkerClickListener(this);
        map.setOnCameraChangeListener(this);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.467, -80.517), 16));
        lastKnownLocation = map.getCameraPosition().target;
        MapUtils.addMarkers(map, dbHelper, lastKnownLocation); // adds markers to representing closest stops to location provided
    }

    public void onCameraChange(CameraPosition cameraPosition) {
        // used to initiate some action when the position of the camera on the map changes
        if (wasMarkerClicked) {
            // if camera change caused by marker click, do nothing and set the wasMarkerClicked back to false
            wasMarkerClicked = false;
        } else {
            // else delete all markers currently on map and add new markers surrounding the new camera position
            LatLng newLocation = cameraPosition.target;
            map.clear();
            MapUtils.addMarkers(map, dbHelper, newLocation);
        }
    }

    @Override
    public boolean onMarkerClick(final Marker currentMarkerClicked) {
        // used to initiate some action when a marker on the map is clicked
            wasMarkerClicked = true;
            stopSelected = currentMarkerClicked.getSnippet();
            stopSelectedName = currentMarkerClicked.getTitle();
            showSnackBar();

            // Run query and setup fragment for the chance the same marker
            //   is clicked again. This is done in a different thread to prevent jank
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    // run query
                    android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND); // make this thread the main one
                    serviceId = DateTimeUtil.getServiceId();
                    routes = dbHelper.getRoutesByStop(stopSelected);
                    Log.i("MyActivity", "Task successfully run in background thread.");
                }

            };

            Thread markerLoaderThread = new Thread(task); // add the task to thread
            markerLoaderThread.start(); // start the thread thus performing the task provided
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_FOREGROUND); // return UI thread to main thread

        return false;
    }

    private void showSnackBar() {
        SnackbarManager.show(
                Snackbar.with(getApplicationContext()) // context
                        .text("Tap for upcoming stop times") // text to display
                        .actionLabel("Go") // action button label
                        .actionListener(new ActionClickListener() {
                            @Override
                            public void onActionClicked(Snackbar snackbar) {
                                if (routes == null || serviceId == null) {
                                    serviceId = DateTimeUtil.getServiceId();
                                    routes = dbHelper.getRoutesByStop(stopSelected);
                                }
                                // returns 2D array of upcoming stop times according to service time and routes provided
                                times = dbHelper.getUpcomingTimes(stopSelected, serviceId, routes);

                                Intent stopInfo = new Intent("tbrown.com.woodbuffalotransitmockup.STOP_INFO");
                                stopInfo.putExtra("STOP_NAME", stopSelected + " " + stopSelectedName);
                                stopInfo.putExtra("ROUTES", DBUtils.twoDToOneDArray(times, 0)); // array of routes going to the stop
                                stopInfo.putExtra("TIMES", DBUtils.twoDToOneDArray(times, 1));  // array of upcoming stop times for each route
                                startActivity(stopInfo);
                            }
                        }) // action button's ActionClickListener
                            , this); // activity where it is displayed
    }


}
