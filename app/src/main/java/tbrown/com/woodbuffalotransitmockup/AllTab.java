package tbrown.com.woodbuffalotransitmockup;

/**
 * Created by tmast_000 on 4/4/2015.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tmast_000 on 4/4/2015.
 */

public class AllTab extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_all,container,false);
        return v;
    }
}
