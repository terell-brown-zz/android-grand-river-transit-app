package tbrown.com.woodbuffalotransitmockup.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tbrown.com.woodbuffalotransitmockup.R;
import tbrown.com.woodbuffalotransitmockup.viewholders.StopsByRouteViewHolder;


/**
 * Created by tmast_000 on 4/19/2015.
 */
public class StopsByRouteAdapter extends RecyclerView.Adapter<StopsByRouteViewHolder> {

    // Backend Components
    private Context activityContext;

    // UI
    private LayoutInflater inflater;

    // Business Logic
    private String[] database;
    private String routeInfo;
    private String routeNo;
    private boolean isFavourited = false;
    private boolean isSubRoute;

    public StopsByRouteAdapter(Context context,String routeName,String routeId, String[] data, boolean isSubroute) {
        activityContext = context;
        inflater = LayoutInflater.from(activityContext);
        routeInfo = routeName;
        routeNo = routeId;
        database = data;
        isSubRoute = isSubroute;
    }

    @Override
    public StopsByRouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Creates a new list item and ViewHolder when requested by the RecyclerView parent.
        //   ViewType is determined by getItemViewType() and is based on whether
        //   a given data item is title info, route info or stop info
        View view = inflater.inflate(R.layout.stop_view, parent, false);
        StopsByRouteViewHolder holder = new StopsByRouteViewHolder(activityContext,isFavourited, isSubRoute, view);
        return holder;
    }

    @Override
    public void onBindViewHolder(StopsByRouteViewHolder holder, int position) {
        // Once the recycler view parent contains a child view and the
        //   associated ViewHolder is created, this method calls on the ViewHolder
        //   bindModel method to pass the data to the appropriate view (ie. textView, etc.)
        ((StopsByRouteViewHolder) holder).bindModel(routeInfo,routeNo, database[position]);
    }

    @Override
    public int getItemCount() {
        return database.length;
    }

}



