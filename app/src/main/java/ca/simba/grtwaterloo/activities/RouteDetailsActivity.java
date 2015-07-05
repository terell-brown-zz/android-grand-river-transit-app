package ca.simba.grtwaterloo.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import ca.simba.grtwaterloo.Constants;
import ca.simba.grtwaterloo.R;
import ca.simba.grtwaterloo.slidingtabs.SchedulePageAdapter;
import ca.simba.grtwaterloo.slidingtabs.SlidingTabLayout;

/**
 * Displays list of stops on selected route in tabbed view
 * Slide between tabs to access stops going in different directions
 */
public class RouteDetailsActivity extends BaseActivity {

    // Backend Components
    private Context activityContext;

    // UI
    private Toolbar toolbar;
    private ViewPager pager;
    private SchedulePageAdapter adapter;
    private SlidingTabLayout tabs;
    private String[] Titles = {Constants.INBOUND, Constants.OUTBOUND};
    private int Numboftabs;

    // Business Logic
    private String routeInfo;
    private String routeNo;
    private boolean iSubRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContext = getBaseContext();
        setContentView(R.layout.activity_route_details);
        getRouteInfo();
        setupToolbar();
        setupViewPager();
        setupTabs();
        Toast.makeText(getBaseContext(), "Long press on stops to add to favourites", Toast.LENGTH_SHORT).show();
    }

    private void getRouteInfo() {
        Intent intent = getIntent();
        routeInfo = intent.getStringExtra("ROUTE_INFO");
        routeNo = intent.getStringExtra("ROUTE_NO");
        iSubRoute = intent.getBooleanExtra("IS_SUBROUTE",false);
        Numboftabs = intent.getIntExtra(Constants.NUM_DIRECTIONS,1);
    }

    public void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setTitle(getToolbarTitle());
        toolbar.setTitleTextColor(getResources().getColor(R.color.ColorToolbarTitle));
    }

    private String getToolbarTitle() {
        if (routeInfo.substring(0,1).equals("0")) { // some strings will have 0 as first character
            return routeInfo.substring(1,routeInfo.length());
        } else {
            return routeInfo;
        }
    }

    private void setupViewPager() {
        // Creates an adapter that will connect to the ViewPager Container in order to
        //   supply page fragmenents on demand
        adapter = new SchedulePageAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        // Creates a View Pager which acts as dynamic view container.
        //   Depending on the selected tab, a different fragment will be supplied to this area of the screen
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
    }

    private void setupTabs() {
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor); // sets colors when action applied to tab
            }
        });
        tabs.setViewPager(pager);

        if (Numboftabs == 1) {
            tabs.setVisibility(View.GONE);
        }
    }
}
