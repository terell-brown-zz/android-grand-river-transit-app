package tbrown.com.woodbuffalotransitmockup.util;

import android.database.Cursor;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import tbrown.com.woodbuffalotransitmockup.database.DBHelper;

/**
 * Provides methods to be used the maps in app
 */
public class MapUtils {

    public static void addNearbyStopMarkers(GoogleMap map, DBHelper dbHelper, LatLng location) {
        // adds markers to the map representing the 8 nearest bus stops to
        // the current camera position

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

    public static void addStopMarkersByRoute(DBHelper dbHelper, String stopIds[], GoogleMap map) {
        // Adds markers to the map corresponding to the location of the stops
        Cursor stops = dbHelper.queryStopInfoForMap(stopIds);
        int noStops = stops.getCount();
        Double stopLatitude;
        Double stopLongitude;

        stops.moveToPosition(0);
        for (int i = 0; i < noStops; i++) { // TODO: implement more efficient iteration
            stopLatitude = Double.parseDouble(stops.getString(2));
            stopLongitude = Double.parseDouble(stops.getString(3));
            if (i == noStops / 2) {
                map.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(new LatLng(stopLatitude, stopLongitude), 13) // (..,camera zoom)
                );
            }
            addNewMarker(map,
                    stops.getString(1), // stop name
                    stops.getString(0), // stop description
                    stopLatitude,
                    stopLongitude
            );
            stops.moveToNext();
        }
    }

    public static void addNewMarker(GoogleMap map, String name, String descr, Double Lat, Double Lng) {
        LatLng location = new LatLng(Lat, Lng);
        map.addMarker(new MarkerOptions()
                .title(name) // stop name
                .snippet(descr) // stop number
                .position(location) // stop co-ordinates
        );
    }
}
