package tbrown.com.woodbuffalotransitmockup.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

import tbrown.com.woodbuffalotransitmockup.Constants;
import tbrown.com.woodbuffalotransitmockup.R;
import tbrown.com.woodbuffalotransitmockup.util.DBUtils;
import tbrown.com.woodbuffalotransitmockup.util.DateTimeUtil;
import tbrown.com.woodbuffalotransitmockup.util.MapUtils;

/*
  Displays map showing bus stops that are close to your current location
 */

public class NearbyStopsActivity extends BaseActivity implements GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraChangeListener, ActionClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    // UI
    private FragmentManager fm;

    // Map Fields
    private GoogleMap map;
    private LatLng lastKnownLocation;
    private boolean wasMarkerClicked = true;
    private Location currentLocation;
    private double currentLong;
    private double currentLat;
    private float cameraZoom = 16;

    // Business Logic
    private String[] routes;
    private String[][] times;
    private String serviceId;
    private String stopSelected;
    private String stopSelectedName;

    // Constants
    private static final String TOOLBAR_TITLE = Constants.TITLE_NEARBY;
    private static final int NAV_ID = Constants.NEARBY;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        activityContext = getBaseContext();
        setupDatabase(activityContext);

        setupToolbar(TOOLBAR_TITLE);
        setupNavDrawer(NAV_ID);
        addMap();
        setupMap();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onResume() {
        super.onResume();
        activityContext = getBaseContext();

        if (dbHelper == null) {
            setupDatabase(activityContext);
        }

        if (map == null) {
            addMap();
            setupMap();
        }
    }

    private void addMap() {
        fm = getSupportFragmentManager();
        map = ((SupportMapFragment) fm.findFragmentById(R.id.map_nearby_stops)).getMap(); // a google map fragment
        //buildGoogleApiClient();
        //mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    private void setupMap() {

        getCurrentLocation();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(currentLat, currentLong), cameraZoom));
        lastKnownLocation = map.getCameraPosition().target;
        MapUtils.addNearbyStopMarkers(map, dbHelper, lastKnownLocation);
        map.setMyLocationEnabled(true);
        map.setOnMarkerClickListener(this);
        map.setOnCameraChangeListener(this);
    }

    private void getCurrentLocation() {

        currentLocation = MapUtils.getCurrentLocation(activityContext.getApplicationContext());

        if (currentLocation != null) {
            currentLat = currentLocation.getLatitude();
            currentLong = currentLocation.getLongitude();
        } else {
            currentLat = Constants.WATERLOO_LAT;
            currentLong = Constants.WATERLOO_LONG;
            cameraZoom = 11;
        }
    }



    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        if (wasMarkerClicked) {
            // do nothing
            wasMarkerClicked = false;
        } else {
            map.clear();
            MapUtils.addNearbyStopMarkers(map, dbHelper, cameraPosition.target);
        }
    }

    @Override
    public boolean onMarkerClick(final Marker currentMarkerClicked) {
        wasMarkerClicked = true;
        stopSelected = currentMarkerClicked.getSnippet();
        stopSelectedName = currentMarkerClicked.getTitle();
        showSnackBar();

        // Query routes associated with stop marker click in background thread
        Runnable task = new Runnable() {
            @Override
            public void run() {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                serviceId = DateTimeUtil.getServiceId();
                routes = dbHelper.getRoutesByStop(stopSelected);
                times = dbHelper.getUpcomingTimes(stopSelected, serviceId, routes);
                Log.i("MyActivity", "Task successfully run in background thread.");
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_FOREGROUND); // return UI thread to main thread
            }
        };

        Thread markerLoaderThread = new Thread(task);
        markerLoaderThread.start();
        return false;
    }

    private void showSnackBar() {
        SnackbarManager.show(
                Snackbar.with(getApplicationContext())
                        .text("Tap for upcoming stop times")
                        .actionLabel("GO")
                        .actionListener(this), this);
    }

    @Override
    public void onActionClicked(Snackbar snackbar) {
        if (routes == null || serviceId == null) {
            serviceId = DateTimeUtil.getServiceId();
            routes = dbHelper.getRoutesByStop(stopSelected);
        }
        showUpcomingStopTimes();
    }

    private void showUpcomingStopTimes() {
        Intent stopInfo = new Intent("tbrown.com.woodbuffalotransitmockup.STOP_INFO");
        stopInfo.putExtra("STOP_NAME", stopSelected + " " + stopSelectedName);
        stopInfo.putExtra("ROUTES", DBUtils.twoDToOneDArray(times, 0)); // array of routes going to the stop
        try {
            stopInfo.putExtra("TIMES", DBUtils.twoDToOneDArray(times, 1));  // array of upcoming stop times for each route
        } catch (ArrayIndexOutOfBoundsException e) {
            stopInfo.putExtra("TIMES", new String[]{""});
        }
        startActivity(stopInfo);
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}

