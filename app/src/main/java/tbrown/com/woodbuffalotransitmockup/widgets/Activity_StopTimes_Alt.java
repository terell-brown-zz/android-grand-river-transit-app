package tbrown.com.woodbuffalotransitmockup.widgets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import tbrown.com.woodbuffalotransitmockup.R;
import tbrown.com.woodbuffalotransitmockup.adapters.StopTimesAdapter;
import tbrown.com.woodbuffalotransitmockup.database.DBHelper;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;

/**
 * Created by tmast_000 on 5/23/2015.
 */
public class Activity_StopTimes_Alt extends ActionBarActivity {
    private Context activityContext;
    private DBHelper dbHelper;
    private Toolbar toolbar;
    private Toolbar tab;
    private TextView tabTitle;
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
    private static final String[] SERVICES = {WEEKDAYS_ALL,SATURDAY,SUNDAY};

    private Spinner spin;
    private static final String[] spinnerItems = {"Weekdays","Saturday", "Sunday & Holidays"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContext = getBaseContext();
        setContentView(R.layout.activity_stop_details);
        setupSpinner();
        // Extract route info from intent
        getTransitInfo();
        setupDatabase(activityContext);
        // Creating the Toolbar and setting it as the Toolbar for the activity
        setupToolbar();
        setupTab();
        setupRecyclerView();

        //setupRecyclerView();
    }

    private void setupToolbar() {
        // Creating the Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        getSupportActionBar().setTitle("");
        toolbar.setTitle(stopInfo);
        toolbar.setTitleTextColor(getResources().getColor(R.color.ColorToolbarTitle));
        toolbar.setLogo(R.drawable.ic_bus);

    }

    private void setupTab() {
        tab = (Toolbar) findViewById(R.id.tab); // Attaching the layout to the toolbar object
        setSupportActionBar(tab);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        getSupportActionBar().setTitle("");
        tabTitle = (TextView) tab.findViewById(R.id.tvTabTitle);
        tabTitle.setText(routeId + " - To Downtown");
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rvScheduleTo);
        mAdapter = new StopTimesAdapter(activityContext,routeInfo,getTimes());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activityContext));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(activityContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    private void setupDatabase(Context activityContext) {
        dbHelper = new DBHelper(activityContext);
    }

    private void setupSpinner () {
        // Setup spinner used to select time of week for schedule
        spin = (Spinner) findViewById(R.id.spinSchedule);
        //String[] items = getResources().getStringArray(R.array.services);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activityContext,
                android.R.layout.simple_spinner_item,
                spinnerItems);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(arrayAdapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Intent intent = new Intent("tbrown.com.woodbuffalotransitmockup.STOP_TIMES");

                Intent intent = new Intent(getApplicationContext(),Activity_StopTimes.class);
                intent.putExtra("ROUTE_INFO",routeInfo);
                intent.putExtra("ROUTE_NO",routeId);
                intent.putExtra("STOP_INFO",stopInfo);
                intent.putExtra("STOP_ID",stopId);
                intent.putExtra("SERVICE_ID", SERVICES[position]);
                intent.putExtra("DIRECTION_ID", getIntent().getIntExtra("DIRECTION_ID", 0));
                finish();
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }

        });
    }

    private void getTransitInfo() {
        routeInfo = getIntent().getStringExtra("ROUTE_INFO");
        routeId = getIntent().getIntExtra("ROUTE_NO", 400);
        stopInfo = getIntent().getStringExtra("STOP_INFO");
        stopId = getIntent().getIntExtra("STOP_ID", 5425);
        serviceId = getIntent().getStringExtra("SERVICE_ID");
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


