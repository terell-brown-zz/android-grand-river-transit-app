package tbrown.com.woodbuffalotransitmockup.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import tbrown.com.woodbuffalotransitmockup.database.DBHelper;
import tbrown.com.woodbuffalotransitmockup.util.FavouritesUtil;
import tbrown.com.woodbuffalotransitmockup.viewholders.RouteViewHolder;

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
