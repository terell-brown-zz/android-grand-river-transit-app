package tbrown.com.woodbuffalotransitmockup.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import tbrown.com.woodbuffalotransitmockup.R;

/**
 * Created by tmast_000 on 4/5/2015.
 */
public class StopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView stopName;
    Context context;
    ImageButton faveButton;
    public StopViewHolder(View itemView) {
        super(itemView);
        //context = c;
        itemView.setClickable(true);
        stopName = (TextView) itemView.findViewById(R.id.tvStopName);
        faveButton = (ImageButton) itemView.findViewById(R.id.action_favourite_selected);
        faveButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

    }

    private void toggleFavourites() {
        // Toggle the favourites icon (star) located in the tool bar between
        // selected and unselected

        if (faveButton.isPressed() == false) {
            faveButton.setVisibility(View.INVISIBLE);
            return;
        } else {
            faveButton.setVisibility(View.VISIBLE);
        };

    }
}