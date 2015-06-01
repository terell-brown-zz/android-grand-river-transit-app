package tbrown.com.woodbuffalotransitmockup.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tbrown.com.woodbuffalotransitmockup.R;
import tbrown.com.woodbuffalotransitmockup.viewholder.RouteViewHolder;

/**
 * Created by tmast_000 on 4/5/2015.
 */
public class AllRoutesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context activityContext;
    private LayoutInflater inflater;
    String[] database = null;


    int viewContext = 1; // Context for which the list is being used
    int NORMAL = 0; // the list is showing info normally
    int FAVOURITES = 1; // the list is showing favourites data


    public AllRoutesAdapter(Context context,String[] data) {
        activityContext = context;
        inflater = LayoutInflater.from(activityContext);
        this.database = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View row =inflater.inflate(R.layout.route_view, parent, false);
                RouteViewHolder holder = new RouteViewHolder(activityContext,NORMAL, row);
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
