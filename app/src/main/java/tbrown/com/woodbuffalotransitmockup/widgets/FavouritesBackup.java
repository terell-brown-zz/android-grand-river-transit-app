package tbrown.com.woodbuffalotransitmockup;


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

import tbrown.com.woodbuffalotransitmockup.adapters.StopAdapter;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;

/**
 * Created by tmast_000 on 4/4/2015.
 */

public class FavouritesBackup extends Fragment {
    String stopData;
    String routeData;
    private static int NumStops = 0;
    private static int NumRoutes = 0;

    private StopAdapter mStopAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemAnimator mItemAnimator;
    private RecyclerView.LayoutManager nLayoutManager;
    private RecyclerView.ItemAnimator nItemAnimator;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tab_favourites,container,false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rvFavouriteStops);
        mStopAdapter = new StopAdapter(getActivity(),getData());
        mRecyclerView.setAdapter(mStopAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        return layout;
    }

    public static String[] getData() {
        /*List<String> data = new ArrayList<>();

        data.add("Stops");
        data.add("Powder Drive Station");
        data.add("Kunta Station");
        data.add("DreamVille Station");
        data.add("Louis Street @ Forest Hill Drive");

        NumStops = data.size();

        data.add("Routes");
        data.add("1 - Timberlea Express");
        data.add("2 - Thickwood Express");
        data.add("99 - MacDonald Island");

        NumRoutes = data.size() - NumStops - 1;*/

        String[] data = {"Stops", "Powder Drive Station","King Kunta Station","DreamVille", "Forest Hill Drive","Cole Station",
                "Routes", "Timberlea Express","Thickwood Express","Downtown Express"};

        return data;

    }

    public int getNumStops() {
        return NumStops;
    }

    public int getNumRoutes() {
        return NumRoutes;
    }

}
