package tbrown.com.woodbuffalotransitmockup.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import tbrown.com.woodbuffalotransitmockup.R;
import tbrown.com.woodbuffalotransitmockup.adapters.AllRoutesAdapter;
import tbrown.com.woodbuffalotransitmockup.adapters.StopInfoAdapter;
import tbrown.com.woodbuffalotransitmockup.database.DBHelper;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;

/*
 * Displays upcoming stop times for a chosen stop
 */

public class StopInfoActivity extends BaseActivity {

    // Backend Components
    private Context activityContext;

    // List of Times
    private StopInfoAdapter adapter;
    private RecyclerView mRecyclerView;

    // Business Logic
    private String[] routes;
    private String[] upcomingStopTimes;
    private String stopName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_info);
        activityContext = getBaseContext();
        getStopInfo();
        setupToolbar(stopName);
        toolbar.setLogo(R.drawable.ic_bus);
        setUpRecyclerView();
    }

    private void getStopInfo() {
        Intent intent = getIntent();
        stopName = intent.getStringExtra("STOP_NAME");
        routes = intent.getStringArrayExtra("ROUTES");
        upcomingStopTimes = intent.getStringArrayExtra("TIMES");
    }

    private void setUpRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rvStopInfo);
        adapter = new StopInfoAdapter(activityContext, routes, upcomingStopTimes);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activityContext));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(activityContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
