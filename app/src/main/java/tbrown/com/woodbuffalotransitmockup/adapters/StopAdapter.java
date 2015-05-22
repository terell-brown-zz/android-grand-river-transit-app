package tbrown.com.woodbuffalotransitmockup.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import tbrown.com.woodbuffalotransitmockup.R;
import tbrown.com.woodbuffalotransitmockup.datamodels.StopOverview;
import tbrown.com.woodbuffalotransitmockup.viewholder.RouteViewHolder;
import tbrown.com.woodbuffalotransitmockup.viewholder.SpacerHolder;
import tbrown.com.woodbuffalotransitmockup.viewholder.StopViewHolder;
import tbrown.com.woodbuffalotransitmockup.viewholder.StopsByRouteViewHolder;

/**
 * Created by tmast_000 on 4/5/2015.
 */
public class StopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static int indexStopTitle;
    private static int indexRouteTitle;
    private static int numStops;
    private static int numRoutes;

    Context activityContext;

    private LayoutInflater inflater;
    String[] database = null;
    String label;
    String itemType;

    public StopAdapter(Context context,String[] data) {
        activityContext = context;
        inflater = LayoutInflater.from(activityContext);
        this.database = data;

            countNumStopsAndRoutes(data);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Creates a new list item and ViewHolder when requested by the RecyclerView parent.
        //   ViewType is determined by getItemViewType() and is based on whether
        //   a given data item is title info, route info or stop info

        switch (viewType) {
            case R.id.details_stops:
                View view =inflater.inflate(R.layout.stop_view, parent, false);
                StopsByRouteViewHolder stopsHolder = new StopsByRouteViewHolder(activityContext,"Route",view);
                return stopsHolder;
            case R.id.spacer_stops:
                 View viewSpacer =inflater.inflate(R.layout.list_spacer, parent, false);
                 SpacerHolder spacerHolder = new SpacerHolder(viewSpacer);
                 return spacerHolder;
            case R.id.spacer_routes:
                View viewSpacer2 =inflater.inflate(R.layout.list_spacer, parent, false);
                SpacerHolder spacerHolder2 = new SpacerHolder(viewSpacer2);
                return spacerHolder2;
            case R.id.details_routes:
                View view2 =inflater.inflate(R.layout.route_view, parent, false);
                RouteViewHolder holder = new RouteViewHolder(activityContext,view2);
                return holder;
            default:
                View view3 =inflater.inflate(R.layout.stop_view, parent, false);
                StopsByRouteViewHolder stopsHolder2 = new StopsByRouteViewHolder(activityContext,"Route",view3);
                return stopsHolder2;
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int dataPosition) {
        // Once the recycler view parent contains a child view and the
        //   associated ViewHolder is created, this method calls on the ViewHolder
        //   bindModel method to pass the data to the appropriate view (ie. textView, etc.)
        switch (getItem(dataPosition)){
            case "TITLE_STOPS":
                ((SpacerHolder) holder).bindModel("Stops");
                break;
            case "TITLE_ROUTES":
                ((SpacerHolder) holder).bindModel("Routes");
                break;

            case "DETAILS_STOPS":
                ((StopsByRouteViewHolder) holder).bindModel("10 Route Name",database[dataPosition]);
                break;
            case "DETAILS_ROUTES":
                ((RouteViewHolder) holder).bindModel(database[dataPosition]);
                break;
            default:
                ((SpacerHolder) holder).bindModel(database[dataPosition]);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Returns an id in integer form specifying the type of data represented at the
        //   given position. Possibilities include title info, route info and stop info
        switch (getItem(position)){
            case "TITLE_STOPS":
            label = "Stops";
            return R.id.spacer_stops;

            case "TITLE_ROUTES":
            label = "Stops";
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
        // Helper function to getItemView which determines type of the data based on
        //   position provided. Functionality of getItemView and getItem to be combined
        //   into one function later.
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
        return database.length;
    }


    private static void countNumStopsAndRoutes(String[] data) {
        int dataSize = data.length;
        System.out.println(dataSize);
        List<String> dataList = (List) Arrays.asList(data);

        indexStopTitle = dataList.indexOf("Stops");
        indexRouteTitle = dataList.indexOf("Routes");
        numStops = indexRouteTitle - 1;
        numRoutes = dataSize - 1;
    }

}
