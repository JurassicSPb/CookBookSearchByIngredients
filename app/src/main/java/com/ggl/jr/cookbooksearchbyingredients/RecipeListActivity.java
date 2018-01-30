package com.ggl.jr.cookbooksearchbyingredients;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ggl.jr.cookbooksearchbyingredients.storage.IngredientDatabase;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by Мария on 07.12.2016.
 */

public class RecipeListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private AdView mAdView;
    private RecipeListAdapter adapter;
    private List<RecipeCount> newRecipes = new ArrayList<>();
    private ExecutorService executor = Executors.newSingleThreadExecutor();
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
    private OnDataReadyCallback callback = () -> runOnUiThread(() -> {
        adapter = new RecipeListAdapter(newRecipes, clickListener);
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.GONE);

        if (newRecipes.isEmpty()) {
            Toast toast = Toast.makeText(getApplication(), R.string.recipe_list_empty, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recipelist_recyclerview);

        if (savedInstanceState != null) {
            SelectedIngredient.copyAllIngr(savedInstanceState.getStringArrayList("ingr"));
            SelectedIngredient.copyAllImage(savedInstanceState.getStringArrayList("image"));
        }

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
        getSupportActionBar().setTitle(R.string.recipe_list);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(android.widget.ProgressBar.VISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.progressBar), PorterDuff.Mode.SRC_ATOP);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdView = (AdView) findViewById(R.id.adFragment);

        executor.execute(this::performRecipes);
    }

    private void performRecipes() {
        IngredientDatabase recipeDB = null;

        try {
            recipeDB = new IngredientDatabase();

            List<Recipe> recipes = recipeDB.getRecipe(recipeDB.getAllIngrStopUnsorted(), SelectedIngredient.getSelectedIngredient());
            int count;
            for (int i = 0; i < recipes.size(); i++) {
                count = 0;
                for (int k = 0; k < SelectedIngredient.getSelectedIngredient().size(); k++) {
                    if (recipes.get(i).getIngredient().contains(SelectedIngredient.getSelectedIngredient().get(k))) {
                        count++;
                    }
                }
                Recipe r = recipes.get(i);
                newRecipes.add(new RecipeCount(count, r.getId(), r.getName(), r.getIngredient(), r.getCategory(),
                        r.getDescription(), r.getImage(), r.getCalories()));
            }
            Collections.sort(newRecipes, sortByCountAndCategory());
        } finally {
            if (recipeDB != null) {
                recipeDB.close();
            }
        }
        callback.callBackCall();
    }

    public Comparator<RecipeCount> sortByCountAndCategory() {
        return (o1, o2) -> {
            if (o2.getCount() == o1.getCount()) {
                return o1.getCategory().compareTo(o2.getCategory());
            } else if (o2.getCount() > o1.getCount()) {
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
        if (!executor.isShutdown()) {
            executor.shutdown();
        }
        super.onDestroy();
    }
}
