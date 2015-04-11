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

/**
 * Created by tmast_000 on 4/5/2015.
 */
public class StopAdapter extends RecyclerView.Adapter<StopAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    List<StopOverview> data = Collections.emptyList();

    public StopAdapter(Context context,List<StopOverview> data) {
            inflater = LayoutInflater.from(context);
            this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.stop_view, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StopOverview current = data.get(position);
        holder.stopName.setText(current.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView stopName;
        Context context;
        public MyViewHolder(View itemView) {//}, int ViewType, Context c) {
            super(itemView);
            //context = c;
            itemView.setClickable(true);
            stopName = (TextView) itemView.findViewById(R.id.tvStopName);
        }
    }
}
