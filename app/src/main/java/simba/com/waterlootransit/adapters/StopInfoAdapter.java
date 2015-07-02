package simba.com.waterlootransit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import simba.com.waterlootransit.R;
import simba.com.waterlootransit.viewholders.StopInfoViewHolder;

/**
 *
 */
public class StopInfoAdapter extends RecyclerView.Adapter<StopInfoViewHolder> {

    // Backend Components
    //private Context activityContext;

    // UI
    private LayoutInflater inflater;

    // Business Logic
    private final String[] routes;
    private final String[] times;

    public StopInfoAdapter(Context activityContext, String[] routes, String[] times) {
        //this.activityContext = activityContext;
        inflater = LayoutInflater.from(activityContext);
        this.routes = routes;
        this.times = times;
    }

    @Override
    public StopInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Creates a new list item in the parent view which is the recyclerView by instatiating the ViewHolder class
        //   ViewType is determined by getItemViewType() and is based on whether
        //   a given data item is title info, route info or stop info
        View row = inflater.inflate(R.layout.stop_info_view, parent, false);
        StopInfoViewHolder holder = new StopInfoViewHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(StopInfoViewHolder holder, int position) {
        holder.bindModel(routes[position], times[position]); // (route info, times)
    }

    @Override
    public int getItemCount() {
        return routes.length;
    }
}
