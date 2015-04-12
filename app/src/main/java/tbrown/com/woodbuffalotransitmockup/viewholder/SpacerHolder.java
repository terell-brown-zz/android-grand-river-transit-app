package tbrown.com.woodbuffalotransitmockup.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tbrown.com.woodbuffalotransitmockup.R;

/**
 * Created by tmast_000 on 4/11/2015.
 */
public class SpacerHolder extends RecyclerView.ViewHolder {

    TextView title;

    String template = null;

    public SpacerHolder(View itemView) {
        super(itemView);
        itemView.setClickable(true);
        title = (TextView) itemView.findViewById(R.id.tvSpacerTitle);
        template = title.getContext().getString(R.string.header_template);

    }

    void bindModel (Integer headerIndex) {
        title.setText("Routes");
    }
}
