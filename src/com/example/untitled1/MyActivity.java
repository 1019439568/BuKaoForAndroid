package com.example.untitled1;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.*;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private WebView bukao_webview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.main);
        bukao_webview = (WebView)findViewById(R.id.bukao_webview);

        WebSettings settings = bukao_webview.getSettings();
        settings.setJavaScriptEnabled(true);    //启用JS脚本
        settings.setAppCacheEnabled(true); //启动缓存
        settings.setDomStorageEnabled(true); //开启DOM存储Api

        BuKaoSearch();

        bukao_webview.setWebViewClient(new WebViewClient() {
            //当点击链接时,希望覆盖而不是打开新窗口
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);  //加载新的url
                return true;    //返回true,代表事件已处理,事件流到此终止
            }
        });

        //点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
        bukao_webview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && bukao_webview.canGoBack()) {
                        bukao_webview.goBack();   //后退
                        return true;    //已处理
                    }
                }
                return false;
            }
        });

        bukao_webview.setWebChromeClient(new WebChromeClient() {
            //当WebView进度改变时更新窗口进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //Activity的进度范围在0到10000之间,所以这里要乘以100
                MyActivity.this.setProgress(newProgress * 100);
            }
        });

        // 如果不设置这个，JS代码中的按钮会显示，但是按下去却不弹出对话框
        // Sets the chrome handler. This is an implementation of WebChromeClient
        // for use in handling JavaScript dialogs, favicons, titles, and the
        // progress. This will replace the current handler.
        bukao_webview.setWebChromeClient(new WebChromeClient()
        {

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result)
            {
                // TODO Auto-generated method stub
                return super.onJsAlert(view, url, message, result);
            }

        });


    }

    private void BuKaoSearch(){
        String bukao_url = "file:///android_asset/index.html";
        bukao_webview.loadUrl(bukao_url);  //加载url
        bukao_webview.requestFocus(); //获取焦点
        bukao_webview.requestFocusFromTouch() ;
    }
}
