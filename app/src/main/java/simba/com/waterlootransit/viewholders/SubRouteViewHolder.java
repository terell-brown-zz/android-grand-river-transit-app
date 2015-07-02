package simba.com.waterlootransit.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import simba.com.waterlootransit.database.DBHelper;
import simba.com.waterlootransit.util.FavouritesUtil;

public class SubRouteViewHolder extends RouteViewHolder {

    private boolean isSubRoute = true;

    public SubRouteViewHolder(Context activityContext, boolean isFavourited, View view) {
        super(activityContext, isFavourited, view);
    }

    @Override
    public void onClick(View v) {
        noDirections = DBHelper.getInstance(activityContext).getNumDirectionsByTripName(mRouteInfo);
        showRouteDetails(isSubRoute);
    }

    @Override
    public boolean onLongClick(View v) {
        setupFavourites();
        if (isFavourited) {
            FavouritesUtil.removeSubRouteFromFavourite(favourites, mRouteInfo);
            Toast.makeText(activityContext, "Route removed from favourites", Toast.LENGTH_LONG).show();
        } else {
            FavouritesUtil.addSubRouteToFavourite(favourites, mRouteInfo);
            Toast.makeText(activityContext, "Route added to favourites", Toast.LENGTH_LONG).show();
        }
        return true;


    }
}
