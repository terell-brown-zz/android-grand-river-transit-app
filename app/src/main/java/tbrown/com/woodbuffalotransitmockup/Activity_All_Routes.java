package tbrown.com.woodbuffalotransitmockup;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import tbrown.com.woodbuffalotransitmockup.adapters.AllRoutesAdapter;
import tbrown.com.woodbuffalotransitmockup.database.DBHelper;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;


public class Activity_All_Routes extends Activity_Base {

    private static final int NAV_ID = 4 ;
    private AllRoutesAdapter mAllRoutesAdapter;
    private RecyclerView nRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        activityContext = getBaseContext();
        setupToolbar("Select A Route");
        setupDatabase(activityContext);
        setUpRecyclerView();
        setupNavDrawer(NAV_ID);

    }

    private void setUpRecyclerView() {
        nRecyclerView = (RecyclerView) findViewById(R.id.rvAllRoutes);
        mAllRoutesAdapter = new AllRoutesAdapter(activityContext, getAllRoutes());
        nRecyclerView.setAdapter(mAllRoutesAdapter);
        nRecyclerView.setHasFixedSize(true);
        nRecyclerView.setLayoutManager(new LinearLayoutManager(activityContext));
        nRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(activityContext));
        nRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public String[] getAllRoutes() {
        // Returns and String array containing all routes in the transit system
        return dbHelper.getAllRoutes();
    }

}
