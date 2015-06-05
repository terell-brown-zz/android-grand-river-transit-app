package tbrown.com.woodbuffalotransitmockup.viewpagers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import tbrown.com.woodbuffalotransitmockup.AllTab;
import tbrown.com.woodbuffalotransitmockup.FavouritesTab;
import tbrown.com.woodbuffalotransitmockup.NearbyTab;

/**
 * Created by tmast_000 on 4/4/2015.
 */
public class ViewPageAdapter extends FragmentStatePagerAdapter {
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPageAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                FavouritesTab faveTab = new FavouritesTab();
                return faveTab;
            case 1:
                NearbyTab nearbyTab = new NearbyTab();
                return nearbyTab;
            case 2:
                AllTab allTab = new AllTab();
                return allTab;
            default:
                FavouritesTab defaultTab = new FavouritesTab();
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



