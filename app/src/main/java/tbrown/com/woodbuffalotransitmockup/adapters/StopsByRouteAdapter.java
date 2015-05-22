package tbrown.com.woodbuffalotransitmockup.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tbrown.com.woodbuffalotransitmockup.R;
import tbrown.com.woodbuffalotransitmockup.viewholder.StopsByRouteViewHolder;
import tbrown.com.woodbuffalotransitmockup.viewholder.StopViewHolder;

/**
 * Created by tmast_000 on 4/19/2015.
 */
public class StopsByRouteAdapter extends RecyclerView.Adapter<StopsByRouteViewHolder> {

    Context activityContext;
    LayoutInflater inflater;
    String[] database;
    String routeInfo;

    public StopsByRouteAdapter(Context context,String routeName, String[] data) {
        activityContext = context;
        routeInfo = routeName;
        inflater = LayoutInflater.from(activityContext);
        this.database = data;
    }

    @Override
    public StopsByRouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Creates a new list item and ViewHolder when requested by the RecyclerView parent.
        //   ViewType is determined by getItemViewType() and is based on whether
        //   a given data item is title info, route info or stop info
        View view = inflater.inflate(R.layout.stop_view, parent, false);
        StopsByRouteViewHolder holder = new StopsByRouteViewHolder(activityContext, routeInfo, view);
        return holder;
    }

    @Override
    public void onBindViewHolder(StopsByRouteViewHolder holder, int position) {
        // Once the recycler view parent contains a child view and the
        //   associated ViewHolder is created, this method calls on the ViewHolder
        //   bindModel method to pass the data to the appropriate view (ie. textView, etc.)
        ((StopsByRouteViewHolder) holder).bindModel(routeInfo,database[position]);
    }

    @Override
    public int getItemCount() {
        return database.length;
    }

}



