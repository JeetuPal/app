package com.elnino.getparking.libs.network;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

import timber.log.Timber;

/**
 * Created by jeetupal on 28/05/17.
 */
public class StringRequestWrapper extends StringRequest {
    String url;
    private Priority priority = Priority.NORMAL;
    String deviceId, session, vcode;

    Map headers, params;

    public StringRequestWrapper(int method, String url, Map<String, String> inParam, Map<String, String> header, Request.Priority priority,
                                Response.Listener<String> listener,
                                Response.ErrorListener errorListener ) {
        super(method, url, listener, errorListener);

        this.headers = header;
        this.params = inParam;
        init();
    }

    void init() {
        setShouldCache(false);
        setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }

    protected void deliverResponse(String response) {
        try {
            Timber.e( getMethod() + getUrl()+ getHeaders() + getParams() + response+ getBodyContentType());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        boolean flag = false;
        if(!flag) {
            super.deliverResponse(response);
        }
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setPriorityForRequest(Priority inPriority) {
        priority = inPriority;
    }

}
