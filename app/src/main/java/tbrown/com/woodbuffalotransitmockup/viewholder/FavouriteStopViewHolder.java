package tbrown.com.woodbuffalotransitmockup.viewholder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tbrown.com.woodbuffalotransitmockup.R;

/**
 * Created by tmast_000 on 4/25/2015.
 */
public class FavouriteStopViewHolder {//extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
   /* TextView tvStopName;
    Context activityContext;
    ImageView faveIcon;

    boolean isFavourite;

    SharedPreferences favourites;
    String sharedPrefs = "My Favourite Stops and Routes";
    String routePrefix = "Stop -";

    public FavouriteStopViewHolder(View row) {
        super(row);
        //context = c;
        isFavourite = false;
        tvStopName = (TextView) row.findViewById(R.id.tvStopName);
        faveIcon = (ImageView) row.findViewById(R.id.ic_favourite_selected);
        row.setClickable(true);
        row.setOnClickListener(this);
        row.setOnLongClickListener(this);
    }

    public void bindModel(String routeName) {
        tvStopName.setText(routeName);
    }

    @Override
    public void onClick(View v) {
        showStopDetails();
    }


    public void showStopDetails() {
        Intent stopDetailsIntent = new Intent("tbrown.com.woodbuffalotransitmockup.STOP_DETAILS");
        stopDetailsIntent.putExtra("ROUTE_INFO",routeInfo);
        stopDetailsIntent.putExtra("STOP_INFO",stopInfo);
        activityContext.startActivity(stopDetailsIntent);
    }
    @Override
    public boolean onLongClick(View v) {
        //toggleFavourites();
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

    private void removeRouteFromFavourite(String mRouteInfo) {
        SharedPreferences.Editor editor = favourites.edit();
        editor.remove(mRouteInfo);
        editor.apply();

    }*/


}
