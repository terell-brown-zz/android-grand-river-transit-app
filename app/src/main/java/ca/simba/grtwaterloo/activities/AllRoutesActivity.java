package ca.simba.grtwaterloo.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import ca.simba.grtwaterloo.Constants;
import ca.simba.grtwaterloo.R;
import ca.simba.grtwaterloo.adapters.AllRoutesAdapter;
import ca.simba.grtwaterloo.database.DBHelper;
import ca.simba.grtwaterloo.util.SimpleDividerItemDecoration;

/*
 * Displays list of all routes
 */

public class AllRoutesActivity extends BaseActivity {

    // UI
    private AllRoutesAdapter mAllRoutesAdapter;
    private RecyclerView mRecyclerView;

    // Constants
    private static final String TOOLBAR_TITLE = Constants.TITLE_ALL_ROUTES;
    private static final int NAV_ID = Constants.ALL_ROUTES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_routes);
        activityContext = getBaseContext();
        setupToolbar(TOOLBAR_TITLE);
        setUpRecyclerView(); // shows list of routes
        setupNavDrawer(NAV_ID);
        Toast.makeText(activityContext, "Long press on routes to add to favourites", Toast.LENGTH_SHORT).show();
    }

    private void setUpRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rvAllRoutes);
        mAllRoutesAdapter = new AllRoutesAdapter(activityContext, getAllRoutes());
        mRecyclerView.setAdapter(mAllRoutesAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activityContext));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(activityContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public String[] getAllRoutes() {
        DBHelper dbHelper = new DBHelper(activityContext);
        return dbHelper.getAllRoutes();
    }
}
