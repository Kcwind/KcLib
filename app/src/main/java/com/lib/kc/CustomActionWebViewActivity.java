package com.lib.kc;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.lib.kc.mylibrary.utils.ClipboardUtils;
import com.lib.kc.mylibrary.view.webview.ActionSelectListener;
import com.lib.kc.mylibrary.view.webview.CustomActionWebView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kechao on 2017/7/19.
 */

public class CustomActionWebViewActivity extends AppCompatActivity {

    View mLadingView;
    CustomActionWebView mCustomActionWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_action_webview);

        mLadingView = findViewById(R.id.loadingView);
        mCustomActionWebView = (CustomActionWebView)findViewById(R.id.customActionWebView);


        List<String> list = new ArrayList<>();
        list.add("Item1");
        list.add("Item2");
        list.add("APIWeb");

        mCustomActionWebView.setWebViewClient(new CustomWebViewClient());

        //设置item
        mCustomActionWebView.setActionList(list);

        //链接js注入接口，使能选中返回数据
        mCustomActionWebView.linkJSInterface();

        mCustomActionWebView.getSettings().setBuiltInZoomControls(true);
        mCustomActionWebView.getSettings().setDisplayZoomControls(false);
        //使用javascript
        mCustomActionWebView.getSettings().setJavaScriptEnabled(true);
        mCustomActionWebView.getSettings().setDomStorageEnabled(true);


        //增加点击回调
        mCustomActionWebView.setActionSelectListener(new ActionSelectListener() {
            @Override
            public void onClick(String title, String selectText) {
                if (title.equals("Item1")){
                    ClipboardUtils.copy(CustomActionWebViewActivity.this,selectText);
                }
                Toast.makeText(CustomActionWebViewActivity.this, "Click Item: " + title + "。\n\nValue: " + selectText, Toast.LENGTH_LONG).show();
            }
        });

        //加载url
        mCustomActionWebView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCustomActionWebView.loadUrl("http://www.jianshu.com/p/b32187d6e0ad");
            }
        }, 1000);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(mCustomActionWebView != null) {
            mCustomActionWebView.dismissAction();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private class CustomWebViewClient extends WebViewClient {

        private boolean mLastLoadFailed = false;

        @Override
        public void onPageFinished(WebView webView, String url) {
            super.onPageFinished(webView, url);
            if (!mLastLoadFailed) {
                CustomActionWebView customActionWebView = (CustomActionWebView) webView;
                customActionWebView.linkJSInterface();
                mLadingView.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageStarted(WebView webView, String url, Bitmap favicon) {
            super.onPageStarted(webView, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            mLastLoadFailed = true;
            mLadingView.setVisibility(View.GONE);
        }
    }
}
