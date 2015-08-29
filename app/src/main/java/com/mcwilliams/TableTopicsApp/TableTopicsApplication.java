package com.mcwilliams.TableTopicsApp;

import android.app.Application;

import com.mcwilliams.TableTopicsApp.utils.DatabaseHandler;

/**
 * Created by joshuamcwilliams on 8/28/15.
 */
public class TableTopicsApplication extends Application {
    public static DatabaseHandler db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = new DatabaseHandler(this);
    }
}
