package com.elnino.getparking.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.elnino.getparking.R;
import com.elnino.getparking.config.VariableConstants;
import com.elnino.getparking.ui.views.MyWebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectDetailWebActivity extends AppCompatActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.help_webview) WebView helpWebview;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_project_detail_web);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    try {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setTitle(getString(R.string.app_name));
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (getIntent().getExtras() != null){
      String url = getIntent().getExtras().getString(VariableConstants.INTENT_PROJECT_URL);
      WebSettings webSettings = helpWebview.getSettings();
      webSettings.setJavaScriptEnabled(true);
      MyWebViewClient webViewClient = new MyWebViewClient(this);
      helpWebview.setWebViewClient(webViewClient);
      helpWebview.loadUrl(url);
    }

  }

  /**
   *  Handle navigation between multiples pages in the same WebView.
   * @param keyCode
   * @param event
   * @return
   */
  public boolean onKeyDown(final int keyCode, final KeyEvent event) {
    if ((keyCode == KeyEvent.KEYCODE_BACK) && helpWebview.canGoBack()) {
      helpWebview.goBack();
      //If there is history, then the canGoBack method will return ‘true’//
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home: {
        finish();
      }
    }
    return super.onOptionsItemSelected(item);
  }
}
