package com.mcwilliams.TableTopicsApp.utils;

import android.content.Context;
import android.util.DisplayMetrics;

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
}
