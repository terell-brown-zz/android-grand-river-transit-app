package tbrown.com.woodbuffalotransitmockup.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import tbrown.com.woodbuffalotransitmockup.Constants;
import tbrown.com.woodbuffalotransitmockup.R;
import tbrown.com.woodbuffalotransitmockup.util.Utilities;

/*
    Displays embedded view of google maps trip planner website
 */

public class TripPlannerActivity extends BaseActivity {

    // UI
    private WebView webViewTripPlanner;

    // Constants
    private static final String TOOLBAR_TITLE = Constants.TITLE_PLANNER;
    private static final int NAV_ID = Constants.PLANNER;
    private static final String URL = Constants.urlGoogleTripPlanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);
        activityContext = getBaseContext();
        setupToolbar(TOOLBAR_TITLE);
        setupNavDrawer(NAV_ID);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setupWebView();
    }

    private void setupWebView() {
        webViewTripPlanner = (WebView) findViewById(R.id.wvTripPlanner);
        if (Utilities.haveNetworkConnection(getApplicationContext())) {
            webViewTripPlanner.setWebViewClient(new WebViewClient());
            webViewTripPlanner.getSettings().setJavaScriptEnabled(true);
            webViewTripPlanner.loadUrl(URL);
        } else {
            TextView myTV = (TextView) findViewById(R.id.tvInternetDisabled);
            myTV.setVisibility(View.VISIBLE);
            webViewTripPlanner.setVisibility(View.GONE);
        }
    }
}


