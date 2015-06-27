package tbrown.com.woodbuffalotransitmockup.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tbrown.com.woodbuffalotransitmockup.R;

/**
 * Viewholder for displaying bus times
 */
public class StopTimesViewHolder extends RecyclerView.ViewHolder {

    // UI
    private TextView tvStopTime;

    public StopTimesViewHolder(View row) {
        super(row);
        tvStopTime = (TextView) row.findViewById(R.id.tvStopTimes);
    }

    public void bindModel(String stopTimes) {
        tvStopTime.setText(stopTimes);
    }
}
