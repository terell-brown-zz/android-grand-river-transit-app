package tbrown.com.woodbuffalotransitmockup;

/**
 * Created by tmast_000 on 4/4/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tbrown.com.woodbuffalotransitmockup.adapters.AllRoutesAdapter;
import tbrown.com.woodbuffalotransitmockup.adapters.StopAdapter;
import tbrown.com.woodbuffalotransitmockup.database.DBHelper;
import tbrown.com.woodbuffalotransitmockup.database.DBUtils;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;

/**
 * Created by tmast_000 on 4/4/2015.
 */

public class AllTab extends Fragment {

    private Activity activityContext;
    private AllRoutesAdapter mAllRoutesAdapter;
    private RecyclerView nRecyclerView;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityContext = getActivity();
        setupDatabase(activityContext);

        View layout = inflater.inflate(R.layout.tab_all, container, false);
        setUpRecyclerView(layout);
        return layout;
    }

    private void setUpRecyclerView(View layout) {
        nRecyclerView = (RecyclerView) layout.findViewById(R.id.rvAllRoutes);
        mAllRoutesAdapter = new AllRoutesAdapter(activityContext, getAllRoutes());
        nRecyclerView.setAdapter(mAllRoutesAdapter);
        nRecyclerView.setHasFixedSize(true);
        nRecyclerView.setLayoutManager(new LinearLayoutManager(activityContext));
        nRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(activityContext));
        nRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setupDatabase(Context activityContext) {
        dbHelper = new DBHelper(activityContext);
    }

    public String[] getAllRoutes() {
        // Returns and String array containing all routes in the transit system
        return dbHelper.getAllRoutes();
/*        String[] allRoutes = {"1 Timberlea Express", "2 Thickwood Express", "3 Morgan Heights",
                "7 Abasand Heights", "11 Airport Shuttle", "12 Timberlea / Thickwood Local",
                "13 Heritage Hills", "14 Taiga Nova", "31 Timberlea", "32 Timberlea", "41 Stoney Creek - Eagle Ridge",
                "42 Eagle Ridge - Stoney Creek", "51 Wood Buffalo", "61 Thickwood", "62 Thickwood", "8 Beacon Hill",
                "91 Downtown East", "92 Downtown West", "99 MacDonald Island", "10A Gregoire/Industrial", "10B Gregoire/Industrial",
                "0 Saprae Creek Estates", "0 Industrial A", "0 Industrial B"};*/
    //return allRoutes;
    }
}


