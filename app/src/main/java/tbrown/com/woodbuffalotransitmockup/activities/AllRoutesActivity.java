package tbrown.com.woodbuffalotransitmockup.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import tbrown.com.woodbuffalotransitmockup.Constants;
import tbrown.com.woodbuffalotransitmockup.R;
import tbrown.com.woodbuffalotransitmockup.adapters.AllRoutesAdapter;
import tbrown.com.woodbuffalotransitmockup.database.DBHelper;
import tbrown.com.woodbuffalotransitmockup.util.DBUtils;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;

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
