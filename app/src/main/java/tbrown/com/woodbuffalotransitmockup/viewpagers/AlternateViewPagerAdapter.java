package tbrown.com.woodbuffalotransitmockup.viewpagers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import tbrown.com.woodbuffalotransitmockup.AllTab;
import tbrown.com.woodbuffalotransitmockup.FavouritesTab;
import tbrown.com.woodbuffalotransitmockup.NearbyTab;
import tbrown.com.woodbuffalotransitmockup.TripPlannerTab;

/**
 * Created by tmast_000 on 4/4/2015.
 */
public class AlternateViewPagerAdapter extends FragmentStatePagerAdapter {

    // View Pager Adapter used for the alternative main activity.
    // Contains 2 tabs including a trip explorer and a full bus schedule

    CharSequence Titles[]; // This will store the titles of the tabs which are going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public AlternateViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }


    @Override
    public Fragment getItem(int tabPosition) {
        // returns a fragment to be displayed in ViewPager based on tab positioning
        switch(tabPosition) {
            case 1:
                AllTab allTab = new AllTab();
                return allTab;
            case 2:
                TripPlannerTab plannerTab = new TripPlannerTab();
                return plannerTab;
            default:
                TripPlannerTab defaultTab = new TripPlannerTab();
                return defaultTab;
        }
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}



