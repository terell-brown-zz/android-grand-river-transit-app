package tbrown.com.woodbuffalotransitmockup.navigation_drawer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by tmast_000 on 6/5/2015.
 */
public class OnNavItemClickListener implements AdapterView.OnItemClickListener {


    private Context activityContext;
    private final int NAV_DRAWER_POSITION;

    DrawerLayout mDrawerLayout;
    ListView mDrawerList;

    public OnNavItemClickListener(Context activityContext,int navDrawerPosition,DrawerLayout dl,ListView lv) {
        this.activityContext = activityContext;
        this.NAV_DRAWER_POSITION = navDrawerPosition;
        this.mDrawerLayout = dl;
        this.mDrawerList = lv;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (NAV_DRAWER_POSITION == position + 1) {

        } else {

            switch (position + 1) {
                case 1:
                    activityContext.startActivity(
                            new Intent("tbrown.com.woodbuffalotransitmockup.FAVOURITES")
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    break;
                case 2:
                    activityContext.startActivity(
                            new Intent("tbrown.com.woodbuffalotransitmockup.NEARBY")
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    break;
                case 3:
                    activityContext.startActivity(
                            new Intent("tbrown.com.woodbuffalotransitmockup.PLANNER")
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    break;
                case 4:
                    activityContext.startActivity(
                            new Intent("tbrown.com.woodbuffalotransitmockup.ALL_ROUTES")
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }
        mDrawerLayout.closeDrawer(mDrawerList);
    }
}
