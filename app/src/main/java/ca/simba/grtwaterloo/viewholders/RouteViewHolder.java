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
import ca.simba.grtwaterloo.util.FavouritesUtil;
import ca.simba.grtwaterloo.util.Utilities;

public class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

    // Backend Components
    protected Context activityContext;
    protected SharedPreferences favourites;

    // UI
    private TextView tvRouteName;
    private TextView tvRouteNo;

    // Business Logic
    protected String mRouteInfo;
    protected String routeNo;
    protected String routeName;
    protected String[] subRoutes;
    protected int noDirections; // how many directions does the bus go in?

    // Business Logic
    protected boolean isFavourited;
    protected boolean isSubRoute = false;

    public RouteViewHolder(Context context, boolean showAsFavourite, View row) {
        super(row);
        activityContext = context;
        isFavourited = showAsFavourite;
        setupViews(row);
    }

    private void setupViews(View row) {
        tvRouteName = (TextView) row.findViewById(R.id.tvRouteName);
        tvRouteNo = (TextView) row.findViewById(R.id.tvRouteNo);
        row.setOnClickListener(this);
        row.setOnLongClickListener(this);
    }

    public void bindModel(String routeInfo, boolean showAsSubRoute) {
        mRouteInfo = routeInfo;
        isSubRoute = showAsSubRoute;
        routeNo = extractRouteNo(mRouteInfo);
        routeName = extractRouteName(mRouteInfo);

        tvRouteNo.setText(routeNo);
        tvRouteName.setText(routeName);
    }

    private String extractRouteNo(String routeInfo) {
        // index separating route number and route name in routeInfo
        int indexOfSeparation = Utilities.getSeparatingIndex(routeInfo);
        String routeNo;
        try {
            routeNo = routeInfo.substring(0, indexOfSeparation);
        } catch (StringIndexOutOfBoundsException e) {
            routeNo = "0";
        }
        if (routeNo.equals("0")) {
            return "";
        } else {
            return routeNo;
        }
    }

    private String extractRouteName(String routeInfo) {
        // index separating route number and route name in routeInfo
        int indexOfSeparation = Utilities.getSeparatingIndex(routeInfo);

        try {
            return routeInfo.substring(indexOfSeparation);
        } catch (StringIndexOutOfBoundsException e) {
            return routeInfo;
        }

    }

    @Override
    public void onClick(View v) {
        DBHelper dbHelper = DBHelper.getInstance(activityContext);
        subRoutes = dbHelper.getSubRoutes(routeNo);
        if (subRoutes.length > 1) {
            showSubRoutes();
        } else {
            noDirections = dbHelper.getNumDirectionsByRouteNo(routeNo);
            showRouteDetails(false);
        }
    }

    private void showSubRoutes() {
        Intent routeDetailsIntent = new Intent(Constants.PACKAGE_NAME + ".activities.SUB_ROUTES");
        routeDetailsIntent.putExtra("ROUTE_NAME", routeName);
        routeDetailsIntent.putExtra("ROUTE_NO", routeNo);
        routeDetailsIntent.putExtra("SUB_ROUTES", subRoutes);
        routeDetailsIntent.putExtra("IS_SUBROUTE", isSubRoute);
        routeDetailsIntent.putExtra("ROUTE_INFO", mRouteInfo);
        routeDetailsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activityContext.startActivity(routeDetailsIntent);
    }

    public void showRouteDetails(boolean isSubRoute) {
        Intent routeDetailsIntent = new Intent(Constants.PACKAGE_NAME + ".activities.ROUTE_DETAILS");
        routeDetailsIntent.putExtra("ROUTE_INFO", mRouteInfo);
        routeDetailsIntent.putExtra("ROUTE_NAME", routeName);
        routeDetailsIntent.putExtra("ROUTE_NO", routeNo);
        routeDetailsIntent.putExtra("NUM_DIRECTIONS", noDirections);
        routeDetailsIntent.putExtra("IS_SUBROUTE", isSubRoute);
        routeDetailsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activityContext.startActivity(routeDetailsIntent);
    }

    @Override
    public boolean onLongClick(View v) {
        setupFavourites();
        if (isFavourited) {
            FavouritesUtil.removeRouteFromFavourite(favourites, mRouteInfo);
            Toast.makeText(activityContext, "Route removed from favourites", Toast.LENGTH_LONG).show();
        } else {
            FavouritesUtil.addRouteToFavourites(favourites, mRouteInfo);
            Toast.makeText(activityContext, "Route added to favourites", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    protected void setupFavourites() {
        favourites = Favourites.getInstance(activityContext);
    }

}
