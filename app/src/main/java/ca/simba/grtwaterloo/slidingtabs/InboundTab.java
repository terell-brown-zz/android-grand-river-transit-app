package ca.simba.grtwaterloo.slidingtabs;


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

import com.melnykov.fab.FloatingActionButton;

import ca.simba.grtwaterloo.Constants;
import ca.simba.grtwaterloo.R;
import ca.simba.grtwaterloo.adapters.StopsByRouteAdapter;
import ca.simba.grtwaterloo.database.DBHelper;
import ca.simba.grtwaterloo.util.DBUtils;
import ca.simba.grtwaterloo.util.SimpleDividerItemDecoration;

/**
 * Shows list of stops going in one direction as selected by UI tabs
 */
public class InboundTab extends Fragment implements View.OnClickListener {

    // Backend Components
    private Context activityContext;
    private DBHelper dbHelper;

    // UI
    private FloatingActionButton fab;
    private StopsByRouteAdapter mAdapter;
    private RecyclerView mRecyclerView;

    // Transit Info
    private String routeInfo;
    private String routeName;
    private String routeId;
    private String[] stopNames;
    private String[] stopIds;
    private boolean isSubRoute;
    private int numDirections;

    // Constants
    private static int DIRECTION_ID = Constants.INBOUND_ID;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout =inflater.inflate(R.layout.tab_schedule_to,container,false);
        activityContext = getActivity();
        getRouteInfo();
        setupDatabase(activityContext);
        if (numDirections == 1) {
            getDirectionId();
        }
        getStops(routeId, DIRECTION_ID);
        setupRecyclerView(layout);
        setupFAB(layout, mRecyclerView);
        return layout;
    }

    private void getDirectionId() {
        if (isSubRoute) {
            DIRECTION_ID = dbHelper.queryDirectionByTripName(routeInfo);
        } else {
            DIRECTION_ID = dbHelper.queryDirectionByRouteNo(routeId);
        }
    }

    private void getRouteInfo() {
        Intent intent = getActivity().getIntent();
        routeInfo = intent.getStringExtra("ROUTE_INFO");
        routeId = intent.getStringExtra("ROUTE_NO");
        isSubRoute = intent.getBooleanExtra("IS_SUBROUTE", false);
        numDirections = intent.getIntExtra("NUM_DIRECTIONS", 1);
    }

    private void getStops(String routeNo,int directionId) {
        Cursor cStops;
        if (isSubRoute) {
            cStops = dbHelper.queryStopsbySubRoute(routeInfo, directionId);
            stopNames = DBUtils.queryToAllRoutes(cStops);
            stopIds = DBUtils.queryToAllRouteIds(cStops);
        } else {
            cStops = dbHelper.queryStopsbyRoute(routeNo, directionId);
            stopNames = DBUtils.queryToAllRoutes(cStops);
            stopIds = DBUtils.queryToAllRouteIds(cStops);
        }
    }

    private void setupDatabase(Context activityContext) {
        dbHelper = DBHelper.getInstance(activityContext);//new DBHelper(activityContext);
    }

    private void setupRecyclerView(View layout) {
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rvScheduleTo);
        mAdapter = new StopsByRouteAdapter(activityContext,routeInfo,routeId,stopNames,isSubRoute);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activityContext));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(activityContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setupFAB(View parent, RecyclerView rv) {
        fab = (FloatingActionButton) parent.findViewById(R.id.fab_to);
        fab.attachToRecyclerView(rv);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_to:
                Intent showMapsIntent = new Intent("ca.simba.grtwaterloo.activities.MAP_STOPS");
                showMapsIntent.putExtra("ROUTE_INFO",routeInfo);
                showMapsIntent.putExtra("ROUTE_NO",routeId);
                showMapsIntent.putExtra("DIRECTION_ID",DIRECTION_ID);
                showMapsIntent.putExtra("STOP_IDS",stopIds);
                showMapsIntent.putExtra("IS_SUBROUTE",isSubRoute);
                startActivity(showMapsIntent);
                break;
        }
    }
}
