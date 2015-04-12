package tbrown.com.woodbuffalotransitmockup.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import tbrown.com.woodbuffalotransitmockup.R;
import tbrown.com.woodbuffalotransitmockup.datamodels.StopOverview;
import tbrown.com.woodbuffalotransitmockup.viewholder.RouteViewHolder;
import tbrown.com.woodbuffalotransitmockup.viewholder.SpacerHolder;
import tbrown.com.woodbuffalotransitmockup.viewholder.StopViewHolder;

/**
 * Created by tmast_000 on 4/5/2015.
 */
public class StopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    String[][] data = null;
    String label;
    RecyclerView.ViewHolder holder;
    String itemType;

    public StopAdapter(Context context,String[][] data) {
            inflater = LayoutInflater.from(context);
            this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case R.id.details_stops:
                View view =inflater.inflate(R.layout.stop_view, parent, false);
                StopViewHolder stopsHolder = new StopViewHolder(view);
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
                View view2 =inflater.inflate(R.layout.stop_view, parent, false);
                RouteViewHolder holder = new RouteViewHolder(view2);
                return holder;
            default:
                View view3 =inflater.inflate(R.layout.stop_view, parent, false);
                StopViewHolder stopsHolder2 = new StopViewHolder(view3);
                return stopsHolder2;
        }


    }

    //@Override
    //public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    //}

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int dataPosition) {

        if (itemType == "SPACER_STOPS" || itemType == "SPACER_ROUTES") {
            String dataItem = data[dataPosition];
        }
        String current = data.get(dataPosition);
        holder.stopName.setText(current.getName());
    }

    @Override
    public int getItemViewType(int dataPosition) {

        switch (getItem(dataPosition)){
            case "SPACER_STOPS":
            label = "Stops";
            return R.id.spacer_stops;

            case "SPACER_ROUTES":
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

    private String getItem(int dataPosition) {
        int offset = dataPosition;
        int batchIndex = 0;
        itemType = null;

        for (String[] batch : data) {
            if (offset == 0) {
                if (batchIndex == 0) {
                    itemType = "SPACER_TOPS";
                    break;
                } else {
                    itemType = "SPACER_ROUTES";
                    break;
                }
            }

            if (offset < batch.length) {
                itemType = "DETAILS_STOPS";
                break;
            } else {
                itemType = "DETAILS_ROUTES";
                break;
            }
        }
            return itemType;
        }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private int getRow(int dataPosition) {

    }



}
