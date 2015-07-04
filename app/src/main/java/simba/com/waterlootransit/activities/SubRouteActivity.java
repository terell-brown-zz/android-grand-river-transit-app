package simba.com.waterlootransit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import simba.com.waterlootransit.R;
import simba.com.waterlootransit.adapters.SubRouteAdapter;
import simba.com.waterlootransit.database.DBHelper;
import simba.com.waterlootransit.util.SimpleDividerItemDecoration;

/*
 * Displays list of all subroutes associated with a given route
 */

public class SubRouteActivity extends BaseActivity {

    // UI
    private SubRouteAdapter mSubRouteAdapter;
    private RecyclerView mRecyclerView;

    // Business Logic
    private String[] subroutes;
    private String routeName;
    private String routeNo;
    private Object isSubRoute = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_routes);
        activityContext = getBaseContext();
        getTransitInfo();
        setupToolbar(routeNo + " " + routeName);
        setUpRecyclerView(); // shows list of sub routes
        Toast.makeText(activityContext, "Long press on routes to add to favourites", Toast.LENGTH_LONG).show();
    }

    private void getTransitInfo() {
        Intent intent = getIntent();
        subroutes = intent.getStringArrayExtra("SUB_ROUTES");
        routeName = intent.getStringExtra("ROUTE_NAME");
        routeNo = intent.getStringExtra("ROUTE_NO");
    }

    private void setUpRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rvAllRoutes);
        mSubRouteAdapter = new SubRouteAdapter(activityContext, subroutes);
        mRecyclerView.setAdapter(mSubRouteAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activityContext));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(activityContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public String[] getAllRoutes() {
        return DBHelper.getInstance(activityContext).getAllRoutes();
    }
}