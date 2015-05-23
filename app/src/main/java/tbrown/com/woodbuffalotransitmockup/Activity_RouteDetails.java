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
        activityContext = this;
        setupDatabase(activityContext);
        setContentView(R.layout.activity_route_details);
        // Extract route info from intent
        getRouteInfo();
        // Creating the Toolbar and setting it as the Toolbar for the activity
        setupToolbar();
        setupRecyclerView();

    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rvRouteDetails);
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
            return dbHelper.getStopsByRoute(routeNo);

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
