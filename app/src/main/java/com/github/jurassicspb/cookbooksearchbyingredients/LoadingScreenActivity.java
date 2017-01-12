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
    private final int WAIT_TIME = 4000;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.loading_screen);
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            progressBar.setVisibility(ProgressBar.VISIBLE);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(LoadingScreenActivity.this,IngedientTablayoutActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, WAIT_TIME);
    }
}
