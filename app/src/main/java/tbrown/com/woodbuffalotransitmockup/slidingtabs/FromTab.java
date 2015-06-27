package tbrown.com.woodbuffalotransitmockup.slidingtabs;


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
import android.widget.Spinner;

import com.melnykov.fab.FloatingActionButton;

import tbrown.com.woodbuffalotransitmockup.R;
import tbrown.com.woodbuffalotransitmockup.adapters.StopsByRouteAdapter;
import tbrown.com.woodbuffalotransitmockup.database.DBHelper;
import tbrown.com.woodbuffalotransitmockup.util.DBUtils;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;

/**
 * Shows list of stops going in one direction as selected by UI tabs
 */
public class FromTab extends Fragment implements View.OnClickListener {

    // Backend Components
    private Context activityContext;
    private DBHelper dbHelper;

    // UI
    private FloatingActionButton fab;
    private StopsByRouteAdapter mAdapter;
    private RecyclerView mRecyclerView;

    // Transit Info
    private String routeInfo;
    private int routeId;
    private String[] stopNames;
    private String[] stopIds;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout =inflater.inflate(R.layout.tab_schedule_from,container,false);
        activityContext = getActivity();
        setupDatabase(activityContext);
        getRouteInfo();
        getStops(routeId,1);
        setupRecyclerView(layout);
        setupFAB(layout, mRecyclerView);
        return layout;
    }


    private void setupDatabase(Context activityContext) {
        // TODO: move this method to database class and use singleton design pattern
        dbHelper = DBHelper.getInstance(activityContext);//new DBHelper(activityContext);
    }

    private void getRouteInfo() {
        Intent intent = getActivity().getIntent();
        routeInfo = intent.getStringExtra("ROUTE_INFO");
        routeId = intent.getIntExtra("ROUTE_NO", 400);
    }

    private void getStops(int routeNo,int directionId) {
        // TODO: move to DBUtilClass
        stopNames = DBUtils.queryToAllRoutes(dbHelper.queryStopsbyRoute(routeNo, directionId));
        stopIds = DBUtils.queryToAllRouteIds(dbHelper.queryStopsbyRoute(routeNo, directionId));
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

    private void setupFAB(View parent, RecyclerView rv) {
        fab = (FloatingActionButton) parent.findViewById(R.id.fab_from);
        fab.attachToRecyclerView(rv);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_from:
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
