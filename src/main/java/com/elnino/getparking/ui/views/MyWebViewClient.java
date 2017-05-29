package com.elnino.getparking.ui.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {

  Context context;
  ProgressDialog pd;

  public MyWebViewClient(Context context) {
    this.context = context;
  }

  /**
   * Implement shouldOverrideUrlLoading
   * @param view
   * @param url
   * @return
   */
  @Override
       public boolean shouldOverrideUrlLoading(WebView view, String url) {
           if(Uri.parse(url).getHost().endsWith("kickstarter.com")) {
               return false;
            }
          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
          view.getContext().startActivity(intent);
            return true;
        }

  @Override
  public void onPageStarted(WebView view, String url, Bitmap favicon) {
     pd = ProgressDialog.show(context, "", "Please wait ...", true);
    pd.setCanceledOnTouchOutside(true);
  }

  @Override
  public void onPageFinished(WebView view, String url) {
    pd.dismiss();
  }
}