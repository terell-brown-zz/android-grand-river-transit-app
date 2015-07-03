package simba.com.waterlootransit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import simba.com.waterlootransit.R;
import simba.com.waterlootransit.viewholders.RouteViewHolder;

/**
 * Created by tmast_000 on 4/5/2015.
 */
public class AllRoutesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Backend Components
    private Context activityContext;

    //UI
    private LayoutInflater inflater;

    // Transit Info
    private String[] routes;

    // Business Logic
    private boolean isFavourited = false;
    private boolean isSubroute = false;

    public AllRoutesAdapter(Context context, String[] data) {
        activityContext = context;
        inflater = LayoutInflater.from(activityContext);
        routes = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // creates a row in the list that can hold data
        View row = inflater.inflate(R.layout.route_view, parent, false);
        RouteViewHolder holder = new RouteViewHolder(activityContext, isFavourited, row);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // supplies a single piece of data to the row to be displayed
        ((RouteViewHolder) holder).bindModel(routes[position], isSubroute);
    }

    @Override
    public int getItemCount() {
        return routes.length;
    }
}
