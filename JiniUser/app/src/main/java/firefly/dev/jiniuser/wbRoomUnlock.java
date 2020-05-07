package firefly.dev.jiniuser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class wbRoomUnlock extends AppCompatActivity {

    private WebView wv1;
    ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wb_room_unlock);

        progressBar = new ProgressDialog(this);
        progressBar.setTitle("Loading");
        progressBar.setMessage("Wait while loading...");
        progressBar.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progressBar.show();

        wv1=(WebView)findViewById(R.id.webviewDoor);
        wv1.setWebViewClient(new chatBotFirefly.MyBrowser());
        wv1.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                setProgress(progress * 100); //Make the bar disappear after URL is loaded

                // Return the app name after finish loading
                if(progress == 100)
                { progressBar.dismiss();}
            }
        });
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl("https://kumaraguru7.github.io/checkin/");


    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(wbRoomUnlock.this,UserServices.class));
    }
}
