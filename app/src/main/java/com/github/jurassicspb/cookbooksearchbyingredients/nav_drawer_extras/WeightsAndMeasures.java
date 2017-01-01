package com.github.jurassicspb.cookbooksearchbyingredients.nav_drawer_extras;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.github.jurassicspb.cookbooksearchbyingredients.R;
import java.util.Locale;



/**
 * Created by Мария on 01.01.2017.
 */

public class WeightsAndMeasures extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weights_and_mesures_rus);

        if (Locale.getDefault().getLanguage().equals("ru")) {
            setContentView(R.layout.weights_and_mesures_rus);
        }
        else{
            setContentView(R.layout.weights_and_mesures_eng);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle(R.string.weights);

    }
}
