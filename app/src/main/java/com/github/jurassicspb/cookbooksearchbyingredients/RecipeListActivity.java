package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;
import com.github.jurassicspb.cookbooksearchbyingredients.storage.MyPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Мария on 07.12.2016.
 */

public class RecipeListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeListAdapter adapter;
    private IngredientDatabase recipeDB;
    private List <Recipe> recipes;
    MyPreferences preferences;
    private OnListItemClickListener clickListener = new OnListItemClickListener() {
        @Override
        public void onClick(View v, int position) {
            String name = adapter.getRecipe(position).getName();
            String photo = adapter.getRecipe(position).getImage();
            String ingredients = adapter.getRecipe(position).getIngredient();
            String description = adapter.getRecipe(position).getDescription();
            String calories = adapter.getRecipe(position).getCalories();

            Intent intent = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("photo", photo);
            intent.putExtra("ingredients", ingredients);
            intent.putExtra("description", description);
            intent.putExtra("calories", calories);
            startActivity(intent);

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeDB = new IngredientDatabase();
        preferences = new MyPreferences(this);
//        deleteRecipe();
//        preferences.clearPrefs();

        setContentView(R.layout.recipelist_recyclerview);

        if (preferences.getFlagRecipes()) {
            if (Locale.getDefault().getLanguage().equals("ru")) {
                createRecipes("rus");
            }
            else {
                createRecipes("eng");
            }
            preferences.setFlagRecipe(false);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle("");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipeListAdapter(performRecipes(), clickListener);
        recyclerView.setAdapter(adapter);

    }
    private List<Recipe> performRecipes() {
        recipes = recipeDB.getRecipe(SelectedIngredient.getSelectedIngredient());
        List<Recipe> newRecipes = recipeDB.copyFromRealmRecipe(recipes);
        int count;
        for (int i = 0; i < newRecipes.size(); i++) {
            count = 0;
            for (int k = 0; k < SelectedIngredient.getSelectedIngredient().size(); k++) {
                if (newRecipes.get(i).getIngredient().contains(SelectedIngredient.getSelectedIngredient().get(k))) {
                    count++;
                }
            }
            newRecipes.get(i).setCount(String.valueOf(count));
        }
        Comparator<Recipe> compare = (name1, name2) -> name2.getCount().compareTo(name1.getCount());
        Collections.sort(newRecipes, compare);
        return newRecipes;
    }

    public void deleteRecipe(){
        ArrayList <Recipe> newRecipe = new ArrayList<>();
        recipeDB.deleteRecipes(newRecipe);
    }

    private void createRecipes(String language) {
        ArrayList<Recipe> newRecipe = new ArrayList<>();
        AssetManager am = getApplicationContext().getAssets();
        try {
            String fileList[] = am.list(language);
            for (int i = 0; i < fileList.length; i++) {
                InputStream is = am.open(language + "/" + fileList[i]);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String json = new String(buffer, "UTF-8");
                try {
                    JSONObject obj = new JSONObject(json);
                    String name = obj.getString("name");
                    String ingredients = obj.getString("ingredients");
                    String description = obj.getString("description");
                    String calories = obj.getString("calories");
                    String image = obj.getString("image");
                    newRecipe.add(new Recipe(name, ingredients, description, calories, image));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        recipeDB.copyOrUpdateRecipe(newRecipe);
    }
    @Override
    protected void onDestroy() {
        recipeDB.close();
        super.onDestroy();
    }
}
