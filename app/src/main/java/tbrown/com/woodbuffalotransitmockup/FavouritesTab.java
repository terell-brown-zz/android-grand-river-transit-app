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
import tbrown.com.woodbuffalotransitmockup.datamodels.StopOverview;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;

/**
 * Created by tmast_000 on 4/4/2015.
 */

public class FavouritesTab extends Fragment {

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

    public static List<StopOverview> getData() {
        List<StopOverview> data = new ArrayList<>();

        data.add(new StopOverview("Powder Drive Station"));
        data.add(new StopOverview("Kunta Station"));
        data.add(new StopOverview("DreamVille Station"));
        data.add(new StopOverview("Louis Street @ Forest Hill Drive"));

        return data;
    }
}
