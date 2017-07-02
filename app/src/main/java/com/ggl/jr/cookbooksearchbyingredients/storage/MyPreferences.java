package com.ggl.jr.cookbooksearchbyingredients.storage;

/**
 * Created by Мария on 03.12.2016.
 */

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferences {
    private static final String KEY_REALM_FLAG = "key_realm_flag";
    private static final String KEY_FLAG_ALERT = "key_flag_alert";
    private static final String KEY_FLAG_RATING = "key_flag_rating";
    private static final String KEY_BUFFER_INGR = "ingrBuffer";
    private static final String KEY_BUFFER_IMAGE = "imageBuffer";
    private static final String KEY_BUFFER_FLAG = "flagBuffer";
    private static final String KEY_INGR_CAT_V_1_1 = "key_ingr_cat_v_1_1";
    private static final String KEY_INGR_CAT_V_1_3 = "key_ingr_cat_v_1_3";
    private static final String KEY_RECIPES_V_1_1 = "key_recipes_v_1_1";
    private static final String KEY_INGR_FAV_V_1_2 = "key_ingr_fav_v_1_2";
    private static final String KEY_INGR_FAV_V_1_3 = "key_ingr_fav_v_1_3";
    private static final String KEY_RECIPES_V_1_3 = "key_recipes_v_1_3";

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

    public void setFlagRating(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_FLAG_RATING, flag)
                .apply();
    }

    public boolean getFlag() {
        return preferences.getBoolean(KEY_REALM_FLAG, true);
    }

    public void clearFlag() {
        preferences.edit().remove(KEY_REALM_FLAG).apply();
    }

    public boolean getFlagAlert() {
        return preferences.getBoolean(KEY_FLAG_ALERT, true);
    }

    public boolean getFlagRating() {
        return preferences.getBoolean(KEY_FLAG_RATING, true);
    }

    public void clearPrefs() {
        preferences.edit().clear().apply();
    }

    public void setBufferedIngredients(String ingredients) {
        preferences.edit()
                .putString(KEY_BUFFER_INGR, ingredients)
                .apply();
    }

    public void setBufferedImage(String images) {
        preferences.edit()
                .putString(KEY_BUFFER_IMAGE, images)
                .apply();
    }

    public void setBufferedFlag(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_BUFFER_FLAG, flag)
                .apply();
    }

    public String getBufferedIngredients() {
        return preferences.getString(KEY_BUFFER_INGR, "");
    }

    public String getBufferedImages() {
        return preferences.getString(KEY_BUFFER_IMAGE, "0");
    }

    public boolean getBufferedFlag() {
        return preferences.getBoolean(KEY_BUFFER_FLAG, true);
    }

    public void clearBufferedIngredients() {
        preferences.edit().remove(KEY_BUFFER_INGR).apply();
    }

    public void clearBufferedImage() {
        preferences.edit().remove(KEY_BUFFER_IMAGE).apply();
    }

    public boolean getFlagRecipesV1_1() {
        return preferences.getBoolean(KEY_RECIPES_V_1_1, true);
    }

    public void setFlagRecipesV1_1(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_1_1, flag)
                .apply();
    }

    public boolean getFlagIngrCatV1_1() {
        return preferences.getBoolean(KEY_INGR_CAT_V_1_1, true);
    }

    public void setFlagIngrCatV1_1(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_INGR_CAT_V_1_1, flag)
                .apply();
    }

    public boolean getFlagIngrFavV1_2() {
        return preferences.getBoolean(KEY_INGR_FAV_V_1_2, true);
    }

    public void setFlagIngrFavV1_2(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_INGR_FAV_V_1_2, flag)
                .apply();
    }

    public boolean getFlagIngrFavV1_3() {
        return preferences.getBoolean(KEY_INGR_FAV_V_1_3, true);
    }

    public void setFlagIngrFavV1_3(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_INGR_FAV_V_1_3, flag)
                .apply();
    }

    public boolean getFlagIngrCatV1_3() {
        return preferences.getBoolean(KEY_INGR_CAT_V_1_3, true);
    }

    public void setFlagIngrCatV1_3(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_INGR_CAT_V_1_3, flag)
                .apply();
    }

    public boolean getFlagRecipesV1_3() {
        return preferences.getBoolean(KEY_RECIPES_V_1_3, true);
    }

    public void setFlagRecipesV1_3(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_1_3, flag)
                .apply();
    }
}
