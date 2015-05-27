package tbrown.com.woodbuffalotransitmockup.viewholder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import tbrown.com.woodbuffalotransitmockup.R;

/**
 * Created by tmast_000 on 4/19/2015.
 */
public class StopsByRouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
    TextView tvStopName;
    Context activityContext;
    ImageView faveIcon;
    String routeInfo;
    String stopInfo;
    int routeNo;
    int stopId;
    boolean isFavourite;

    private static final String WEEKDAYS_ALL = "'15SPRI-All-Weekday-01'";
    private static final String SATURDAY = "'15SPRI-All-Saturday-01'";
    private static final String SUNDAY = "'15SPRI-All-Sunday-01'";

    SharedPreferences favourites;
    String sharedPrefs = "My Favourite Stops and Routes";
    String stopPrefix = "Stop -";


    public StopsByRouteViewHolder(Context context,String routeName, View row) {
        super(row);
        row.setClickable(true);
        activityContext = context;
        tvStopName = (TextView) row.findViewById(R.id.tvStopName);
        faveIcon = (ImageView) row.findViewById(R.id.ic_favourite_selected);
        row.setOnClickListener(this);
        row.setOnLongClickListener(this);
        isFavourite = false;
    }

    public void bindModel(String routeName, int routeNo, String stopName) {
        this.routeInfo = routeName;
        this.routeNo = routeNo;
        this.stopInfo = stopName;

        // index separating route number and route name in routeInfo
        int indexOfSeparation = getSeparatingIndex(stopInfo);
        stopId = Integer.parseInt(stopInfo.substring(0,indexOfSeparation));
        tvStopName.setText(stopName);
    }

    private int getSeparatingIndex(String routeInfo) {
        // This method returns the index of the space character (" ") which
        //    separates the the Route Number and Route Name in the string provided.
        //    The string comes from the database.

        return routeInfo.indexOf(" ");
    }

    @Override
    public void onClick(View v) {
       showStopDetails();
    }


    public void showStopDetails() {
        Intent stopDetailsIntent = new Intent("tbrown.com.woodbuffalotransitmockup.STOP_TIMES");
        stopDetailsIntent.putExtra("ROUTE_INFO",routeInfo);
        stopDetailsIntent.putExtra("ROUTE_NO",routeNo);
        stopDetailsIntent.putExtra("STOP_INFO",stopInfo);
        stopDetailsIntent.putExtra("STOP_ID",stopId);
        stopDetailsIntent.putExtra("SERVICE_ID",WEEKDAYS_ALL);
        stopDetailsIntent.putExtra("DIRECTION_ID",1);
        stopDetailsIntent.putExtra("SPINNER_SELECTION",0);
        //stopDetailsIntent.putExtra("ROUTE_IDS",activityContext)
        activityContext.startActivity(stopDetailsIntent);
    }
    @Override
    public boolean onLongClick(View v) {
        toggleFavourites();
        setupFavourites();
        addStopToFavourites(stopInfo,routeInfo);
        return true;
    }

    private void setupFavourites() {
        favourites = activityContext.getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE);
    }

    private void toggleFavourites() {
        // Toggle the favourites icon (star) located in the tool bar between
        // selected and unselected

        if (isFavourite == true) {
            faveIcon.setVisibility(View.INVISIBLE);
            isFavourite = false;
            Toast toast = Toast.makeText(activityContext, "Stop removed from favourites", Toast.LENGTH_LONG);
            toast.show();
            return;
        } else {
            faveIcon.setVisibility(View.VISIBLE);
            isFavourite = true;
            Toast toast = Toast.makeText(activityContext, "Stop added to favourites", Toast.LENGTH_LONG);
            toast.show();
        };
    }

    public void addStopToFavourites(String stopName,String routeName) {
        // This method adds the given stop along with an associated route
        //    to favourites which is stored in a Shared Preferences Object
        SharedPreferences.Editor editor = favourites.edit();
        if (favourites.contains(stopPrefix + stopName)) {

            try {
                Set<String> set = new HashSet<String>();
                favourites.getStringSet(stopPrefix + stopName, set);
            } catch (ClassCastException e) {
                editor.remove(stopPrefix + stopName);
                Set<String> faveStop = addNewRouteToFavouriteStop(stopName, routeName);
                editor.putStringSet(stopPrefix + stopName, faveStop);
                editor.apply();
            }
        } else {
            Set<String> faveStop = addNewRouteToFavouriteStop(stopName, routeName);
            editor.putStringSet(stopPrefix + stopName,faveStop);
            editor.apply();
        }
    }

    private Set<String> addNewRouteToFavouriteStop(String stopName,String routeName){
        HashSet<String> faveStop = new HashSet<String>();
        faveStop.add(stopName);
        faveStop.add(routeName);
        return faveStop;
    }
}
