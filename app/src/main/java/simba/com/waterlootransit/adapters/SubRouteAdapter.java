package simba.com.waterlootransit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import simba.com.waterlootransit.R;
import simba.com.waterlootransit.viewholders.RouteViewHolder;
import simba.com.waterlootransit.viewholders.SubRouteViewHolder;

/**
 * Created by tmast_000 on 6/27/2015.
 */
public class SubRouteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // Backend Components
    private Context activityContext;

    //UI
    private LayoutInflater inflater;

    // Transit Info
    private String[] subRoutes;

    // Business Logic
    private boolean isFavourited = false;
    private boolean isSubroute = false;

    public SubRouteAdapter(Context context, String[] data) {
        this.activityContext = context;
        this.inflater = LayoutInflater.from(activityContext);
        this.subRoutes = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // creates a row in the list that can hold data
        View row = inflater.inflate(R.layout.route_view, parent, false);
        SubRouteViewHolder holder = new SubRouteViewHolder(activityContext, isFavourited, row);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // supplies a single piece of data to the row to be displayed
        ((RouteViewHolder) holder).bindModel(subRoutes[position], isSubroute);
    }

    @Override
    public int getItemCount() {
        return subRoutes.length;
    }
}
