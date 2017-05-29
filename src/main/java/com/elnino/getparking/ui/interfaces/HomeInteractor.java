package com.elnino.getparking.ui.interfaces;

import com.elnino.getparking.models.Kickstarter;

import java.util.List;

/**
 * Created by jeetupal on 28/05/17.
 */

public interface HomeInteractor {

  interface ApiCallbacks{
    void sucessCallback( List<Kickstarter> kickstarterList);
    void failurecallback( String msg);
  }

  void fetchKickStarterApi(String url);
}
