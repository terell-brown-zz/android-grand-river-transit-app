package tbrown.com.woodbuffalotransitmockup;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import tbrown.com.woodbuffalotransitmockup.database.DBHelper;
import tbrown.com.woodbuffalotransitmockup.navigation_drawer.ActionBarDrawerToggler;
import tbrown.com.woodbuffalotransitmockup.navigation_drawer.OnNavItemClickListener;


public class Activity_Base extends ActionBarActivity {
    // base activity for all top-level activities in app (ie. it's a superclass)

    protected Context activityContext;
    protected DBHelper dbHelper;
    protected Toolbar toolbar;

    protected DrawerLayout mDrawerLayout;
    protected ListView mDrawerList;
    protected String[] mDrawerListItems;
    protected android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    protected AdapterView.OnItemClickListener drawerListener;


    protected SharedPreferences favourites;
    protected String sharedPrefs = "My Favourite Stops and Routes";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    protected void setupToolbar(String title) {
        // Creating the Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        getSupportActionBar().setTitle("");
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.ColorToolbarTitle));
        toolbar.setLogo(R.drawable.ic_bus);
    }

    protected void setupNavDrawer(int navId) {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerList = (ListView) findViewById(R.id.drawerList);
        mDrawerListItems = getResources().getStringArray(R.array.drawer_navigation);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDrawerListItems));
        drawerListener = new OnNavItemClickListener(activityContext, navId, mDrawerLayout, mDrawerList);
        mDrawerList.setOnItemClickListener(drawerListener);
        mDrawerToggle = new ActionBarDrawerToggler(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    protected void setupDatabase(Context activityContext) {
        dbHelper = new DBHelper(activityContext);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState(); // ensures open/closed state matches what's shown on screen
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // called when screen changes orientation
        super.onConfigurationChanged(newConfig);
        // ensures state of drawer remains same on orientation change
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
