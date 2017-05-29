package com.elnino.getparking.ui.activities.home;

import android.content.Context;

import com.elnino.getparking.libs.utils.Utility;
import com.elnino.getparking.models.Kickstarter;
import com.elnino.getparking.ui.interfaces.HomeInteractor;
import com.elnino.getparking.ui.interfaces.HomePresenter;
import com.elnino.getparking.ui.interfaces.HomeView;

import java.util.List;

/**
 * Created by jeetupal on 28/05/17.
 */

public class HomePresenterImp implements HomePresenter, HomeInteractor.ApiCallbacks {

  HomeView homeView;
  HomeInteractor homeInteractor;

  public HomePresenterImp(HomeView homeView) {
    this.homeView = homeView;
    this.homeInteractor = new HomeInteractorImp(this);
  }
  @Override
  public void getKickStarterInfo(String url) {
    if (Utility.isNetworkAvailable((Context) homeView)){
      homeView.showProgress();
      homeInteractor.fetchKickStarterApi(url);
    }

  }
  @Override
  public void sucessCallback(List<Kickstarter> kickstarterList) {
    homeView.setDataToAdapter(kickstarterList);
    homeView.hideProgress();
  }
  @Override
  public void failurecallback(String msg) {
    homeView.hideProgress();
  }
}
