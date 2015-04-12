package tbrown.com.woodbuffalotransitmockup.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import tbrown.com.woodbuffalotransitmockup.R;

/**
 * Created by tmast_000 on 4/11/2015.
 */
public class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView tvRouteName;
    ImageButton faveButton;


    public RouteViewHolder(View itemView) {
        super(itemView);
        itemView.setClickable(true);
        tvRouteName = (TextView) itemView.findViewById(R.id.tvRouteName);
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
