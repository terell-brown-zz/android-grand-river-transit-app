package tbrown.com.woodbuffalotransitmockup.util;

import android.app.Fragment;
import android.support.v7.widget.RecyclerView;

/**
 * Created by tmast_000 on 4/15/2015.
 */
public class RecyclerViewFragment extends Fragment {
    RecyclerView rv = null;

    public void setAdapter(RecyclerView.Adapter adapter) {
        getRecyclerView().setAdapter(adapter);
    }

    private RecyclerView getRecyclerView() {
        if (rv == null) {
            rv = new RecyclerView(getActivity());
            rv.setHasFixedSize(true);
            //setContentView(rv);
        }
        return rv;
    }
}
