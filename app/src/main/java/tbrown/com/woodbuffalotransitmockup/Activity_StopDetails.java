package tbrown.com.woodbuffalotransitmockup;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;

import tbrown.com.woodbuffalotransitmockup.adapters.SchedulePageAdapter;
import tbrown.com.woodbuffalotransitmockup.adapters.StopTimesAdapter;
import tbrown.com.woodbuffalotransitmockup.adapters.StopsByRouteAdapter;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;
import tbrown.com.woodbuffalotransitmockup.widgets.SlidingTabLayout;

/**
 * Created by tmast_000 on 4/19/2015.
 */
public class Activity_StopDetails extends ActionBarActivity {
    // When using Appcombat support library you need to extend Main Activity to ActionBarActivity.

    private Toolbar toolbar;                              // Declaring the Toolbar Object

    private MenuItem faveSelected;                        // Declare favourite button in toolbar
    private MenuItem faveUnSelected;
    private Context mContext;
    private StopTimesAdapter mAdapter;
    private RecyclerView mRecyclerView;
    String routeInfo;
    String stopInfo;

    private ViewPager pager;
    SchedulePageAdapter adapter;
    private SlidingTabLayout tabs;
    CharSequence Titles[]={"To Uptown","To Downtown"};
    int Numboftabs = 2;


    static String[][] stopsByRoute = {
            {"1 Timberlea Express", "Main St. and Franklin Ave. Transfer Stn","Eagle Ridge Gate @ Louitit Road","Powder Station"},
            {"2 Thickwood Express","Main St. and Franklin Ave. Transfer Stn","Signal Rd. @ Thickwood Shopping Plaza","Westwood School" }, {"3 Morgan Heights","Test Stop"},
            {"7 Abasand Heights","Test Stop"}, {"11 Airport Shuttle","Test Stop"},{"12 Timberlea / Thickwood Local","Test Stop"},
            {"13 Heritage Hills","Test Stop"},{"14 Taiga Nova","Test Stop"},{"31 Timberlea","Test Stop"},{"32 Timberlea","Test Stop"},{"41 Stoney Creek - Eagle Ridge","Test Stop"},
            {"42 Eagle Ridge - Stoney Creek","Test Stop"}, {"51 Wood Buffalo","Test Stop"},{"61 Thickwood","Test Stop"},{"62 Thickwood","Test Stop"},{"8 Beacon Hill","Test Stop"},
            {"91 Downtown East","Test Stop"},{"92 Downtown West","Test Stop"},{"99 MacDonald Island","Test Stop"},{"10A Gregoire/Industrial","Test Stop"},{"10B Gregoire/Industrial","Test Stop"},
            {"0 Saprae Creek Estates","Test Stop"},{"0 Industrial A","Test Stop"},{"0 Industrial B","Test Stop"}};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getBaseContext();
        setContentView(R.layout.activity_stop_details);
        // Extract route info from intent
        getRouteInfo();
        getStopInfo();
        // Creating the Toolbar and setting it as the Toolbar for the activity
        setupToolbar();
        setupViewPager();
        setupTabs();
        //setupRecyclerView();

    }

    private void setupToolbar() {
        // Creating the Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        getSupportActionBar().setTitle("");
        toolbar.setTitle(stopInfo); toolbar.setTitleTextColor(getResources().getColor(R.color.ColorToolbarTitle));
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
        String r = routeInfo;
    }

    private void getStopInfo() {
        // Extracts the route information from the intent provided
        stopInfo = getIntent().getStringExtra("STOP_INFO");
        String r = stopInfo;
    }

    private static String[] getData(String routeInfo) {
        String[] times = {"6:30 AM","7:00 AM","7:30 AM","8:00 AM","8:30 AM","9:00 AM","9:30 AM",
                "10:00 AM","10:30 AM","11:00 AM","11:30 AM","12:00 PM","12:30 PM","1:00 PM","1:30 PM",
                "2:00 PM","2:30 PM","3:00 PM","3:30 PM","4:00 PM","4:30 PM","5:00 PM","5:30 PM"};
        return times;
    }
}
