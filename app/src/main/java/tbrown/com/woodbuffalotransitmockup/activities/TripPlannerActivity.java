package tbrown.com.woodbuffalotransitmockup.activities;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import tbrown.com.woodbuffalotransitmockup.Constants;
import tbrown.com.woodbuffalotransitmockup.R;

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
        setupWebView();
    }

    private void setupWebView() {
        webViewTripPlanner = (WebView) findViewById(R.id.wvTripPlanner);
        webViewTripPlanner.setWebViewClient(new WebViewClient());
        webViewTripPlanner.getSettings().setJavaScriptEnabled(true);
        webViewTripPlanner.loadUrl(URL);
    }
}

