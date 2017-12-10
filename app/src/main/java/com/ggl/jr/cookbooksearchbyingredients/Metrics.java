package com.ggl.jr.cookbooksearchbyingredients;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by Мария on 26.05.2017.
 */

public class Metrics {

    public static float smallestWidth() {
        final DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        float scaleFactor = metrics.density;
        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;
        return Math.min(widthDp, heightDp);
    }
}
