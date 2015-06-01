package tbrown.com.woodbuffalotransitmockup.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import tbrown.com.woodbuffalotransitmockup.R;

/**
 * Created by tmast_000 on 4/21/2015.
 */
public class StopTimesViewHolder extends RecyclerView.ViewHolder {
    TextView tvStopTime;
    Context activityContext;
    String routeInfo;
    String stopInfo;


    public StopTimesViewHolder(Context context,String routeName, View row) {
        super(row);
        //row.setClickable(true);
        activityContext = context;
        tvStopTime = (TextView) row.findViewById(R.id.tvStopTimes);
    }

    public void bindModel(String routeName, String stopTimes) {
        routeInfo = routeName;
        stopInfo = stopTimes;
        tvStopTime.setText(stopTimes);
    }
}
