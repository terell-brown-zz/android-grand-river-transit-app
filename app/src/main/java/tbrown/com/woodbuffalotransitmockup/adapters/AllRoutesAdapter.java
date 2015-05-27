package tbrown.com.woodbuffalotransitmockup.adapters;

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
import tbrown.com.woodbuffalotransitmockup.viewholder.RouteViewHolder;
import tbrown.com.woodbuffalotransitmockup.viewholder.SpacerHolder;
import tbrown.com.woodbuffalotransitmockup.viewholder.StopViewHolder;

/**
 * Created by tmast_000 on 4/5/2015.
 */
public class AllRoutesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context activityContext;
    private LayoutInflater inflater;
    String[] database = null;


    public AllRoutesAdapter(Context context,String[] data) {
        activityContext = context;
        inflater = LayoutInflater.from(activityContext);
        this.database = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View row =inflater.inflate(R.layout.route_view, parent, false);
                RouteViewHolder holder = new RouteViewHolder(activityContext, row);
                return holder;
        }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((RouteViewHolder) holder).bindModel(database[position]);
        }

    @Override
    public int getItemCount() {
        return database.length;
    }

    private int getSeperatingIndex(String RouteNumAndName) {
        // This method returns the index of the space character (" ") which
        //    seperates the the Route Number and Route Name in the string provided.
        //    The string comes from the database.

        return RouteNumAndName.indexOf(" ");
    }
}
