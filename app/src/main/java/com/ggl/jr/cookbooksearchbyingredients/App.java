package com.ggl.jr.cookbooksearchbyingredients;
import android.app.Application;
import io.realm.Realm;

/**
 * Created by Мария on 04.11.2016.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
