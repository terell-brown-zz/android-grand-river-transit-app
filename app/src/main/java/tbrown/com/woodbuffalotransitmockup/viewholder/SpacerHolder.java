package tbrown.com.woodbuffalotransitmockup.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tbrown.com.woodbuffalotransitmockup.R;

/**
 * Created by tmast_000 on 4/11/2015.
 */
public class SpacerHolder extends RecyclerView.ViewHolder {

    TextView tvTitle = null;

    public SpacerHolder(View row) {
        super(row);
        tvTitle = (TextView) row.findViewById(R.id.tvSpacerTitle);
    }

    public void bindModel (String title) {
        tvTitle.setText(title);
    }
}
