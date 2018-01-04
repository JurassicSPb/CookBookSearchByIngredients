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
import com.ggl.jr.cookbooksearchbyingredients.storage.MyPreferences;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

/**
 * Created by Мария on 27.12.2016.
 */

public class FavoritesActivity extends AppCompatActivity {
    private FavoritesAdapter adapter;
    private IngredientDatabase recipeDB;
    private List<Favorites> favorites;
    private OnListItemClickListener clickListener = new OnListItemClickListener() {
        @Override
        public void onClick(View v, int position) {
            int id = adapter.getRecipe(position).getId();
            String name = adapter.getRecipe(position).getName();
            String photo = adapter.getRecipe(position).getImage();
            String ingredients = adapter.getRecipe(position).getIngredient();
            String description = adapter.getRecipe(position).getDescription();
            String calories = adapter.getRecipe(position).getCalories();
            String category = adapter.getRecipe(position).getCategory();

            Intent intent = new Intent(FavoritesActivity.this, RecipeDetailActivity.class);
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

        setContentView(R.layout.favorites_recyclerview);

        recipeDB = new IngredientDatabase();

        MyPreferences preferences = new MyPreferences(this);
        if (preferences.getFlagRecipesFavV2_1()) {
            updateFavorites();
            preferences.setFlagRecipesFavV2_1(false);
        }

        performFavorites();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Metrics.smallestWidth() >= 600) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_tablets);
        } else {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_phones);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle(R.string.drawer_menu_favorites);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoritesAdapter(favorites, clickListener);
        recyclerView.setAdapter(adapter);

        AdView mAdView = (AdView) findViewById(R.id.adFragment);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void updateFavorites() {
        favorites = recipeDB.getAllFavorites();
        for (int i = 0; i < favorites.size(); i++) {
            Recipe recipe = recipeDB.getRecipeById(favorites.get(i).getId());
            if (recipe != null) {
                Favorites favorite = new Favorites(recipe.getId(), recipe.getName(),
                        recipe.getIngredient(), recipe.getCategory(), recipe.getDescription(),
                        recipe.getCalories(), recipe.getImage());
                recipeDB.copyOrUpdateFavorites(favorite);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void performFavorites(){
        favorites = recipeDB.getAllFavorites();
    }

    @Override
    protected void onDestroy() {
        recipeDB.close();
        super.onDestroy();
    }
}
