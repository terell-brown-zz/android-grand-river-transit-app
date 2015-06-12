package tbrown.com.woodbuffalotransitmockup.navigation_drawer;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import static android.support.v4.app.ActivityCompat.invalidateOptionsMenu;



public class ActionBarDrawerToggler extends ActionBarDrawerToggle {

    protected Activity mActivity;

    public ActionBarDrawerToggler(Activity activity, DrawerLayout drawerLayout, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        mActivity = activity;
    }

    public ActionBarDrawerToggler(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        mActivity = activity;
    }

    @Override
    public void onDrawerClosed(View v) {
        super.onDrawerClosed(v);
        invalidateOptionsMenu(mActivity);
        syncState();
    }

    @Override
    public void onDrawerOpened(View v) {
        super.onDrawerOpened(v);
        invalidateOptionsMenu(mActivity);
        syncState();
    }
}
