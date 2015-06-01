package tbrown.com.woodbuffalotransitmockup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;

import tbrown.com.woodbuffalotransitmockup.adapters.SchedulePageAdapter;
import tbrown.com.woodbuffalotransitmockup.adapters.StopTimesAdapter;
import tbrown.com.woodbuffalotransitmockup.adapters.StopsByRouteAdapter;
import tbrown.com.woodbuffalotransitmockup.database.DBHelper;
import tbrown.com.woodbuffalotransitmockup.database.DBUtils;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;
import tbrown.com.woodbuffalotransitmockup.widgets.SlidingTabLayout;

/**
 * Created by tmast_000 on 4/19/2015.
 */
public class Activity_StopDetails extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
    // When using Appcombat support library you need to extend Main Activity to ActionBarActivity.

    private Toolbar toolbar;                              // Declaring the Toolbar Object

    private MenuItem faveSelected;                        // Declare favourite button in toolbar
    private MenuItem faveUnSelected;
    private Context activityContext;
    private DBHelper dbHelper;
    private StopTimesAdapter mAdapter;
    private RecyclerView mRecyclerView;
    String routeInfo;
    String stopInfo;
    int routeNo;

    private ViewPager pager;
    SchedulePageAdapter adapter;
    private SlidingTabLayout tabs;
    CharSequence Titles[]={"To Uptown","To Downtown"};
    int Numboftabs = 1;

    private static final String WEEKDAYS_ALL = "'15SPRI-All-Weekday-01'";
    private static final String SATURDAY = "'15SPRI-All-Saturday-01'";
    private static final String SUNDAY = "'15SPRI-All-Sunday-01'";
    private static final String[] SERVICES = {WEEKDAYS_ALL,SATURDAY,SUNDAY};

    private Spinner spin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContext = getBaseContext();
        setContentView(R.layout.activity_stop_details);
        // Extract route info from intent
        getTransitInfo();
        setupDatabase(activityContext);
        // Creating the Toolbar and setting it as the Toolbar for the activity
        setupToolbar();
        setupViewPager();
        setupTabs();
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


    private void setupDatabase(Context activityContext) {
        dbHelper = new DBHelper(activityContext);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_stop_details, menu);
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

    private void toggleFavourites() {
        // Toggle the favourites icon (star) located in the tool bar between
        // selected and unselected

        boolean isSelected = faveSelected.isVisible();
        faveSelected.setVisible(!isSelected);
        faveUnSelected.setVisible(isSelected);
    }

    private void getTransitInfo() {
        // Extracts the route information from the intent provided
        routeInfo = getIntent().getStringExtra("ROUTE_INFO");
        routeNo = getIntent().getIntExtra("ROUTE_NO", 400);
        stopInfo = getIntent().getStringExtra("STOP_INFO");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String serviceId = SERVICES[parent.getSelectedItemPosition()];
        Intent intent = getIntent();
        intent.putExtra("SERVICE_ID",serviceId);
        finish();
        startActivity(intent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
