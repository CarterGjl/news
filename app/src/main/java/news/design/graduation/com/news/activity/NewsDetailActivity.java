package news.design.graduation.com.news.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebBackForwardList;
import android.webkit.WebSettings;
import android.webkit.WebView;

import news.design.graduation.com.news.R;

public class NewsDetailActivity extends AppCompatActivity {

    private static final String TAG = "NewsDetailActivity";
    private WebView mWebView;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        getInfo();
        initView();
    }

    private void getInfo() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        Log.d(TAG, "onCreate: "+ mUrl);
    }

    private void initView() {
        mWebView = findViewById(R.id.wv_view);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDefaultTextEncodingName("utf-8");
        mWebView.loadUrl(mUrl);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mWebView.goBack();
    }
}
