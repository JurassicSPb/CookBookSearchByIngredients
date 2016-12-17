package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;
import com.github.jurassicspb.cookbooksearchbyingredients.storage.MyPreferences;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Мария on 13.12.2016.
 */

public class RecipeDetailActivity extends AppCompatActivity{
    private IngredientDatabase favoritesDB;
    private List <Favorites> favorites;
    Toast toast;
    MyPreferences preferences;
    ImageView largeImage;
    TextView name;
    TextView ingredient;
    TextView description;
    TextView calorie;
    String names;
    String ingredients;
    String descriptions;
    String calories;
    String image;
    Drawable myDrawable;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipe_detail);

        favoritesDB = new IngredientDatabase();
        preferences = new MyPreferences(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle("");

        Intent intent = getIntent();

        largeImage = (ImageView) findViewById(R.id.large_image);
        image = intent.getStringExtra("photo");
        Picasso.with(this)
                .load(image)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(largeImage);

        name = (TextView) findViewById(R.id.name_field);
        names = intent.getStringExtra("name");
        name.setText(names);
        favorites = favoritesDB.getFavorite(names);

        ingredient = (TextView) findViewById(R.id.ingredients_field);
        ingredients = intent.getStringExtra("ingredients");
        ingredient.setText(ingredients);

        description = (TextView) findViewById(R.id.description_field);
        descriptions = intent.getStringExtra("description");
        description.setText(descriptions);

        calorie = (TextView) findViewById(R.id.calories_field);
        calories = intent.getStringExtra("calories");
        calorie.setText(calories);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_buttons_third_activity, menu);

        MenuItem item = menu.findItem(R.id.item4);

        if (favorites.size()==1) {
            item.setIcon(R.drawable.ic_favorites_selected);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item4:
                if (favorites.size()==0) {
                    myDrawable = getResources().getDrawable(R.drawable.ic_favorites_selected);
                    item.setIcon(myDrawable);
                    ArrayList<Favorites> newFavorites = new ArrayList<>();
                    newFavorites.add(new Favorites(names, ingredients, descriptions, calories, image));
                    favoritesDB.copyOrUpdateFavorites(newFavorites);
                    toast = Toast.makeText(this, R.string.toast_favorites, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else if (favorites.size()==1) {
                    favoritesDB.deleteFavoritePosition(names);
                    myDrawable = getResources().getDrawable(R.drawable.ic_favourites);
                    item.setIcon(myDrawable);
                    toast = Toast.makeText(this, R.string.toast_favorites_remove, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        favoritesDB.close();
        super.onDestroy();
    }
}
