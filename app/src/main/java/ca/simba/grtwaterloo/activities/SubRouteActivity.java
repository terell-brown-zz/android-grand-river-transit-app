package ca.simba.grtwaterloo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import ca.simba.grtwaterloo.InitialLaunch;
import ca.simba.grtwaterloo.R;
import ca.simba.grtwaterloo.adapters.SubRouteAdapter;
import ca.simba.grtwaterloo.database.DBHelper;
import ca.simba.grtwaterloo.util.SimpleDividerItemDecoration;

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
        showHelpMessage();
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

    private void showHelpMessage() {
        if (InitialLaunch.check()) {
            Toast.makeText(getBaseContext(), "Long press on stops to add to favourites", Toast.LENGTH_SHORT).show();
        }
    }
}
