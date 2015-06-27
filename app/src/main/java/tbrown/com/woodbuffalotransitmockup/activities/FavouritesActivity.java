package tbrown.com.woodbuffalotransitmockup.activities;


import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import tbrown.com.woodbuffalotransitmockup.Constants;
import tbrown.com.woodbuffalotransitmockup.Favourites;
import tbrown.com.woodbuffalotransitmockup.R;
import tbrown.com.woodbuffalotransitmockup.adapters.StopAdapter;
import tbrown.com.woodbuffalotransitmockup.util.FavouritesUtil;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;

/*
 * Displays list of favourite stops and routes
 */

public class FavouritesActivity extends BaseActivity {

    // Favourites List
    private StopAdapter mFaveAdapter;
    private RecyclerView mRecyclerView;

    // Constants
    private static final String TOOLBAR_TITLE = Constants.TITLE_FAVOURITES;
    private final int NAV_ID = Constants.FAVOURITES;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        activityContext = getBaseContext();
        setupRecyclerView();
        setupToolbar(TOOLBAR_TITLE);
        setupNavDrawer(NAV_ID);
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rvFavouriteStops);
        mFaveAdapter = new StopAdapter(activityContext, getFavourites(),true);
        mRecyclerView.setAdapter(mFaveAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activityContext));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(activityContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public String[] getFavourites() {
        // returns array of all favourited stops and routes
        return FavouritesUtil.getFavouritesArray(Favourites.getInstance(activityContext));
    }
}
