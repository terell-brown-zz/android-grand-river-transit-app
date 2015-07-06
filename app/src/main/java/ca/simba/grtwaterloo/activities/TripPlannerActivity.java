package ca.simba.grtwaterloo.activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import ca.simba.grtwaterloo.Constants;
import ca.simba.grtwaterloo.R;
import ca.simba.grtwaterloo.util.Utilities;

/*
 * Displays embedded view of google maps trip planner website
 */

public class TripPlannerActivity extends BaseActivity {

    // UI
    private WebView webViewTripPlanner;
    private ProgressBar progressBar;

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
        runProgressBar();
    }

    private void runProgressBar() {
        progressBar = (ProgressBar) findViewById(R.id.pbLoading);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupWebView();
    }

    private void setupWebView() {
        webViewTripPlanner = (WebView) findViewById(R.id.wvTripPlanner);
        if (Utilities.haveNetworkConnection(getApplicationContext())) {
            webViewTripPlanner.setWebViewClient(new GoogleMapsWebClient());
            webViewTripPlanner.getSettings().setJavaScriptEnabled(true);
            webViewTripPlanner.loadUrl(URL);
            webViewTripPlanner.setVisibility(View.INVISIBLE);
        } else {
            TextView myTV = (TextView) findViewById(R.id.tvInternetDisabled);
            myTV.setVisibility(View.VISIBLE);
            webViewTripPlanner.setVisibility(View.GONE);
        }
    }

    public class GoogleMapsWebClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            webViewTripPlanner.setVisibility(View.VISIBLE);
        }
    }
}


