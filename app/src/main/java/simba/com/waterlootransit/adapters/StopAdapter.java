package simba.com.waterlootransit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import simba.com.waterlootransit.R;
import simba.com.waterlootransit.viewholders.SubRouteViewHolder;
import simba.com.waterlootransit.viewholders.RouteViewHolder;
import simba.com.waterlootransit.viewholders.SpacerHolder;
import simba.com.waterlootransit.viewholders.StopsByRouteViewHolder;

/**
 * Adapter connecting data to list of stops and routes
 */
public class StopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Backend Components
    Context activityContext;

    // UI
    private LayoutInflater inflater;

    // Business Logic
    private String[] stops;
    private int indexRouteTitle;
    private int indexSubRouteTitle;
    private int indexStopTitle;
    private String itemType;
    private boolean isFavourited;
    private boolean isSubroute = false;


    public StopAdapter(Context context, String[] data,boolean isFavourited) {
        activityContext = context;
        inflater = LayoutInflater.from(activityContext);
        this.stops = data;
        this.isFavourited = isFavourited;
        countNumStopsAndRoutes(data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Creates a new list item and ViewHolder when requested by the RecyclerView parent.
        //   ViewType is determined by getItemViewType() and is based on whether
        //   a given data item is title info, route info or stop info

        View view;
        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {
            case R.id.details_stops:
                view = inflater.inflate(R.layout.stop_view, parent, false);
                viewHolder = new StopsByRouteViewHolder(activityContext, isFavourited,isSubroute, view);
                break;
            case R.id.spacer_stops:
                view = inflater.inflate(R.layout.list_spacer, parent, false);
                viewHolder = new SpacerHolder(view);
                break;
            case R.id.spacer_routes:
                view = inflater.inflate(R.layout.list_spacer, parent, false);
                viewHolder = new SpacerHolder(view);
                break;
            case R.id.spacer_subroutes:
                view = inflater.inflate(R.layout.list_spacer_null, parent, false);
                viewHolder = new SpacerHolder(view);
                break;
            case R.id.details_routes:
                view = inflater.inflate(R.layout.route_view, parent, false);
                viewHolder = new RouteViewHolder(activityContext, isFavourited, view);
                break;
            case R.id.details_subroutes:
                view = inflater.inflate(R.layout.subroute_view, parent, false);
                viewHolder = new SubRouteViewHolder(activityContext, isFavourited, view);
                break;
            default:
                view = inflater.inflate(R.layout.route_view, parent, false);
                viewHolder = new RouteViewHolder(activityContext, isFavourited, view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int dataPosition) {
        // Once the recycler view parent contains a child view and the
        //   associated ViewHolder is created, this method calls on the ViewHolder
        //   bindModel method to pass the data to the appropriate view (ie. textView, etc.)
        String content;
        switch (getItemViewType(dataPosition)) {
            case R.id.spacer_stops:
                ((SpacerHolder) holder).bindModel("Stops");
                break;
            case R.id.spacer_routes:
                ((SpacerHolder) holder).bindModel("Routes");
                break;
            case R.id.spacer_subroutes:
                ((SpacerHolder) holder).bindModel("Sub Routes");
                break;
            case R.id.details_stops:
                ((StopsByRouteViewHolder) holder).bindModel("yo","1", stops[dataPosition]);
                break;
            case R.id.details_routes:
                content = stops[dataPosition];
                ((RouteViewHolder) holder).bindModel(content, false);
                break;
            case R.id.details_subroutes:
                content = stops[dataPosition];
                ((SubRouteViewHolder) holder).bindModel(content, true);
            default:
                content = stops[dataPosition];
                ((RouteViewHolder) holder).bindModel(content, false);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Returns an id in integer form specifying the type of data represented at the
        //   given position. Possibilities include title info, route info and stop info
        if (position == indexStopTitle) {
            return R.id.spacer_stops;
        } else if (position < indexRouteTitle && position > indexStopTitle) {
            return R.id.details_stops;
        } else if (position == indexRouteTitle) {
            return R.id.spacer_routes;
        } else if (position > indexRouteTitle && position < indexSubRouteTitle) {
            return R.id.details_routes;
        } else if (position == indexSubRouteTitle) {
            return R.id.spacer_subroutes;
        } else if (position > indexSubRouteTitle){
            return R.id.details_subroutes;
        } else {
            return R.id.details_routes;
        }
    }

    private int getItem(int position) {
        // determines type of data being supplied by the string array of data

        if (position == indexStopTitle) {
            return R.id.spacer_stops;
        } else if (position < indexRouteTitle && position > indexStopTitle) {
            return R.id.details_stops;
        } else if (position == indexRouteTitle) {
            return R.id.spacer_routes;
        } else if (position > indexRouteTitle && position < indexSubRouteTitle) {
            return R.id.details_routes;
        } else if (position == indexSubRouteTitle) {
            return R.id.spacer_subroutes;
        } else if (position > indexSubRouteTitle){
            return R.id.details_subroutes;
        } else {
            return R.id.details_routes;
        }
    }

    @Override
    public int getItemCount() {
        return stops.length;
    }

    private void countNumStopsAndRoutes(String[] data) {
        int dataSize = data.length;
        List<String> dataList = (List) Arrays.asList(data);

        indexStopTitle = dataList.indexOf("Stops");
        indexRouteTitle = dataList.indexOf("Routes");
        indexSubRouteTitle = dataList.indexOf("Sub Routes");
    }
}
