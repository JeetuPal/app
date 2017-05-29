package com.elnino.getparking.ui.activities.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elnino.getparking.R;
import com.elnino.getparking.config.api.ApiHelper;
import com.elnino.getparking.models.Kickstarter;
import com.elnino.getparking.ui.activities.BaseActivity;
import com.elnino.getparking.ui.adapters.HomeAdapter;
import com.elnino.getparking.ui.interfaces.HomePresenter;
import com.elnino.getparking.ui.interfaces.HomeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements HomeView, View.OnClickListener {


  @BindView(R.id.toolbar) AppBarLayout toolbar;
  @BindView(R.id.progressBar) ProgressBar progressBar;
  @BindView(R.id.project_recyclerView) RecyclerView myRecyclerView;
  @BindView(R.id.activity_main) RelativeLayout activityMain;
  @BindView(R.id.tv_toolbar_title) TextView tvToolbarTitle;
  @BindView(R.id.img_sort) ImageView imgSort;
  @BindView(R.id.img_filter) ImageView imgFilter;
  HomePresenter presenter;
  HomeAdapter adapter;
  LinearLayoutManager linearLayoutManager;
  List<Kickstarter> kickstarterList = new ArrayList<Kickstarter>();;
  private boolean exit = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    ButterKnife.bind(this);
    initialiseViewAndListner();
    presenter = new HomePresenterImp(this);
    presenter.getKickStarterInfo(ApiHelper.getKickstarterLink());
  }

  private void initialiseViewAndListner() {
    linearLayoutManager = new LinearLayoutManager(this);
    myRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new HomeAdapter(this, kickstarterList);
        myRecyclerView.setAdapter(adapter);
    imgSort.setOnClickListener(this);
    imgFilter.setOnClickListener(this);
  }

  @Override
  public void showProgress() {
    progressBar.setVisibility(View.VISIBLE);
    myRecyclerView.setVisibility(View.GONE);
  }
  @Override
  public void hideProgress() {
    progressBar.setVisibility(View.GONE);
    myRecyclerView.setVisibility(View.VISIBLE);
  }
  @Override
  public void showToast(int msg) {
    Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
  }
  @Override
  public void showToast(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
  @Override
  public void setDataToAdapter(List<Kickstarter> list) {
    kickstarterList.addAll(list);
//    adapter = new HomeAdapter(this, list);
//    myRecyclerView.setAdapter(adapter);
    adapter.notifyDataSetChanged();
  }

  @Override
  public void onBackPressed() {
    //    super.onBackPressed();
    if (exit) {
      finish();
    } else {
      exit = true;
      Toast.makeText(this, getString(R.string.press_back_exit), Toast.LENGTH_SHORT).show();
      new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
          exit = false;
        }
      }, 3 * 1000);
    }
  }
  @Override
  public void onClick(View v) {
    switch (v.getId()){
      case R.id.img_sort:{
        Collections.sort(kickstarterList, new Comparator<Kickstarter>() {
          @Override
          public int compare(Kickstarter o1, Kickstarter o2) {
            return o1.getTitle().compareTo(o2.getTitle());
          }
        });
        adapter.notifyDataSetChanged();
        break;
      }
      case R.id.img_filter:{
        /**
         * s.no 54 contains strin in no.backers : "Cambridge, MA"
         * s.no 60 "num.backers": "New York, NY",
         */
        Collections.sort(kickstarterList, new Comparator<Kickstarter>() {
          @Override
          public int compare(Kickstarter o1, Kickstarter o2) {
            Integer v1 = null;
            Integer v2 = null;
            try {
              v1 = Integer.parseInt(o1.getNumBackers());
              v2 = Integer.parseInt(o2.getNumBackers());
              return v1.compareTo(v2);
            } catch (NumberFormatException e) {
              e.printStackTrace();
              return 0;
            }
          }
        });
        adapter.notifyDataSetChanged();
        break;
      }
    }
  }
}
