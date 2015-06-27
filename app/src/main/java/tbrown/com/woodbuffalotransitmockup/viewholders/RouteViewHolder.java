package tbrown.com.woodbuffalotransitmockup.viewholders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tbrown.com.woodbuffalotransitmockup.Favourites;
import tbrown.com.woodbuffalotransitmockup.R;
import tbrown.com.woodbuffalotransitmockup.util.FavouritesUtil;
import tbrown.com.woodbuffalotransitmockup.util.Utilities;


/**
 * Holds
 */
public class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

    // Backend Components
    private Context activityContext;

    // UI
    private TextView tvRouteName;
    private TextView tvRouteNo;

    // Transit Info
    private String mRouteInfo;
    private String routeNo;
    private String routeName;

    // Business Logic
    private boolean isFavourited;

    private SharedPreferences favourites;

    public RouteViewHolder(Context context,boolean showAsFavourite, View row) {
        super(row);
        activityContext = context;
        this.isFavourited = showAsFavourite;

        setupViews(row);
    }

    private void setupViews(View row) {
        tvRouteName = (TextView) row.findViewById(R.id.tvRouteName);
        tvRouteNo = (TextView) row.findViewById(R.id.tvRouteNo);
        row.setOnClickListener(this);
        row.setOnLongClickListener(this);
    }

    public void bindModel(String routeInfo) {
        // writes retrieved data to UI views
        mRouteInfo = routeInfo;

        routeNo = extractRouteNo(mRouteInfo);
        routeName = extractRouteName(mRouteInfo);
        tvRouteNo.setText(routeNo);
        tvRouteName.setText(routeName);
    }

    private String extractRouteNo(String routeInfo) {
        // index separating route number and route name in routeInfo
        int indexOfSeparation = Utilities.getSeparatingIndex(routeInfo);
        String routeNo = routeInfo.substring(0, indexOfSeparation);
        if (routeNo.equals("0")) {
            return "";
        } else {
            return routeNo;
        }
    }

    private String extractRouteName(String routeInfo) {
        // index separating route number and route name in routeInfo
        int indexOfSeparation = Utilities.getSeparatingIndex(routeInfo);
        return routeInfo.substring(indexOfSeparation);
    }

    @Override
    public void onClick(View v) {
        showRouteDetails();
    }

    public void showRouteDetails() {
        Intent routeDetailsIntent = new Intent("tbrown.com.woodbuffalotransitmockup.ROUTE_DETAILS");
        routeDetailsIntent.putExtra("ROUTE_INFO", mRouteInfo);
        routeDetailsIntent.putExtra("ROUTE_NO", Integer.parseInt(routeNo));
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

    private void setupFavourites() {
        // TODO: move this method to the FavouritesUtil Class and use Singleton Design Pattern
        favourites = Favourites.getInstance(activityContext);
    }

}
