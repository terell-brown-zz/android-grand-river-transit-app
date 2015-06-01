package tbrown.com.woodbuffalotransitmockup;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tbrown.com.woodbuffalotransitmockup.adapters.StopAdapter;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;

/**
 * Created by tmast_000 on 4/4/2015.
 */

public class FavouritesTab extends Fragment {

    private  Context activityContext;
    private StopAdapter mFaveAdapter;
    private RecyclerView mRecyclerView;

    static SharedPreferences favourites;
    static String sharedPrefs = "My Favourite Stops and Routes";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tab_favourites,container,false);
        setupRecyclerView(layout);
        return layout;
    }

    private void setupRecyclerView(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rvFavouriteStops);
        mFaveAdapter = new StopAdapter(activityContext,getData());
        mRecyclerView.setAdapter(mFaveAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activityContext));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(activityContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public String[] getData() {
        //String[] data = {"Stops", "Powder Drive Station","King Kunta Station","DreamVille", "Forest Hill Drive","Cole Station",
        //"Routes", "1 Timberlea Express","2 Thickwood Express","12 Downtown Express"};
        favourites = getActivity().getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= favourites.edit();
        return getFavourites();
    }

    public String[] getFavourites() {
        Map<String,?> copiedPrefs = favourites.getAll();
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

        String [] favesArray = favesList.toArray(new String[favesList.size()]);
        return favesArray;
        }

    private boolean isRoute(String key) {
        String sub = key.substring(0, 7);
        return sub.equals("Route -");
    }


    private void addStopToFavouritesArray(Object value) {
    }

    private boolean isStop(String key) {
        String sub = key.substring(0,6);
        boolean ans = sub.equals("Stop -");

        return ans;
    }





}
