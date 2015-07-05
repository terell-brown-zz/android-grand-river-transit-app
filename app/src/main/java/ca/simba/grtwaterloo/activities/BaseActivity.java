package ca.simba.grtwaterloo.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ca.simba.grtwaterloo.InitialLaunch;
import ca.simba.grtwaterloo.R;
import ca.simba.grtwaterloo.database.DBHelper;
import ca.simba.grtwaterloo.navigationdrawer.ActionBarDrawerToggler;
import ca.simba.grtwaterloo.navigationdrawer.OnNavItemClickListener;

/*
 * Base activity to be extended by all top level activities
 */

public class BaseActivity extends ActionBarActivity {

    // Backend Components
    protected Context activityContext;
    protected DBHelper dbHelper;

    // UI
    protected Toolbar toolbar;
    protected DrawerLayout mDrawerLayout;
    protected ListView mDrawerList;
    protected String[] mDrawerListItems;
    protected android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    protected AdapterView.OnItemClickListener drawerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupToolbar(String title) {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.ColorToolbarTitle));
    }

    protected void setupNavDrawer(int navId) {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // setup item list in nav drawer
        mDrawerList = (ListView) findViewById(R.id.drawerList);
        mDrawerListItems = getResources().getStringArray(R.array.drawer_navigation);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDrawerListItems));

        drawerListener = new OnNavItemClickListener(activityContext, navId, mDrawerLayout, mDrawerList);
        mDrawerList.setOnItemClickListener(drawerListener);

        // toggles drawer open/close
        mDrawerToggle = new ActionBarDrawerToggler(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    protected void setupDatabase(Context activityContext) {
        if (dbHelper == null) {
            dbHelper = DBHelper.getInstance(activityContext);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        mDrawerToggle.syncState(); // ensures open/closed state matches what's shown on screen
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
        // inflates the menu
        getMenuInflater().inflate(R.menu.menu_activity_base, menu);
        return true;
    }
}
