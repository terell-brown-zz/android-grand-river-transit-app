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
import tbrown.com.woodbuffalotransitmockup.database.DBHelper;
import tbrown.com.woodbuffalotransitmockup.database.DBUtils;
import tbrown.com.woodbuffalotransitmockup.util.DateTimeUtil;
import tbrown.com.woodbuffalotransitmockup.util.FavouritesUtil;

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

    int viewContext; // Context for which the list is being used
    int NORMAL = 0; // the list is showing info normally
    int FAVOURITES = 1; // the list is showing favourites data

    private static final String WEEKDAYS_ALL = "'15SPRI-All-Weekday-01'";
    private static final String SATURDAY = "'15SPRI-All-Saturday-01'";
    private static final String SUNDAY = "'15SPRI-All-Sunday-01'";

    SharedPreferences favourites;
    String sharedPrefs = "My Favourite Stops and Routes";
    String stopPrefix = "Stop -";


    public StopsByRouteViewHolder(Context context,int viewContext, String routeName, View row) {
        super(row);
        row.setClickable(true);
        this.activityContext = context;
        this.viewContext = viewContext;
        this.tvStopName = (TextView) row.findViewById(R.id.tvStopName);
        this.faveIcon = (ImageView) row.findViewById(R.id.ic_favourite_selected);
        row.setOnClickListener(this);
        row.setOnLongClickListener(this);
        this.isFavourite = false;
    }

    public void bindModel(String routeName,int routeNo, String stopName) {
        this.routeInfo = routeName;
        this.routeNo = routeNo;
        this.stopInfo = stopName;

        // index separating route number and route name in routeInfo
        int indexOfSeparation = getSeparatingIndex(stopInfo); // add to utilities class
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
        if (viewContext == 0) {
            // if the context of the list is normal show times associated with this stop
            showStopDetails();
        } else {
            // if the context of the list is showing favourite stops show an
            //   activity containing the upcoming times for that stop
            showUpcomingStopTimes();
        }
    }

    public void showStopDetails() {
        Intent stopDetailsIntent = new Intent("tbrown.com.woodbuffalotransitmockup.STOP_TIMES");
        stopDetailsIntent.putExtra("ROUTE_INFO",routeInfo);
        stopDetailsIntent.putExtra("ROUTE_NO",routeNo);
        stopDetailsIntent.putExtra("STOP_INFO",stopInfo);
        stopDetailsIntent.putExtra("STOP_ID",stopId);
        stopDetailsIntent.putExtra("SERVICE_ID",WEEKDAYS_ALL);
        stopDetailsIntent.putExtra("DIRECTION_ID", 1);
        stopDetailsIntent.putExtra("SPINNER_SELECTION", 0);
        activityContext.startActivity(stopDetailsIntent);
    }

    private void showUpcomingStopTimes() {
        // initializes a DBHelper object which provides access to methods
        //   used to query the database
        DBHelper dbHelper= new DBHelper(activityContext);

        String stopNo = "" + stopId;
        String[][] times = dbHelper.getUpcomingTimes(
                stopNo,
                DateTimeUtil.getServiceId(),        // current time of service (ie. Weekday, Saturday, Sunday)
                dbHelper.getRoutesByStop(stopNo)    // list of routes associated with stop
        );

        Intent intent = new Intent("tbrown.com.woodbuffalotransitmockup.STOP_INFO");

        intent.putExtra("STOP_NAME", stopInfo);
        intent.putExtra("ROUTES", DBUtils.twoDToOneDArray(times, 0));
        intent.putExtra("TIMES", DBUtils.twoDToOneDArray(times, 1));

        activityContext.startActivity(intent);

    }
    @Override
    public boolean onLongClick(View v) {
        setupFavourites();
        toggleFavourites();
        if (viewContext == NORMAL) {
            FavouritesUtil.addStopToFavourites(favourites, stopInfo);
        } else {
            // ie. viewContext == FAVOURITES
            FavouritesUtil.removeStopFromFavourites(favourites,stopInfo);
        }

        return true;
    }

    private void setupFavourites() {
        if (favourites == null) {
            favourites = activityContext.getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE);
        }
    }

    private void toggleFavourites() {
        // Toggle the favourites icon (star) located in the tool bar between
        // selected and unselected

        if (viewContext == FAVOURITES) {
            faveIcon.setVisibility(View.INVISIBLE);
            //isFavourite = false;
            Toast toast = Toast.makeText(activityContext, "Stop removed from favourites", Toast.LENGTH_LONG);
            toast.show();
            return;
        } else {
            faveIcon.setVisibility(View.VISIBLE);
            //isFavourite = true;
            Toast toast = Toast.makeText(activityContext, "Stop added to favourites", Toast.LENGTH_LONG);
            toast.show();
        };
    }

}
