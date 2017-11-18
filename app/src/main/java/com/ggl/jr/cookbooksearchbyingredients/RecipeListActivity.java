package com.ggl.jr.cookbooksearchbyingredients;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ggl.jr.cookbooksearchbyingredients.storage.IngredientDatabase;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by Мария on 07.12.2016.
 */

public class RecipeListActivity extends AppCompatActivity {
    private RecipeListAdapter adapter;
    private IngredientDatabase recipeDB;
    private OnListItemClickListener clickListener = new OnListItemClickListener() {
        @Override
        public void onClick(View v, int position) {
            int id = adapter.getRecipe(position).getRecipe().getId();
            String name = adapter.getRecipe(position).getRecipe().getName();
            String photo = adapter.getRecipe(position).getRecipe().getImage();
            String ingredients = adapter.getRecipe(position).getRecipe().getIngredient();
            String description = adapter.getRecipe(position).getRecipe().getDescription();
            String calories = adapter.getRecipe(position).getRecipe().getCalories();
            String category = adapter.getRecipe(position).getRecipe().getCategory();

            Intent intent = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("name", name);
            intent.putExtra("photo", photo);
            intent.putExtra("ingredients", ingredients);
            intent.putExtra("description", description);
            intent.putExtra("calories", calories);
            intent.putExtra("category", category);
            startActivity(intent);

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recipelist_recyclerview);

        if (savedInstanceState != null) {
            SelectedIngredient.copyAllIngr(savedInstanceState.getStringArrayList("ingr"));
            SelectedIngredient.copyAllImage(savedInstanceState.getStringArrayList("image"));
        }

        recipeDB = new IngredientDatabase();

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
        getSupportActionBar().setTitle(R.string.recipe_list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipeListAdapter(performRecipes(), clickListener);
        recyclerView.setAdapter(adapter);

        AdView mAdView = (AdView) findViewById(R.id.adFragment);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    private List<RecipeCount> performRecipes() {
        List<Recipe> recipes = recipeDB.getRecipe(SelectedIngredient.getSelectedIngredient());
        List<RecipeCount> newRecipes = new ArrayList<>();
        int count;
        for (int i = 0; i < recipes.size(); i++) {
            count = 0;
            for (int k = 0; k < SelectedIngredient.getSelectedIngredient().size(); k++) {
                if (recipes.get(i).getIngredient().contains(SelectedIngredient.getSelectedIngredient().get(k))) {
                    count++;
                }
            }
            newRecipes.add(new RecipeCount(count, recipes.get(i)));
        }
        Collections.sort(newRecipes, sortByCountAndCategory());
        return newRecipes;
    }

    public Comparator <RecipeCount> sortByCountAndCategory() {
        return (o1, o2) -> {
            if (o2.getCount()==o1.getCount()) {
                return o1.getRecipe().getCategory().compareTo(o2.getRecipe().getCategory());
            } else if (o2.getCount()>o1.getCount()){
                return 1;
            }
            return -1;
        };
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("ingr", SelectedIngredient.getSelectedIngredient());
        outState.putStringArrayList("image", SelectedIngredient.getSelectedImage());
    }

    @Override
    protected void onDestroy() {
        recipeDB.close();
        super.onDestroy();
    }
}
