package ca.simba.grtwaterloo.slidingtabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Provides an interface where contents of screen can be changed using tabs
 */
public class SchedulePageAdapter extends FragmentStatePagerAdapter {

    // UI
    private CharSequence mTitles[];
    private int mNumbOfTabs;

    public SchedulePageAdapter(FragmentManager fm, CharSequence titles[], int numTabs) {
        super(fm);
        this.mTitles = titles;
        this.mNumbOfTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        // returns UI fragment according to tab selected
        Fragment tab;
        switch (position) {
            case 0:
                tab = new InboundTab();
                break;
            default:
                tab = new OutboundTab();
                break;
        }
        return tab;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mNumbOfTabs;
    }
}



