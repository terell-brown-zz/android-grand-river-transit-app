package tbrown.com.woodbuffalotransitmockup.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tbrown.com.woodbuffalotransitmockup.R;

/**
 * Displays rows in lists that act as dividers and titles for list items that follow
 */
public class SpacerHolder extends RecyclerView.ViewHolder {

    private TextView tvTitle;

    public SpacerHolder(View row) {
        super(row);
        tvTitle = (TextView) row.findViewById(R.id.tvSpacerTitle);
    }

    public void bindModel (String title) {
        tvTitle.setText(title);
    }
}
