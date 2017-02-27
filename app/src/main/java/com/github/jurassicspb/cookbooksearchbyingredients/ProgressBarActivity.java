package com.github.jurassicspb.cookbooksearchbyingredients;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

/**
 * Created by Мария on 15.01.2017.
 */

public class ProgressBarActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.progress_bar);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(android.widget.ProgressBar.VISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.progressBar), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
    @Override
    public void onBackPressed() {}
}
