package ca.simba.grtwaterloo.navigationdrawer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ca.simba.grtwaterloo.Constants;

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

    public OnNavItemClickListener(Context context,int navDrawerPosition,DrawerLayout dl,ListView lv) {
        activityContext = context;
        NAV_DRAWER_POSITION = navDrawerPosition;
        mDrawerLayout = dl;
        mDrawerList = lv;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (NAV_DRAWER_POSITION == position) {

        } else {
            switch (position) {
                case Constants.FAVOURITES:
                    activityContext.startActivity(
                            new Intent("ca.simba.grtwaterloo.activities.FAVOURITES")
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    break;
                case Constants.NEARBY:
                    activityContext.startActivity(
                            new Intent("ca.simba.grtwaterloo.activities.NEARBY")
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    break;
                case Constants.PLANNER:
                    activityContext.startActivity(
                            new Intent("ca.simba.grtwaterloo.activities.PLANNER")
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    break;
                case Constants.ALL_ROUTES:
                    activityContext.startActivity(
                            new Intent("ca.simba.grtwaterloo.activities.ALL_ROUTES")
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }
        mDrawerLayout.closeDrawer(mDrawerList);
    }

}
