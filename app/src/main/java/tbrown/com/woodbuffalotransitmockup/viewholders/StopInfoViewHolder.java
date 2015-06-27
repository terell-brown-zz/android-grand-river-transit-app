package tbrown.com.woodbuffalotransitmockup.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tbrown.com.woodbuffalotransitmockup.R;

/**
 * Viewholder for upcoming bus stop times
 */
public class StopInfoViewHolder extends RecyclerView.ViewHolder {

    // UI
    private TextView tvRouteInfo;
    private TextView tvUpcomingTimes;

    public StopInfoViewHolder(View row) {
        // When created the views found in the xml layout of the row provided
        //   are connected to an appropriate object for later use
        super(row);
        row.setClickable(true);
        this.tvRouteInfo = (TextView) row.findViewById(R.id.tvRouteInfo);
        this.tvUpcomingTimes = (TextView) row.findViewById(R.id.tvUpcomingStopTimes);
    }

    public void bindModel(String routeInfo, String upcomingTimes) {
        // Binds the data provided which was extracted from the database to
        //   to the appropriate view
        tvRouteInfo.setText(routeInfo);
        tvUpcomingTimes.setText(upcomingTimes);
    }
}
