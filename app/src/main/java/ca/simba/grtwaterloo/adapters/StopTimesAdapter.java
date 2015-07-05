package ca.simba.grtwaterloo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.simba.grtwaterloo.R;
import ca.simba.grtwaterloo.viewholders.StopTimesViewHolder;

/**
 * Created by tmast_000 on 4/21/2015.
 */
public class StopTimesAdapter extends RecyclerView.Adapter<StopTimesViewHolder> {


    // Backend Components
    //private Context activityContext;
    private String[] times;
    // UI
    private LayoutInflater inflater;

    // Business Logic
    private String routeInfo;

    public StopTimesAdapter(Context context,String routeName, String[] data) {
        //activityContext = context;
        inflater = LayoutInflater.from(context);
        this.routeInfo = routeName;
        this.times = data;
    }

    @Override
    public StopTimesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Creates a new list item and ViewHolder when requested by the RecyclerView parent.
        //   ViewType is determined by getItemViewType() and is based on whether
        //   a given data item is title info, route info or stop info
        View row = inflater.inflate(R.layout.stoptimes_view, parent, false);
        StopTimesViewHolder holder = new StopTimesViewHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(StopTimesViewHolder holder, int position) {
        // Once the recycler view parent contains a child view and the
        //   associated ViewHolder is created, this method calls on the ViewHolder
        //   bindModel method to pass the data to the appropriate view (ie. textView, etc.)
        ((StopTimesViewHolder) holder).bindModel(times[position]);
    }

    @Override
    public int getItemCount() {
        return times.length;
    }
}



