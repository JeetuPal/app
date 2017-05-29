package com.elnino.getparking.config;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.elnino.getparking.BuildConfig;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import timber.log.Timber;

public class AppController extends MultiDexApplication {
    public static final String TAG = AppController.class.getName();
    private RequestQueue mRequestQueue;
    private static AppController mInstance;
    private RefWatcher refWatcher;

    /**
     * @param context
     */
    public static RefWatcher getRefWatcher(Context context) {
        AppController baseApplication = (AppController) context.getApplicationContext();
        return baseApplication.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //        leakcanary installation
        refWatcher = LeakCanary.install(this);
        //        volley setup
        mInstance = this;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        //        Timber plant insetion
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            //      plant a tree for release falvour
        }
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public <T> void add(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancel() {
        mRequestQueue.cancelAll(TAG);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
