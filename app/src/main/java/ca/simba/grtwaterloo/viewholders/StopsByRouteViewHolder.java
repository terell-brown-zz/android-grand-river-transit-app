package ca.simba.grtwaterloo.viewholders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ca.simba.grtwaterloo.Constants;
import ca.simba.grtwaterloo.Favourites;
import ca.simba.grtwaterloo.R;
import ca.simba.grtwaterloo.database.DBHelper;
import ca.simba.grtwaterloo.util.DBUtils;
import ca.simba.grtwaterloo.util.DateTimeUtil;
import ca.simba.grtwaterloo.util.FavouritesUtil;
import ca.simba.grtwaterloo.util.Utilities;

/**
 * Created by tmast_000 on 4/19/2015.
 */
public class StopsByRouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    // Backend Components
    private Context activityContext;
    private SharedPreferences favourites;

    // UI
    private TextView tvStopName;

    // Business Logic
    private String routeInfo;
    private String stopInfo;
    private String routeNo;
    private int stopId;
    private boolean isFavourited;
    private boolean isSubRoute;

    // Constants
    private static final String WEEKDAYS_ALL = Constants.WEEKDAYS_ALL;

    public StopsByRouteViewHolder(Context context, boolean isFavourited, boolean isSubroute, View row) {
        super(row);
        row.setClickable(true);
        activityContext = context;
        this.isFavourited = isFavourited;
        isSubRoute = isSubroute;
        tvStopName = (TextView) row.findViewById(R.id.tvStopName);
        row.setOnClickListener(this);
        row.setOnLongClickListener(this);
    }

    public void bindModel(String routeName, String routeId, String stopName) {
        routeInfo = routeName;
        routeNo = routeId;
        stopInfo = stopName;

        // index separating route number and route name in routeInfo
        int indexOfSeparation = Utilities.getSeparatingIndex(stopInfo); // add to utilities class
        stopId = Integer.parseInt(stopInfo.substring(0, indexOfSeparation));

        // index separating route number and route name in routeInfo
        //indexOfSeparation = Utilities.getSeparatingIndex(stopName); // add to utilities class
        //routeNo = routeName.substring(0, indexOfSeparation);
        tvStopName.setText(stopName);
    }

    @Override
    public void onClick(View v) {
        if (isFavourited) {
            showUpcomingStopTimes();
        } else {
            showStopDetails();
        }
    }

    public void showStopDetails() {
        String serviceId = DateTimeUtil.getServiceId();
        Intent stopDetailsIntent = new Intent("ca.simba.grtwaterloo.activities.STOP_TIMES");
        stopDetailsIntent.putExtra("ROUTE_INFO", routeInfo);
        stopDetailsIntent.putExtra("ROUTE_NO", routeNo);
        stopDetailsIntent.putExtra("STOP_INFO", stopInfo);
        stopDetailsIntent.putExtra("STOP_ID", stopId);
        stopDetailsIntent.putExtra("SERVICE_ID", serviceId);
        stopDetailsIntent.putExtra("DIRECTION_ID", 1);
        stopDetailsIntent.putExtra("SPINNER_SELECTION",Utilities.getCurrentSpinnerItem(serviceId));
        stopDetailsIntent.putExtra("IS_SUBROUTE",isSubRoute);
        stopDetailsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activityContext.startActivity(stopDetailsIntent);
    }

    private void showUpcomingStopTimes() {
        DBHelper dbHelper = DBHelper.getInstance(activityContext);//new DBHelper(activityContext);

        String stopNo = "" + stopId;
        String[][] times = dbHelper.getUpcomingTimes(
                stopNo,
                DateTimeUtil.getServiceId(),
                dbHelper.getRoutesByStop(stopNo)
        );

        Intent intent = new Intent("ca.simba.grtwaterloo.activities.STOP_INFO");
        intent.putExtra("STOP_NAME", stopInfo);
        intent.putExtra("ROUTES", DBUtils.twoDToOneDArray(times, 0));
        try {
            intent.putExtra("TIMES", DBUtils.twoDToOneDArray(times, 1));
        } catch(ArrayIndexOutOfBoundsException e) {
            intent.putExtra("TIMES", new String[]{""});
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activityContext.startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        setupFavourites();

        if (isFavourited) {
            FavouritesUtil.removeStopFromFavourites(favourites, stopInfo);
            Toast.makeText(activityContext, "Stop removed from favourites", Toast.LENGTH_LONG).show();
        } else {
            FavouritesUtil.addStopToFavourites(favourites, stopInfo);
            Toast.makeText(activityContext, "Stop added to favourites", Toast.LENGTH_LONG);
        }

        return true;
    }

    private void setupFavourites() {
        if (favourites == null) {
            favourites = Favourites.getInstance(activityContext);
        }
    }
}
