package tbrown.com.woodbuffalotransitmockup.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import tbrown.com.woodbuffalotransitmockup.R;

/**
 * Used for:
 */
public class StopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView tvStopName;
    Context activityContext;
    ImageView faveIcon;
    public StopViewHolder(View itemView) {
        super(itemView);
        //context = c;
        itemView.setClickable(true);
        tvStopName = (TextView) itemView.findViewById(R.id.tvStopName);
        faveIcon = (ImageView) itemView.findViewById(R.id.ic_favourite_selected);
        }

    public void bindModel(String routeName) {
        tvStopName.setText(routeName);
    }

    @Override
    public void onClick(View v) {

    }

//    private void toggleFavourites() {
//        // Toggle the favourites icon (star) located in the tool bar between
//        // selected and unselected
//
//        if (faveButton.isPressed() == false) {
//            faveButton.setVisibility(View.INVISIBLE);
//            return;
//        } else {
//            faveButton.setVisibility(View.VISIBLE);
//        };
//
//    }
}
