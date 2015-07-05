package ca.simba.grtwaterloo.activities;

import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

import ca.simba.grtwaterloo.Constants;
import ca.simba.grtwaterloo.R;
import ca.simba.grtwaterloo.util.DBUtils;
import ca.simba.grtwaterloo.util.DateTimeUtil;
import ca.simba.grtwaterloo.util.MapUtils;

/*
  Displays map showing bus stops that are close to your current location
 */

public class NearbyStopsActivity extends BaseActivity implements GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraChangeListener, ActionClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // UI
    private FragmentManager fm;

    // Map Fields
    private GoogleMap map;
    private GoogleApiClient mGoogleApiClient;
    private LatLng lastCameraLocation;
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
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private static final double WATERLOO_LAT = Constants.WATERLOO_LAT;
    private static final double WATERLOO_LONG = Constants.WATERLOO_LONG;
    private static final double DISTANCE_CUTTOFF = Constants.DISTANCE_CUTTOFF;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        // Setup Backend Components
        activityContext = getBaseContext();
        setupDatabase(activityContext);

        // Setup UI
        setupToolbar(TOOLBAR_TITLE);
        setupNavDrawer(NAV_ID);
        setupMap();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (dbHelper == null) {
            setupDatabase(activityContext);
        }

        if (map == null) {
            setupMap();
        }
        setupGooglePlayServices();
    }

    private void setupMap() {
        fm = getSupportFragmentManager();
        map = ((SupportMapFragment) fm.findFragmentById(R.id.map_nearby_stops)).getMap(); // a google map fragment

        map.setMyLocationEnabled(true);
        map.setOnMarkerClickListener(this);
        map.setOnCameraChangeListener(this);
    }

    private void setupGooglePlayServices() {
        if(checkPlayServices()) {
            buildGoogleApiClient();
            mGoogleApiClient.connect();
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void getCurrentLocation() {

        currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        float distance = 0;

        if (currentLocation != null) {
            currentLat = currentLocation.getLatitude();
            currentLong = currentLocation.getLongitude();

            float[] result = new float[3];

            // Get Distance From Current Location and Waterloo - stored in result[0]
            Location.distanceBetween(currentLat, WATERLOO_LAT, currentLong, WATERLOO_LONG, result);
            distance = result[0]; // in meters
        }

        if (currentLocation == null || distance > DISTANCE_CUTTOFF) {
            currentLat = WATERLOO_LAT;
            currentLong = WATERLOO_LONG;
            cameraZoom = 11;
            Toast.makeText(activityContext,"There are no nearby GRT stops.",Toast.LENGTH_SHORT).show();
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
                        .text("Tap GO for upcoming stop times")
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
        Intent stopInfo = new Intent("ca.simba.grtwaterloo.activities.STOP_INFO");
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
        getCurrentLocation();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat, currentLong), cameraZoom));
        lastCameraLocation = map.getCameraPosition().target;
        MapUtils.addNearbyStopMarkers(map, dbHelper, lastCameraLocation);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("Location Servicces", "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }
}

