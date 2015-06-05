package tbrown.com.woodbuffalotransitmockup;

/**
 * Created by tmast_000 on 4/4/2015.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import tbrown.com.woodbuffalotransitmockup.adapters.AllRoutesAdapter;
import tbrown.com.woodbuffalotransitmockup.database.DBHelper;
import tbrown.com.woodbuffalotransitmockup.util.SimpleDividerItemDecoration;

/**
 * Created by tmast_000 on 4/4/2015.
 */

public class TripPlannerTab extends Fragment {

    private Activity activityContext;
    private WebView webViewTripPlanner;
    private DBHelper dbHelper;

    private static final String urlGoogleTripPlanner =  "https://www.google.ca/maps";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // called when ..
        activityContext = getActivity();
        //setupDatabase(activityContext);
        View layout = inflater.inflate(R.layout.tab_trip_planner, container, false);
        webViewTripPlanner = (WebView) layout.findViewById(R.id.wvTripPlanner);
        webViewTripPlanner.setWebViewClient(new WebViewClient() {
/*                                                @Override
                                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                                    boolean check = super.shouldOverrideUrlLoading(view, url);
                                                    return false;
                                                }

                                                @Override
                                                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                                                    handler.proceed(); // Ignore SSL certificate errors
                                                }*/

            @Override
            public void onReceivedSslError(final WebView view, final SslErrorHandler handler, SslError error) {
                Log.d("CHECK", "onReceivedSslError");
                AlertDialog.Builder builder = new AlertDialog.Builder(activityContext);
                AlertDialog alertDialog = builder.create();
                String message = "Certificate error.";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        message = "The certificate is not yet valid.";
                        break;
                }
                message += " Do you want to continue anyway?";
                alertDialog.setTitle("SSL Certificate Error");
                alertDialog.setMessage(message);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("CHECK", "Button ok pressed");
                        // Ignore SSL certificate errors
                        handler.proceed();
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("CHECK", "Button cancel pressed");
                        handler.cancel();
                    }
                });
                alertDialog.show();
            }
        });
        webViewTripPlanner.loadUrl("https://www.google.ca/maps/@56.7589956,-111.4606412,18z");
        return layout;                                  }
}


