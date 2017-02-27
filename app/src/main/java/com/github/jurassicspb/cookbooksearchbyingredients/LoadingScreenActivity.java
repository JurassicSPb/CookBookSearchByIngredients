package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Мария on 12.01.2017.
 */

public class LoadingScreenActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getResources().getBoolean(R.bool.portrait_for_phones)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        Intent mainIntent = new Intent(LoadingScreenActivity.this,IngedientTablayoutActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
