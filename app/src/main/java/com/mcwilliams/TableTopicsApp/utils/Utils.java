package com.mcwilliams.TableTopicsApp.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by jrclark on 1/27/15.
 */
public class Utils {

    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
