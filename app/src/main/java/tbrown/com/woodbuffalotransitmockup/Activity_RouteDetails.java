package tbrown.com.woodbuffalotransitmockup;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import tbrown.com.woodbuffalotransitmockup.adapters.SchedulePageAdapter;
import tbrown.com.woodbuffalotransitmockup.adapters.StopAdapter;
import tbrown.com.woodbuffalotransitmockup.adapters.StopsByRouteAdapter;
import tbrown.com.woodbuffalotransitmockup.database.DBHelper;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;
import tbrown.com.woodbuffalotransitmockup.widgets.SlidingTabLayout;

/**
 * Created by tmast_000 on 4/17/2015.
 */
public class Activity_RouteDetails extends ActionBarActivity {
    // When using Appcombat support library you need to extend Main Activity to ActionBarActivity.

    private Toolbar toolbar;                              // Declaring the Toolbar Object

    private MenuItem faveSelected;                        // Declare favourite button in toolbar
    private MenuItem faveUnSelected;
    private Context activityContext;
    private StopsByRouteAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private DBHelper dbHelper;
    String routeInfo;
    int routeNo;

    private SchedulePageAdapter adapter;
    private String[] Titles = {"To Downton","To Uptown"};
    private int Numboftabs = 2;
    private ViewPager pager;
    private SlidingTabLayout tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContext = this;
        setupDatabase(activityContext);
        setContentView(R.layout.activity_route_details);
        // Extract route info from intent
        getRouteInfo();
        // Creating the Toolbar and setting it as the Toolbar for the activity
        setupToolbar();
        //setupRecyclerView();
        setupViewPager();
        setupTabs();
    }

    private void setupRecyclerView() {
        //mRecyclerView = (RecyclerView) findViewById(R.id.rvRouteDetails);
        mAdapter = new StopsByRouteAdapter(activityContext,routeInfo,routeNo,getStopsByRoute(routeNo));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activityContext));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(activityContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setupToolbar() {
        // Creating the Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        getSupportActionBar().setTitle("");

        if (routeInfo.substring(0,1).equals("0")) {
            toolbar.setTitle(routeInfo.substring(1,routeInfo.length()));
        } else {
            toolbar.setTitle(routeInfo);
        }
        toolbar.setTitleTextColor(getResources().getColor(R.color.ColorToolbarTitle));
        //toolbar.setLogo(R.drawable.ic_bus);
    }

    private void setupDatabase(Context activityContext) {
        dbHelper = new DBHelper(activityContext);
    }

    private void setupViewPager() {
        // Creating an adapter that will connect to the ViewPager Container in order to
        //   supply page fragmenents on demand
        adapter = new SchedulePageAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        // Creating a View Pager which acts as dynamic view container.
        //   Depending on the current tab, a different fragment will be supplied to this area of the screen
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

    }

    private void setupTabs() {
        // Implementing a tab bar below the tool bar, that can slide
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        tabs.setViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_route_details, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        faveSelected = menu.findItem(R.id.action_favourite_selected);
        faveUnSelected = menu.findItem(R.id.action_favourite_unselected);

        faveSelected.setVisible(false);
        faveUnSelected.setVisible(true);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_favourite_selected:
                toggleFavourites();
                break;
            case R.id.action_favourite_unselected:
                toggleFavourites();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleFavourites() {
        // Toggle the favourites icon (star) located in the tool bar between
        // selected and unselected

        boolean isSelected = faveSelected.isVisible();
        faveSelected.setVisible(!isSelected);
        faveUnSelected.setVisible(isSelected);
    }

    private void getRouteInfo() {
        // Extracts the route information from the intent provided
        routeInfo = getIntent().getStringExtra("ROUTE_INFO");
        routeNo = getIntent().getIntExtra("ROUTE_NO",400);
    }

    private String[] getStopsByRoute(int routeNo) {
            return dbHelper.getStopsByRoute(routeNo,0);

//        String[] result = null;
//        int i = 0;
//        for (int j = 0;j < stopsByRoute.length; j++) {
//            if (stopsByRoute[j][i].equals(routeInfo)) {
//                result = Arrays.copyOfRange(stopsByRoute[j], 1, stopsByRoute[j].length);
//            }
//        }
//        return result;
    }

}
