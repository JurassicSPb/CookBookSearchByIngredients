package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;

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
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle(R.string.categories);

        CategoriesAdapter adapter = new CategoriesAdapter(this, categories);
        gridview.setAdapter(adapter);

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
