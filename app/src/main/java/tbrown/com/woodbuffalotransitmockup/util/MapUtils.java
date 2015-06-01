package tbrown.com.woodbuffalotransitmockup.util;

import android.database.Cursor;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import tbrown.com.woodbuffalotransitmockup.database.DBHelper;

/**
 * Created by tmast_000 on 5/30/2015.
 */
public class MapUtils {
    // Utility class providing methods to be used with maps

    public static void addMarkers(GoogleMap map, DBHelper dbHelper,LatLng location) {
        // This method adds markers to the map representing the 8 nearest bus stops to
        //   the current camera location

        Cursor stops = dbHelper.queryNearbyStops(location.latitude, location.longitude);
        int noStops = stops.getCount();

        stops.moveToFirst();
        for (int i = 0; i < noStops; i++) {
            addNewMarker(map,           // GoogleMaps object
                    stops.getString(1), // name of stop
                    stops.getString(0), // stop number
                    Double.parseDouble(stops.getString(2)), // latitude
                    Double.parseDouble(stops.getString(3)) // longitude
            );
            stops.moveToNext();
        }
    }

    public static void addNewMarker(GoogleMap map,String name,String descr,Double Lat, Double Lng) {
        // Adds a marker to the current map based on the parameters given

        LatLng location = new LatLng(Lat,Lng);

        map.addMarker(new MarkerOptions()
                .title(name) // stop name
                .snippet(descr) // stop number
                .position(location) // stop co-ordinates
        );
    }
}
