package com.mcwilliams.TableTopicsApp;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;
import com.mcwilliams.TableTopicsApp.utils.network.ParseKeyRequestInterceptor;
import com.squareup.okhttp.OkHttpClient;

import io.fabric.sdk.android.Fabric;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by joshuamcwilliams on 8/28/15.
 */
public class TableTopicsApplication extends Application {
    public static DatabaseHandler db;
    public static Retrofit retrofit;
    public static String DOMAIN_URL = "https://api.backendless.com/v1/";

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        db = new DatabaseHandler(this);

        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new ParseKeyRequestInterceptor());

        retrofit = new Retrofit.Builder()
                .baseUrl(DOMAIN_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
