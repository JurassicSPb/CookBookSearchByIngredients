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
    private static final String KEY_RECIPES_V_1_1 = "key_recipes_v_1_1";
    private static final String KEY_RECIPES_V_1_3 = "key_recipes_v_1_3";
    private static final String KEY_RECIPES_V_1_4 = "key_recipes_v_1_4";
    private static final String KEY_RECIPES_V_1_5 = "key_recipes_v_1_5";
    private static final String KEY_INGR_AND_CAT_V_1_6 = "key_ingr_and_cat_v_1_6";
    private static final String KEY_INGR_FAV_V_1_6 = "key_ingr_fav_v_1_6";
    private static final String KEY_RECIPES_V_1_6 = "key_recipes_v_1_6";


    private SharedPreferences preferences;

    public MyPreferences(Context context) {
        preferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
    }

    public boolean getFlag() {
        return preferences.getBoolean(KEY_REALM_FLAG, true);
    }

    public void setFlag(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_REALM_FLAG, flag)
                .apply();
    }

    public void clearFlag() {
        preferences.edit().remove(KEY_REALM_FLAG).apply();
    }

    public boolean getFlagAlert() {
        return preferences.getBoolean(KEY_FLAG_ALERT, true);
    }

    public void setFlagAlert(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_FLAG_ALERT, flag)
                .apply();
    }

    public boolean getFlagRating() {
        return preferences.getBoolean(KEY_FLAG_RATING, true);
    }

    public void setFlagRating(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_FLAG_RATING, flag)
                .apply();
    }

    public void clearPrefs() {
        preferences.edit().clear().apply();
    }

    public void setBufferedImage(String images) {
        preferences.edit()
                .putString(KEY_BUFFER_IMAGE, images)
                .apply();
    }

    public String getBufferedIngredients() {
        return preferences.getString(KEY_BUFFER_INGR, "");
    }

    public void setBufferedIngredients(String ingredients) {
        preferences.edit()
                .putString(KEY_BUFFER_INGR, ingredients)
                .apply();
    }

    public String getBufferedImages() {
        return preferences.getString(KEY_BUFFER_IMAGE, "0");
    }

    public boolean getBufferedFlag() {
        return preferences.getBoolean(KEY_BUFFER_FLAG, true);
    }

    public void setBufferedFlag(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_BUFFER_FLAG, flag)
                .apply();
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

    public boolean getFlagRecipesV1_3() {
        return preferences.getBoolean(KEY_RECIPES_V_1_3, true);
    }

    public void setFlagRecipesV1_3(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_1_3, flag)
                .apply();
    }

    public boolean getFlagRecipesV1_4() {
        return preferences.getBoolean(KEY_RECIPES_V_1_4, true);
    }

    public void setFlagRecipesV1_4(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_1_4, flag)
                .apply();
    }

    public boolean getFlagRecipesV1_5() {
        return preferences.getBoolean(KEY_RECIPES_V_1_5, true);
    }

    public void setFlagRecipesV1_5(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_1_5, flag)
                .apply();
    }

    public boolean getFlagIngrFavV1_6() {
        return preferences.getBoolean(KEY_INGR_FAV_V_1_6, true);
    }

    public void setFlagIngrFavV1_6(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_INGR_FAV_V_1_6, flag)
                .apply();
    }

    public boolean getFlagIngrCatV1_6() {
        return preferences.getBoolean(KEY_INGR_AND_CAT_V_1_6, true);
    }

    public void setFlagIngrCatV1_6(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_INGR_AND_CAT_V_1_6, flag)
                .apply();
    }

    public boolean getFlagRecipesV1_6() {
        return preferences.getBoolean(KEY_RECIPES_V_1_6, true);
    }

    public void setFlagRecipesV1_6(boolean flag) {
        preferences.edit()
                .putBoolean(KEY_RECIPES_V_1_6, flag)
                .apply();
    }

}
