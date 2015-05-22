package tbrown.com.woodbuffalotransitmockup;

/**
 * Created by tmast_000 on 4/4/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tbrown.com.woodbuffalotransitmockup.adapters.AllRoutesAdapter;
import tbrown.com.woodbuffalotransitmockup.adapters.StopAdapter;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;

/**
 * Created by tmast_000 on 4/4/2015.
 */

public class AllTab extends Fragment {

    private AllRoutesAdapter mAllRoutesAdapter;
    private RecyclerView nRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tab_all, container, false);
        nRecyclerView = (RecyclerView) layout.findViewById(R.id.rvAllRoutes);
        mAllRoutesAdapter = new AllRoutesAdapter(getActivity(), getData());
        nRecyclerView.setAdapter(mAllRoutesAdapter);
        nRecyclerView.setHasFixedSize(true);
        nRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        nRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        nRecyclerView.setItemAnimator(new DefaultItemAnimator());
        return layout;
    }

    public static String[] getData() {
        String[] allRoutes = {"1 Timberlea Express", "2 Thickwood Express", "3 Morgan Heights",
                "7 Abasand Heights", "11 Airport Shuttle", "12 Timberlea / Thickwood Local",
                "13 Heritage Hills", "14 Taiga Nova", "31 Timberlea", "32 Timberlea", "41 Stoney Creek - Eagle Ridge",
                "42 Eagle Ridge - Stoney Creek", "51 Wood Buffalo", "61 Thickwood", "62 Thickwood", "8 Beacon Hill",
                "91 Downtown East", "92 Downtown West", "99 MacDonald Island", "10A Gregoire/Industrial", "10B Gregoire/Industrial",
                "0 Saprae Creek Estates", "0 Industrial A", "0 Industrial B"};

    return allRoutes;
    }
}


