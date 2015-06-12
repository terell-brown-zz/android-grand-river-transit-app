package tbrown.com.woodbuffalotransitmockup;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tbrown.com.woodbuffalotransitmockup.adapters.StopAdapter;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;


public class Activity_Main extends Activity_Base {
    // When using Appcompat support library you need to extend Main Activity to ActionBarActivity.

    private StopAdapter mFaveAdapter;
    private RecyclerView mRecyclerView;

    private final int NAV_ID = 1; // postion of activity in navigation drawer
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        activityContext = getBaseContext();
        setupRecyclerView();
        setupToolbar("Woosh Mobile");
        setupNavDrawer(NAV_ID);
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rvFavouriteStops);
        mFaveAdapter = new StopAdapter(activityContext, getData());
        mRecyclerView.setAdapter(mFaveAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activityContext));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(activityContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public String[] getData() {
        favourites = activityContext.getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = favourites.edit();
        return getFavourites();
    }

    public String[] getFavourites() {
        Map<String, ?> copiedPrefs = favourites.getAll();
        List<String> favesList = new ArrayList<String>();
        favesList.add("Stops");
        for (Map.Entry<String, ?> entry : copiedPrefs.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (isStop(key)) {
                favesList.add(key.substring(6));
            }
        }

        favesList.add("Routes");
        for (Map.Entry<String, ?> entry : copiedPrefs.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (isRoute(key)) {
                favesList.add(key.substring(7));
            }
        }

        String[] favesArray = favesList.toArray(new String[favesList.size()]);
        return favesArray;
    }

    private boolean isRoute(String key) {
        String sub = key.substring(0, 7);
        return sub.equals("Route -");
    }


    private void addStopToFavouritesArray(Object value) {
    }

    private boolean isStop(String key) {
        String sub = key.substring(0, 6);
        boolean ans = sub.equals("Stop -");

        return ans;
    }

}
