package tbrown.com.woodbuffalotransitmockup;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tbrown.com.woodbuffalotransitmockup.adapters.StopTimesAdapter;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;

/**
 * Created by tmast_000 on 4/24/2015.
 */
public class ToTab extends Fragment {
    private Context activityContext;
    private StopTimesAdapter mAdapter;
    private RecyclerView mRecyclerView;
    String routeInfo;
    String stopInfo;


    static String[][] stopsByRoute = {
            {"1 Timberlea Express", "Main St. and Franklin Ave. Transfer Stn","Eagle Ridge Gate @ Louitit Road","Powder Station"},
            {"2 Thickwood Express","Main St. and Franklin Ave. Transfer Stn","Signal Rd. @ Thickwood Shopping Plaza","Westwood School" }, {"3 Morgan Heights","Test Stop"},
            {"7 Abasand Heights","Test Stop"}, {"11 Airport Shuttle","Test Stop"},{"12 Timberlea / Thickwood Local","Test Stop"},
            {"13 Heritage Hills","Test Stop"},{"14 Taiga Nova","Test Stop"},{"31 Timberlea","Test Stop"},{"32 Timberlea","Test Stop"},{"41 Stoney Creek - Eagle Ridge","Test Stop"},
            {"42 Eagle Ridge - Stoney Creek","Test Stop"}, {"51 Wood Buffalo","Test Stop"},{"61 Thickwood","Test Stop"},{"62 Thickwood","Test Stop"},{"8 Beacon Hill","Test Stop"},
            {"91 Downtown East","Test Stop"},{"92 Downtown West","Test Stop"},{"99 MacDonald Island","Test Stop"},{"10A Gregoire/Industrial","Test Stop"},{"10B Gregoire/Industrial","Test Stop"},
            {"0 Saprae Creek Estates","Test Stop"},{"0 Industrial A","Test Stop"},{"0 Industrial B","Test Stop"}};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout =inflater.inflate(R.layout.tab_schedule_to,container,false);
        setup();
        setupRecyclerView(layout);

        return layout;
    }
    private void setupRecyclerView(View parent) {
        mRecyclerView = (RecyclerView) parent.findViewById(R.id.rvScheduleTo);
        mAdapter = new StopTimesAdapter(activityContext,routeInfo,getData(routeInfo));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activityContext));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(activityContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setup() {
        activityContext = getActivity();
        getRouteInfo();
        getStopInfo();
    }

    private void getRouteInfo() {
        // Extracts the route information from the intent provided
        routeInfo = getActivity().getIntent().getStringExtra("ROUTE_INFO");
        String r = routeInfo;
    }

    private void getStopInfo() {
        // Extracts the route information from the intent provided
        stopInfo = getActivity().getIntent().getStringExtra("STOP_INFO");
        String r = stopInfo;
    }

    private static String[] getData(String routeInfo) {
        String[] times = {"6:30 AM","7:00 AM","7:30 AM","8:00 AM","8:30 AM","9:00 AM","9:30 AM",
                "10:00 AM","10:30 AM","11:00 AM","11:30 AM","12:00 PM","12:30 PM","1:00 PM","1:30 PM",
                "2:00 PM","2:30 PM","3:00 PM","3:30 PM","4:00 PM","4:30 PM","5:00 PM","5:30 PM"};
        return times;
    }


}
