package com.github.jurassicspb.cookbooksearchbyingredients.storage;

/**
 * Created by Мария on 03.12.2016.
 */

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferences {
    private static final String KEY_REALM_FLAG = "key_realm_flag";
    private static final String KEY_FLAG_ALERT = "key_flag_alert";

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
}
