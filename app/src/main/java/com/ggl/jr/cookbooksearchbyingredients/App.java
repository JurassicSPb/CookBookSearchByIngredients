package com.ggl.jr.cookbooksearchbyingredients;

import androidx.multidex.MultiDexApplication;

import com.google.android.gms.ads.MobileAds;

import io.realm.Realm;

/**
 * Created by Мария on 04.11.2016.
 */

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        MobileAds.initialize(this);
    }
}
