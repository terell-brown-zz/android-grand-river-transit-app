package tbrown.com.woodbuffalotransitmockup.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.Inflater;

import tbrown.com.woodbuffalotransitmockup.R;
import tbrown.com.woodbuffalotransitmockup.activities.fragments.StopInfoFragment;
import tbrown.com.woodbuffalotransitmockup.viewholder.StopInfoViewHolder;
import tbrown.com.woodbuffalotransitmockup.viewholder.StopTimesViewHolder;

/**
 * Created by tmast_000 on 5/29/2015.
 */
public class StopInfoAdapter extends RecyclerView.Adapter<StopInfoViewHolder> {


    Context activityContext;
    LayoutInflater inflater;
    private final String[] routes;
    private final String[] times;

    public StopInfoAdapter(Context activityContext, String[] routes, String[] times) {
        this.activityContext = activityContext;
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
        // Extracts data from the database according to the position provided.
        //   Passes this data to bindModel() where it is binded to the appropriate
        //   views associated with the row (or ViewHolder)

        //String routeInfo = database[position][0];
        //String upCommingTimes = database[position][1];
        holder.bindModel(routes[position],  // route info
                times[position]);          // upcoming stop times
    }

    @Override
    public int getItemCount() {
        return routes.length;
    }
}
