package tbrown.com.woodbuffalotransitmockup.viewholder;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import tbrown.com.woodbuffalotransitmockup.R;

/**
 * Created by tmast_000 on 5/29/2015.
 */
public class StopInfoViewHolder extends RecyclerView.ViewHolder {
    // StopInfoViewHolder provides easy access to the views in each row
    //   of the list making it easier to bind the appropriate data to it

    TextView tvRouteInfo;
    TextView tvUpcomingTimes;

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
