package com.ggl.jr.cookbooksearchbyingredients;
import android.app.Application;

import com.google.android.gms.ads.MobileAds;

import io.realm.Realm;

/**
 * Created by Мария on 04.11.2016.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        MobileAds.initialize(this, "ca-app-pub-4416823947112112~6746877583");
    }
}
