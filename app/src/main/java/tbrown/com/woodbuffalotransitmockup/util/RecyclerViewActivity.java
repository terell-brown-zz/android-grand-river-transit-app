package tbrown.com.woodbuffalotransitmockup.util;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

/**
 * Created by tmast_000 on 4/15/2015.
 */
public class RecyclerViewActivity extends Activity {

    RecyclerView rv = null;

    public void setAdapter(RecyclerView.Adapter adapter) {
        getRecyclerView().setAdapter(adapter);
    }

    private RecyclerView getRecyclerView() {
        if (rv == null) {
            rv = new RecyclerView(this);
            rv.setHasFixedSize(true);
            setContentView(rv);
        }
        return rv;
    }

}
