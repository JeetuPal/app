package com.elnino.getparking.ui.activities.home;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.elnino.getparking.config.AppController;
import com.elnino.getparking.libs.network.StringRequestWrapper;
import com.elnino.getparking.libs.utils.Utility;
import com.elnino.getparking.models.Kickstarter;
import com.elnino.getparking.ui.interfaces.HomeInteractor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static com.elnino.getparking.config.AppController.TAG;

/**
 * Created by jeetupal on 28/05/17.
 */

public class HomeInteractorImp implements HomeInteractor {

  private static final String TAG = "HomeInteractorImp";
  ApiCallbacks apiCallbacks;
  public HomeInteractorImp( ApiCallbacks apiCallbacks) {
    this.apiCallbacks = apiCallbacks;
  }

  @Override
  public void fetchKickStarterApi(String url) {

    Map headerMap = new HashMap();
    Map paramsMap = new HashMap();
    StringRequestWrapper requestWrapper = new StringRequestWrapper(Request.Method.GET, url, paramsMap, headerMap, Request.Priority.NORMAL, new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
        if (!Utility.isNullOrEmpty(response)) {
          Gson gson = new Gson();
          Type kickType = new TypeToken<ArrayList<Kickstarter>>() {}.getType();
          List<Kickstarter> kickstarterList = gson.fromJson(response, kickType);
          Log.e(TAG, "");
          apiCallbacks.sucessCallback(kickstarterList);
        } else {
          apiCallbacks.failurecallback(response);
        }
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        apiCallbacks.failurecallback(error.getMessage()+"");
      }
    });

    AppController.getInstance().getRequestQueue().add(requestWrapper);

  }
}
