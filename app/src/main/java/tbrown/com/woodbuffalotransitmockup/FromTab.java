package tbrown.com.woodbuffalotransitmockup;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.melnykov.fab.FloatingActionButton;

import tbrown.com.woodbuffalotransitmockup.adapters.StopTimesAdapter;
import tbrown.com.woodbuffalotransitmockup.adapters.StopsByRouteAdapter;
import tbrown.com.woodbuffalotransitmockup.database.DBHelper;
import tbrown.com.woodbuffalotransitmockup.database.DBUtils;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;

/**
 * Created by tmast_000 on 4/24/2015.
 */
public class FromTab extends Fragment implements View.OnClickListener {
    private Context activityContext;
    private DBHelper dbHelper;
    private StopsByRouteAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    String routeInfo;
    int routeId;
    String stopInfo;
    int stopId;
    String serviceId;
    int directionId = 0;

    String[] stopNames;
    String[] stopIds;

    private static final String WEEKDAYS_ALL = "'15SPRI-All-Weekday-01'";
    private static final String SATURDAY = "'15SPRI-All-Saturday-01'";
    private static final String SUNDAY = "'15SPRI-All-Sunday-01'";

    private Spinner spin;
    private static final String[] spinnerItems = {"Mon - Fri.","Saturday", "Sunday & Holidays"};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout =inflater.inflate(R.layout.tab_schedule_from,container,false);
        setup();
        setupDatabase(activityContext);
        setupRecyclerView(layout);
        setupFAB(layout, mRecyclerView);
        return layout;
    }
    private void setupRecyclerView(View layout) {
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rvScheduleFrom);
        mAdapter = new StopsByRouteAdapter(activityContext,routeInfo,routeId,stopNames);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activityContext));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(activityContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    private void setup() {
        activityContext = getActivity();
        getRouteInfo();
    }


    private void setupDatabase(Context activityContext) {
        dbHelper = new DBHelper(activityContext);
        getStops(routeId,1);
    }

    private void setupFAB(View parent, RecyclerView rv) {
        fab = (FloatingActionButton) parent.findViewById(R.id.fab_from);
        fab.attachToRecyclerView(rv);
        fab.setOnClickListener(this);
    }


    private void getRouteInfo() {
        // Extracts the route information from the intent provided
        routeInfo = getActivity().getIntent().getStringExtra("ROUTE_INFO");
        routeId = getActivity().getIntent().getIntExtra("ROUTE_NO", 400);
    }


    private void getStops(int routeNo,int directionid) {
        stopNames = DBUtils.queryToAllRoutes(dbHelper.queryStopsbyRoute(routeNo,directionid));
        stopIds = DBUtils.queryToAllRouteIds(dbHelper.queryStopsbyRoute(routeNo,directionid));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_from:
                // Create and Launch intent to go to Maps mode
                Intent showMapsIntent = new Intent("tbrown.com.woodbuffalotransitmockup.MAP_STOPS");
                showMapsIntent.putExtra("ROUTE_INFO",routeInfo);
                showMapsIntent.putExtra("ROUTE_NO",routeId);
                showMapsIntent.putExtra("DIRECTION_ID",1);
                showMapsIntent.putExtra("STOP_IDS",stopIds);
                startActivity(showMapsIntent);
                break;
        }
    }
}
