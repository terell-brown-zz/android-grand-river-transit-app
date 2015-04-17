package tbrown.com.woodbuffalotransitmockup.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import tbrown.com.woodbuffalotransitmockup.R;

/**
 * Created by tmast_000 on 4/11/2015.
 */
public class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView tvRouteName;
    TextView tvRouteNo;
    ImageButton faveButton;

    public RouteViewHolder(View row) {
        super(row);
        row.setClickable(true);
        tvRouteName = (TextView) row.findViewById(R.id.tvRouteName);
        tvRouteNo = (TextView) row.findViewById(R.id.tvRouteNo);
        faveButton = (ImageButton) row.findViewById(R.id.action_favourite_selected);
        faveButton.setOnClickListener(this);
    }

    public void bindModel(String routeInfo) {
        // index separating route number and route name from routeInfo
        int indexOfSeparation = getSeparatingIndex(routeInfo);
        String routeNo = routeInfo.substring(0,indexOfSeparation);
        String routeName = routeInfo.substring(indexOfSeparation + 1);
        tvRouteNo.setText(routeNo);
        tvRouteName.setText(routeName);
    }

    private int getSeparatingIndex(String routeInfo) {
        // This method returns the index of the space character (" ") which
        //    separates the the Route Number and Route Name in the string provided.
        //    The string comes from the database.

        return routeInfo.indexOf(" ");
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
