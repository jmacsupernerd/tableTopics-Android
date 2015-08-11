package com.mcwilliams.TableTopicsApp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import com.mcwilliams.TableTopicsApp.R;
import com.mcwilliams.TableTopicsApp.activities.Home;
import com.mcwilliams.TableTopicsApp.activities.ManageTopics;

import java.util.Random;

/**
 * Created by jrclark on 1/27/15.
 */
public class Utils {

    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static int randInt(int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt(((max - 1) - 0) + 1) + 0;
        return randomNum;
    }

    public static void setupToolbar(AppCompatActivity activity){
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        final ActionBar ab = activity.getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public static DrawerLayout setupDrawer(AppCompatActivity activity, DrawerLayout mDrawerLayout){
        mDrawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView, mDrawerLayout, activity);
        }

        return mDrawerLayout;
    }

    private static void setupDrawerContent(NavigationView navigationView, final DrawerLayout mDrawerLayout, final AppCompatActivity activity) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()){
                            case R.id.nav_home:
                                Intent goHome = new Intent(activity, Home.class);
                                activity.startActivity(goHome);
                                break;
                            case R.id.nav_topics:
                                Intent goTopics = new Intent(activity, com.mcwilliams.TableTopicsApp.mdactivitiess.ManageTopics.class);
                                activity.startActivity(goTopics);
                                break;
                            case R.id.nav_people:
                                Intent goPeople = new Intent(activity, com.mcwilliams.TableTopicsApp.mdactivitiess.ManagePeople.class);
                                activity.startActivity(goPeople);
                                break;
                            default:
                                break;
                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
}
