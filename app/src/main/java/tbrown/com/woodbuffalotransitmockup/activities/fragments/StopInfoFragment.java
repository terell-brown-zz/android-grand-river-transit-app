package tbrown.com.woodbuffalotransitmockup.activities.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tbrown.com.woodbuffalotransitmockup.R;
import tbrown.com.woodbuffalotransitmockup.adapters.StopInfoAdapter;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;

/**
 * Created by tmast_000 on 5/29/2015.
 */
public class StopInfoFragment extends DialogFragment {

    Context activityContext;
    RecyclerView rvStopInfo;
    private StopInfoAdapter adapter;
    private String[] routes;
    private String[][] upcomingStopTimes;


    public static DialogFragment StopInfoFragment() {
        StopInfoFragment dialog = new StopInfoFragment();
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContext = getActivity();

        Bundle args = getArguments();
        if (args != null) {
            routes = args.getStringArray("ROUTES");
            upcomingStopTimes = (String[][]) args.getSerializable("TIMES");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.dialogue_stop_info, container, false);
        setupRecyclerView(layout);
        return layout;
    }

    private void setupRecyclerView(View v) {
        rvStopInfo = (RecyclerView) v.findViewById(R.id.rvStopInfo);
        //adapter = new StopInfoAdapter(activityContext,upcomingStopTimes);
        rvStopInfo.setAdapter(adapter);
        rvStopInfo.setHasFixedSize(true);
        rvStopInfo.setLayoutManager(new LinearLayoutManager(activityContext));
        rvStopInfo.addItemDecoration(new SimpleDividerItemDecoration(activityContext));
        rvStopInfo.setItemAnimator(new DefaultItemAnimator());
    }

}
