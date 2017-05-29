package com.elnino.getparking.ui.interfaces;

import com.elnino.getparking.models.Kickstarter;

import java.util.List;

/**
 * Created by jeetupal on 28/05/17.
 */

public interface HomeView {
  void showProgress();
  void hideProgress();
  void showToast(int msg);
  void showToast(String message);
  void setDataToAdapter( List<Kickstarter> kickstarterList);
//  void setToolBarData( Flights flights);
}
