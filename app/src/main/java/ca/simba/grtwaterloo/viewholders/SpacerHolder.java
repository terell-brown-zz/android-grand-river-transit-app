package ca.simba.grtwaterloo.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ca.simba.grtwaterloo.R;

/**
 * Displays rows in lists that act as dividers and titles for list items that follow
 */
public class SpacerHolder extends RecyclerView.ViewHolder {

    private TextView tvTitle;
    private View row;

    public SpacerHolder(View view) {
        super(view);
        this.row = view;
        tvTitle = (TextView) row.findViewById(R.id.tvSpacerTitle);
    }

    public void bindModel (String title) {
        if (title.equals("Sub Routes")) {
            row.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }
    }
}
