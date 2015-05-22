package tbrown.com.woodbuffalotransitmockup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by tmast_000 on 4/4/2015.
 */

public class NearbyTab extends Fragment implements View.OnClickListener {

    Button searchButton;
    ProgressBar spinner;
    TextView tvFeatureComing;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_nearby,container,false);
        searchButton = (Button) v.findViewById(R.id.bSearch);
        spinner = (ProgressBar) v.findViewById(R.id.pbSpinner);
        tvFeatureComing = (TextView) v.findViewById(R.id.tvFeatureText);
        searchButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSearch:
                spinner.setVisibility(View.VISIBLE);
                tvFeatureComing.setVisibility(View.VISIBLE);
                break;
            default:
                spinner.setVisibility(View.VISIBLE);
                tvFeatureComing.setVisibility(View.VISIBLE);
                break;
        }

    }
}
