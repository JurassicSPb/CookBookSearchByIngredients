package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

/**
 * Created by Мария on 12.01.2017.
 */

public class LoadingScreenActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent mainIntent = new Intent(LoadingScreenActivity.this,IngedientTablayoutActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
