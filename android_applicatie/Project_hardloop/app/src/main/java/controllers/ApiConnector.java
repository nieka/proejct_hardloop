package controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by NHo on 25-9-2015.
 * This classes is used to send a http post or get request to a server
 */
public class ApiConnector {

    /*
    De benodigde permissions in androidManifest:
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
        <uses-permission android:name="android.permission.INTERNET" />
     */

    OkHttpClient client = new OkHttpClient();

    MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final static String URL = "http://192.168.40.11:99/";
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public String getMethode(String url) throws IOException {
        Request request = new Request.Builder()
                .url(URL + url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }



    public String postMethode(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        System.out.println(URL + url);
        Request request = new Request.Builder()
                .url(URL + url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
