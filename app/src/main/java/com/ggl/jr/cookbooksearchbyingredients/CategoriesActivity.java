package com.ggl.jr.cookbooksearchbyingredients;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

import com.ggl.jr.cookbooksearchbyingredients.storage.IngredientDatabase;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

/**
 * Created by Мария on 14.02.2017.
 */

public class CategoriesActivity extends AppCompatActivity{
    private IngredientDatabase categoriesDB;
    private List<Categories> categories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.categories_gridview);

        GridView gridview = (GridView) findViewById(R.id.gridview);

        categoriesDB = new IngredientDatabase();
        performCategories();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Metrics.smallestWidth()>600) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_tablets);
        } else {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_phones);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle(R.string.categories);

        CategoriesAdapter adapter = new CategoriesAdapter(this, categories);
        gridview.setAdapter(adapter);

        AdView mAdView = (AdView) findViewById(R.id.adFragment);
        AdRequest adRequest = new AdRequest.Builder()
//                 s3
                .addTestDevice("67F276A8D2BC2AF79DDA7E1FD3FCC12D")
//                 tablet
//                .addTestDevice("BCCA97?C08B759F6F304C2665B7233097")
//                 a5
//                .addTestDevice("E0FC7B9C15DCFF71E2D006CAB7808184")
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        gridview.setOnItemClickListener((parent, view, position, id) -> {
            String name = categories.get((int)id).getName();
            Intent intent = new Intent(CategoriesActivity.this, CategoriesListActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
        });

    }
    private void performCategories(){
        categories = categoriesDB.getAllCategories();
    }

    @Override
    protected void onDestroy() {
        categoriesDB.close();
        super.onDestroy();
    }
}
