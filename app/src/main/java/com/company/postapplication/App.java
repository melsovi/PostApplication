package com.company.postapplication;

import android.app.Application;

import com.company.postapplication.data.remote.PostApi;
import com.company.postapplication.data.remote.PostApiClient;
import com.company.postapplication.data.remote.RetrofitClient;

public class App extends Application {

    private RetrofitClient retrofitClient;
    public static PostApi api;
    public static PostApiClient postApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofitClient = new RetrofitClient();
        api = retrofitClient.createApi();
        postApiClient = new PostApiClient(api);
    }
}
