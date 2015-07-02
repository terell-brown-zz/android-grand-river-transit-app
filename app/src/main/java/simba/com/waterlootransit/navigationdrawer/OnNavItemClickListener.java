package simba.com.waterlootransit.navigationdrawer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import simba.com.waterlootransit.Constants;

/**
 * Created by tmast_000 on 6/5/2015.
 */
public class OnNavItemClickListener implements AdapterView.OnItemClickListener {

    // Backend Components
    private Context activityContext;

    // UI
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    // Business Logic
    private int NAV_DRAWER_POSITION;

    public OnNavItemClickListener(Context activityContext,int navDrawerPosition,DrawerLayout dl,ListView lv) {
        this.activityContext = activityContext;
        this.NAV_DRAWER_POSITION = navDrawerPosition;
        this.mDrawerLayout = dl;
        this.mDrawerList = lv;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (NAV_DRAWER_POSITION == position) {
        } else {

            switch (position) {
                case Constants.FAVOURITES:
                    activityContext.startActivity(
                            new Intent("simba.com.waterlootransit.FAVOURITES")
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    break;
                case Constants.NEARBY:
                    activityContext.startActivity(
                            new Intent("simba.com.waterlootransit.NEARBY")
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    break;
                case Constants.PLANNER:
                    activityContext.startActivity(
                            new Intent("simba.com.waterlootransit.PLANNER")
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    break;
                case Constants.ALL_ROUTES:
                    activityContext.startActivity(
                            new Intent("simba.com.waterlootransit.ALL_ROUTES")
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }
        mDrawerLayout.closeDrawer(mDrawerList);
    }
}
