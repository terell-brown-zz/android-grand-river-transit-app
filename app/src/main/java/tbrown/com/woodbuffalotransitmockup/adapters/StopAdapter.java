package tbrown.com.woodbuffalotransitmockup.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import tbrown.com.woodbuffalotransitmockup.R;
import tbrown.com.woodbuffalotransitmockup.viewholders.RouteViewHolder;
import tbrown.com.woodbuffalotransitmockup.viewholders.SpacerHolder;
import tbrown.com.woodbuffalotransitmockup.viewholders.StopsByRouteViewHolder;

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
    private int indexStopTitle;
    private int indexRouteTitle;
    private int numStops;
    private int numRoutes;
    private String itemType;
    private boolean isFavourited;

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
                viewHolder = new StopsByRouteViewHolder(activityContext, isFavourited, view);
                break;
            case R.id.spacer_stops:
                view = inflater.inflate(R.layout.list_spacer, parent, false);
                viewHolder = new SpacerHolder(view);
                break;
            case R.id.spacer_routes:
                view = inflater.inflate(R.layout.list_spacer, parent, false);
                viewHolder = new SpacerHolder(view);
                break;
            case R.id.details_routes:
                view = inflater.inflate(R.layout.route_view, parent, false);
                viewHolder = new RouteViewHolder(activityContext, isFavourited, view);
                break;
            default:
                view = inflater.inflate(R.layout.stop_view, parent, false);
                viewHolder = new StopsByRouteViewHolder(activityContext, isFavourited, view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int dataPosition) {
        // Once the recycler view parent contains a child view and the
        //   associated ViewHolder is created, this method calls on the ViewHolder
        //   bindModel method to pass the data to the appropriate view (ie. textView, etc.)
        switch (getItem(dataPosition)) {
            case "TITLE_STOPS":
                ((SpacerHolder) holder).bindModel("Stops");
                break;
            case "TITLE_ROUTES":
                ((SpacerHolder) holder).bindModel("Routes");
                break;

            case "DETAILS_STOPS":
                ((StopsByRouteViewHolder) holder).bindModel("yo", 1, stops[dataPosition]);
                break;
            case "DETAILS_ROUTES":
                ((RouteViewHolder) holder).bindModel(stops[dataPosition]);
                break;
            default:
                ((SpacerHolder) holder).bindModel(stops[dataPosition]);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Returns an id in integer form specifying the type of data represented at the
        //   given position. Possibilities include title info, route info and stop info
        switch (getItem(position)) {
            case "TITLE_STOPS":
                return R.id.spacer_stops;
            case "TITLE_ROUTES":
                return R.id.spacer_routes;
            case "DETAILS_STOPS":
                return R.id.details_stops;
            case "DETAILS_ROUTES":
                return R.id.details_routes;
            default:
                return R.id.details_stops;
        }

    }

    private String getItem(int position) {
        // determines type of data being supplied by the string array of data
        if (position == indexStopTitle) {
            itemType = "TITLE_STOPS";
        } else if (position < indexRouteTitle) {
            itemType = "DETAILS_STOPS";
        } else if (position == indexRouteTitle) {
            itemType = "TITLE_ROUTES";
        } else if (position > indexRouteTitle) {
            itemType = "DETAILS_ROUTES";
        }
        return itemType;
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
        numStops = indexRouteTitle - 1;
        numRoutes = dataSize - 1;
    }
}
