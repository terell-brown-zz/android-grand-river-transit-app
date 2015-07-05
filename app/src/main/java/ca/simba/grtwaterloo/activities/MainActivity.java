package ca.simba.grtwaterloo.activities;


import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ca.simba.grtwaterloo.Constants;
import ca.simba.grtwaterloo.Favourites;
import ca.simba.grtwaterloo.InitialLaunch;
import ca.simba.grtwaterloo.R;
import ca.simba.grtwaterloo.adapters.StopAdapter;
import ca.simba.grtwaterloo.util.FavouritesUtil;
import ca.simba.grtwaterloo.util.SimpleDividerItemDecoration;


public class MainActivity extends BaseActivity {

    // Favourites List
    private StopAdapter mFaveAdapter;
    private RecyclerView mRecyclerView;

    // Constants
    private static final String TOOLBAR_TITLE = Constants.TITLE_FAVOURITES;
    private final int NAV_ID = Constants.FAVOURITES;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContext = getBaseContext();
        InitialLaunch.onInitialLaunch(activityContext);
        setContentView(R.layout.activity_favourites);
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
