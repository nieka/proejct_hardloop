package controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import interfaces.NetworkReqeust;
/**
 * Created by NHo on 25-9-2015.
 * Deze class word gebruikt om netwerk reqeust te doen
 */
public class ApiConnector {

    private final static String URL = "http://student.aii.avans.nl/ict/nfjhoffm/htdocs/";
    private NetworkReqeust callback;
    private RequestQueue queue;

    public ApiConnector(Context context, NetworkReqeust networkReqeust){
        this.callback = networkReqeust;
        this.queue = Volley.newRequestQueue(context);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static Boolean hasWifiConnection(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            }
        }
        return false;
    }

    public void getReqeust(String urlpart, final String tag){
        // Request a string response from the provided URL.
        String url = URL + urlpart;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("succe snetwork reqeust " + tag);
                        callback.onSucces(response, tag);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error network reqeust " + tag);
                callback.onError(error, tag);
            }
        });

        queue.add(stringRequest);
    }

    public void postReqeust(String urlpart, final String body, final String tag){
        String url = URL + urlpart;
        // Request a string response from the provided URL.
        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSucces(response,tag);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error,tag);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("data",body);

                return params;
            }
        };
        queue.add(sr);
    }
}
