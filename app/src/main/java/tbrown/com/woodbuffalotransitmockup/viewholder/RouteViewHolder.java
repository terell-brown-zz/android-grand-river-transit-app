package tbrown.com.woodbuffalotransitmockup.viewholder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.Set;

import tbrown.com.woodbuffalotransitmockup.R;



/**
 * Created by tmast_000 on 4/11/2015.
 */
public class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

    TextView tvRouteName;
    TextView tvRouteNo;
    ImageView faveIcon;
    String mRouteInfo;
    String routeNo;
    Context activityContext;
    Log mLog;

    boolean isFavourited;

    SharedPreferences favourites;
    String sharedPrefs = "My Favourite Stops and Routes";
    String routePrefix = "Route -";

    public RouteViewHolder(Context context, View row) {
        super(row);
        isFavourited = false;
        activityContext = context;
        tvRouteName = (TextView) row.findViewById(R.id.tvRouteName);
        tvRouteNo = (TextView) row.findViewById(R.id.tvRouteNo);
        faveIcon = (ImageView) row.findViewById(R.id.ic_favourite_selected);
        row.setOnClickListener(this);
        row.setOnLongClickListener(this);
    }

    public void bindModel(String routeInfo) {
        mRouteInfo = routeInfo;

        // index separating route number and route name in routeInfo
        int indexOfSeparation = getSeparatingIndex(routeInfo);
        routeNo = routeInfo.substring(0,indexOfSeparation);

        if (routeNo.equals("0")) {
            tvRouteNo.setText("");
        } else {
            tvRouteNo.setText(routeNo);
        }

        String routeName = routeInfo.substring(indexOfSeparation + 1);
        tvRouteName.setText(routeName);
    }

    private int getSeparatingIndex(String routeInfo) {
        // This method returns the index of the space character (" ") which
        //    separates the the Route Number and Route Name in the string provided.
        //    The string comes from the database.

        return routeInfo.indexOf(" ");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                showRouteDetails();
        }

    }

    public void showRouteDetails() {
        Intent routeDetailsIntent = new Intent("tbrown.com.woodbuffalotransitmockup.ROUTE_DETAILS");
        String ig = mRouteInfo;
        routeDetailsIntent.putExtra("ROUTE_INFO", mRouteInfo);
        routeDetailsIntent.putExtra("ROUTE_NO", Integer.parseInt(routeNo));
        activityContext.startActivity(routeDetailsIntent);
    }

    @Override
    public boolean onLongClick(View v) {

        setupFavourites();
        if (isFavourited) {
            removeRouteFromFavourite(mRouteInfo);
        } else {
            addRouteToFavourites(mRouteInfo);
        }
        toggleFavourites();
        return true;
    }

    private void removeRouteFromFavourite(String mRouteInfo) {
        SharedPreferences.Editor editor = favourites.edit();
        editor.remove(mRouteInfo);
        editor.apply();
        //isFavourited = false;
    }

    private void setupFavourites() {
        favourites = activityContext.getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE);
    }

    private void toggleFavourites() {
        // Toggle the favourites icon (star) located in the tool bar between
        // selected and unselected

        if (isFavourited == true) {
            faveIcon.setVisibility(View.INVISIBLE);
            isFavourited = false;
            Toast toast = Toast.makeText(activityContext, "Route removed from favourites", Toast.LENGTH_LONG);
            toast.show();
            return;
        } else {
            faveIcon.setVisibility(View.VISIBLE);
            isFavourited = true;
            Toast toast = Toast.makeText(activityContext, "Route added to favourites", Toast.LENGTH_LONG);
            toast.show();
        };
    }

    public void addRouteToFavourites(String routeName) {
        // This method adds the given route to favourites which is
        //   stored in a Shared Preferences Object

        if (favourites.contains(routePrefix + routeName)) {
            return;
        } else {
            SharedPreferences.Editor editor = favourites.edit();
            editor.putString(routePrefix + routeName, routeName);
            editor.apply();
        }
        //isFavourited = true;
    }

}
