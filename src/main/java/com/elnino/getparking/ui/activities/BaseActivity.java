package com.elnino.getparking.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.elnino.getparking.config.AppController;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by jeetupal on 28/05/17.
 */
public class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    RefWatcher refWatcher = AppController.getRefWatcher(this);
    refWatcher.watch(this);
  }
}
