package tbrown.com.woodbuffalotransitmockup;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.*;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import tbrown.com.woodbuffalotransitmockup.activities.fragments.StopInfoFragment;
import tbrown.com.woodbuffalotransitmockup.database.DBHelper;
import tbrown.com.woodbuffalotransitmockup.util.DateTimeUtil;
import tbrown.com.woodbuffalotransitmockup.util.MapUtils;

/**
 * Created by tmast_000 on 4/4/2015.
 */

public class NearbyTab extends Fragment implements GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraChangeListener {

    // Fragment that is a part of a Sliding Tab Activity.
    // Fragment contains a map which shows the closest bus stops according
    //   to the users current location. Closest stops are updated as the
    //   camera moves around.

    private Context activityContext;
    private DBHelper dbHelper;
    private FragmentManager fm;
    private StopInfoFragment dialog;


    GoogleMap map;
    LatLng lastKnownLocation;
    private Marker lastMarkerClicked;
    private boolean doubleclick = false;
    boolean wasMarkerClicked = true;

    private String[] routes;
    private String[][] times;
    private String serviceId;
    private String stopSelected;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // called when creating the fragment
        super.onCreate(savedInstanceState);
        activityContext = getActivity();
        setupDatabase(activityContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // called when the fragment is created and it is now time to display the
        //   UI of the fragment for the first time
        View v = inflater.inflate(R.layout.tab_nearby,container,false);
        addMap();
        setupMap();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        activityContext = getActivity();

        if (dbHelper == null) {
            setupDatabase(activityContext);
        }
    }

    private void setupDatabase(Context activityContext) {
        // initializes a DBHelper object which provides access to methods
        //   used to query the database
        dbHelper = new DBHelper(activityContext);
    }


    private void addMap() {
        fm = getChildFragmentManager(); // used to interact with multiple child fragments within parent fragment
        map = ((SupportMapFragment) fm.findFragmentById(R.id.map_nearby_stops)).getMap();
    }

    private void setupMap() {
        map.setMyLocationEnabled(true);
        map.setOnMarkerClickListener(this);
        map.setOnCameraChangeListener(this);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.467, -80.517), 16));
        lastKnownLocation = map.getCameraPosition().target;
        MapUtils.addMarkers(map, dbHelper, lastKnownLocation); // adds markers to representing closest stops to location provided
    }

    public void onCameraChange(CameraPosition cameraPosition) {
        if (wasMarkerClicked) {
            // if camera change caused by marker click, do nothing and set the value back to false
            wasMarkerClicked = false;
        } else {
            // else clear markers on map and add new markers according to new camera position
            LatLng newLocation = cameraPosition.target;
            map.clear();
            MapUtils.addMarkers(map, dbHelper,newLocation);
        }
    }

    @Override
    public boolean onMarkerClick(final Marker currentMarkerClicked) {
        wasMarkerClicked = true;
        if (doubleclick && currentMarkerClicked.equals(lastMarkerClicked)) {
            // Inflate a dialogue fragment displaying the routes that go to
            //   that stop as well as the four next bus stops
            //serviceId = DateTimeUtil.getServiceId();
            //routes = dbHelper.getRoutesByStop(stopSelected,serviceId);

            wasMarkerClicked = false;

            if (routes == null || serviceId == null ) {
                serviceId = DateTimeUtil.getServiceId();
                routes = dbHelper.getRoutesByStop(stopSelected);
            }
            //if (routes == null) {
                //Toast.makeText(activityContext,)
            //}
            times = dbHelper.getUpcomingTimes(stopSelected,serviceId,routes);
            Intent stopInfo = new Intent("tbrown.com.woodbuffalotransitmockup.STOP_INFO");
            //Bundle b=new Bundle();
            stopInfo.putExtra("ROUTES", convert(times, 0));
            stopInfo.putExtra("TIMES", convert(times, 1));
            //stopInfo.putExtras(b);
            startActivity(stopInfo);
            //showDialog();
        } else {
            stopSelected = currentMarkerClicked.getSnippet();
            // Run query and setup fragment for the chance the same marker
            //   is clicked again. This can be done in a different thread

            Runnable task = new Runnable() {
                @Override
                public void run() {
                // run query
                    android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                    serviceId = DateTimeUtil.getServiceId();
                    routes = dbHelper.getRoutesByStop(stopSelected);
                    Log.i("MyActivity", "Task successfully run in background thread.");
                }

            };
            Thread markerLoaderThread = new Thread(task);
            markerLoaderThread.start();
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_FOREGROUND);
            doubleclick = !doubleclick;
            lastMarkerClicked = currentMarkerClicked;
        }
        return false;
    }

    public String[] convert(String[][] yo,int col) {
        String[] result = new String[yo.length];
        int i = 0;
        for (String[] row: yo) {
            result[i] = row[col];
            i++;
        }
        return result;
    }
}
