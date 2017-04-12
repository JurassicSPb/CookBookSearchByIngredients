package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;

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
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle(R.string.recipe_list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipeListAdapter(performRecipes(), clickListener);
        recyclerView.setAdapter(adapter);

    }

    private List<Recipe> performRecipes() {
        List<Recipe> recipes = recipeDB.getRecipe(SelectedIngredient.getSelectedIngredient());
        List<Recipe> newRecipes = recipeDB.copyFromRealmRecipe(recipes);
        int count;
        for (int i = 0; i < newRecipes.size(); i++) {
            count = 0;
            for (int k = 0; k < SelectedIngredient.getSelectedIngredient().size(); k++) {
                if (newRecipes.get(i).getIngredient().contains(SelectedIngredient.getSelectedIngredient().get(k))) {
                    count++;
                }
            }
            newRecipes.get(i).setCount(count);
        }
        Collections.sort(newRecipes, sortByCountAndCategory());
        return newRecipes;
    }

    public Comparator <Recipe> sortByCountAndCategory() {
        Comparator<Recipe> comparator = (o1, o2) -> {
            if (o2.getCount()==o1.getCount()) {
                return o1.getCategory().compareTo(o2.getCategory());
            } else if (o2.getCount()>o1.getCount()){
                return 1;
            }
            return -1;
        };
        return comparator;
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
