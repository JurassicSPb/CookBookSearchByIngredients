package com.github.jurassicspb.cookbooksearchbyingredients.storage;

/**
 * Created by Мария on 03.12.2016.
 */

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferences {
    private static final String KEY_REALM_FLAG = "key_realm_flag";
    private static final String KEY_FLAG_ALERT = "key_flag_alert";
    private static final String KEY_FAV_INGR = "ingrBuffer";
    private static final String KEY_FAV_IMAGE = "imageBuffer";

    private SharedPreferences preferences;

    public MyPreferences(Context context) {
        preferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
    }

    public void setFlag(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_REALM_FLAG, flag)
                .apply();
    }
    public void setFlagAlert(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_FLAG_ALERT, flag)
                .apply();
    }

    public boolean getFlag() {
        return preferences.getBoolean(KEY_REALM_FLAG, true);
    }

    public boolean getFlagAlert() {
        return preferences.getBoolean(KEY_FLAG_ALERT, true);
    }


    public void clearPrefs() {
        preferences.edit().clear().apply();
    }

    public void setBufferedIngredients(String ingredients) {
        preferences.edit()
                .putString(KEY_FAV_INGR, ingredients)
                .apply();
    }

    public void setBufferedImage(String images) {
        preferences.edit()
                .putString(KEY_FAV_IMAGE, images)
                .apply();
    }

    public String getBufferedIngredients() {
        return preferences.getString(KEY_FAV_INGR, "");
    }

    public String getBufferedImages() {
        return preferences.getString(KEY_FAV_IMAGE, "0");
    }

    public void clearBufferedIngredients() {
        preferences.edit().remove(KEY_FAV_INGR).apply();
    }

    public void clearBufferedImage() {
        preferences.edit().remove(KEY_FAV_IMAGE).apply();
    }
}
