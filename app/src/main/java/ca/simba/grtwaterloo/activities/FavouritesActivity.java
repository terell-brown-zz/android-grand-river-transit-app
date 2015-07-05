package ca.simba.grtwaterloo.activities;


import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import ca.simba.grtwaterloo.Constants;
import ca.simba.grtwaterloo.Favourites;
import ca.simba.grtwaterloo.R;
import ca.simba.grtwaterloo.adapters.StopAdapter;
import ca.simba.grtwaterloo.util.FavouritesUtil;
import ca.simba.grtwaterloo.util.SimpleDividerItemDecoration;

/*
 * Displays list of favourite stops and routes
 */

public class FavouritesActivity extends BaseActivity {

    // Favourites List
    private StopAdapter mFaveAdapter;
    private RecyclerView mRecyclerView;
    private String[] favourites;

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

    @Override
    protected void onStart() {
        super.onStart();
        if (favourites.length > 2) {
            // favourites list contains atleast one row of data (not including titles Stops and Routes)
            Toast.makeText(getBaseContext(), "Long press on stops or routes to remove from favourites", Toast.LENGTH_LONG).show();
        }
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
        favourites = FavouritesUtil.getFavouritesArray(Favourites.getInstance(activityContext));
        return favourites;
    }
}
