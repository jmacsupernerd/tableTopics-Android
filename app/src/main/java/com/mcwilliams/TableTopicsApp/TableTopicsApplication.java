package com.mcwilliams.TableTopicsApp;

import android.app.Application;
import android.content.Context;

import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;
import com.mcwilliams.TableTopicsApp.utils.network.ParseKeyRequestInterceptor;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by joshuamcwilliams on 8/28/15.
 */
public class TableTopicsApplication extends Application {
    public static DatabaseHandler db;
    public static Retrofit retrofit;
    public static String DOMAIN_URL = "https://api.backendless.com/v1/";

    private static Context context;

    public static Context getAppContext() {
        return TableTopicsApplication.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db = new DatabaseHandler(this);

        TableTopicsApplication.context = getApplicationContext();

        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new ParseKeyRequestInterceptor());

        retrofit = new Retrofit.Builder()
                .baseUrl(DOMAIN_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
