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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import tbrown.com.woodbuffalotransitmockup.adapters.StopTimesAdapter;
import tbrown.com.woodbuffalotransitmockup.database.DBHelper;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;

/**
 * Created by tmast_000 on 4/24/2015.
 */
public class ToTab extends Fragment {
    private Context activityContext;
    private DBHelper dbHelper;
    private StopTimesAdapter mAdapter;
    private RecyclerView mRecyclerView;
    String routeInfo;
    int routeId;
    String stopInfo;
    int stopId;
    String serviceId;

    private static final String WEEKDAYS_ALL = "'15SPRI-All-Weekday-01'";
    private static final String SATURDAY = "'15SPRI-All-Saturday-01'";
    private static final String SUNDAY = "'15SPRI-All-Sunday-01'";

    private Spinner spin;
    private static final String[] spinnerItems = {"Mon - Fri.","Saturday", "Sunday & Holidays"};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout =inflater.inflate(R.layout.tab_schedule_to,container,false);
        setup();
        setupDatabase(activityContext);
        setupRecyclerView(layout);

        return layout;
    }
    private void setupRecyclerView(View parent) {
        mRecyclerView = (RecyclerView) parent.findViewById(R.id.rvScheduleTo);
        mAdapter = new StopTimesAdapter(activityContext,routeInfo,getTimes());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activityContext));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(activityContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    private void setup() {
        activityContext = getActivity();
        getTransitInfo();
    }

    private void setupDatabase(Context activityContext) {
        dbHelper = new DBHelper(activityContext);
    }

    private void getTransitInfo() {
        routeInfo = getActivity().getIntent().getStringExtra("ROUTE_INFO");
        routeId = getActivity().getIntent().getIntExtra("ROUTE_NO", 400);
        stopInfo = getActivity().getIntent().getStringExtra("STOP_INFO");
        stopId = getActivity().getIntent().getIntExtra("STOP_ID", 5425);
        serviceId = getActivity().getIntent().getStringExtra("SERVICE_ID");
    }

    private String[] getTimes() {
        String[] times = dbHelper.getTimes(routeId, serviceId,1,stopId);
        if (times.length < 1) {
            switch (serviceId) {
                case WEEKDAYS_ALL:
                    return new String[]{"No service in this direction from Mon - Fri."};
                case SATURDAY:
                    return new String[]{"No service in this direction on Saturday"};
                case SUNDAY:
                    return new String[]{"No service in this direction Sundays & Holidays"};
            }

        }
        return times;
    }


}
