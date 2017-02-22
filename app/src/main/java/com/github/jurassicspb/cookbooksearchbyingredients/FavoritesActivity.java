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
import java.util.List;

/**
 * Created by Мария on 27.12.2016.
 */

public class FavoritesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;
    private IngredientDatabase recipeDB;
    private List<Favorites> favorites;
    private OnListItemClickListener clickListener = new OnListItemClickListener() {
        @Override
        public void onClick(View v, int position) {
            String name = adapter.getRecipe(position).getName();
            String photo = adapter.getRecipe(position).getImage();
            String ingredients = adapter.getRecipe(position).getIngredient();
            String description = adapter.getRecipe(position).getDescription();
            String calories = adapter.getRecipe(position).getCalories();

            Intent intent = new Intent(FavoritesActivity.this, RecipeDetailActivity.class);
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
        setContentView(R.layout.favorites_recyclerview);

        recipeDB = new IngredientDatabase();
        performFavorites();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle(R.string.drawer_menu_favorites);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoritesAdapter(favorites, clickListener);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void performFavorites(){
        favorites = recipeDB.getAllFavorites();
    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        Intent intent = new Intent(this,IngedientTablayoutActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        finish();
//    }
    @Override
    protected void onDestroy() {
        recipeDB.close();
        super.onDestroy();
    }
}
