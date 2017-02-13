package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;
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
    ImageView largeImage;
    TextView ingredient;
    TextView description;
    TextView calorie;
    String names;
    String ingredients;
    String descriptions;
    String calories;
    String image;
    String category;
    Drawable myDrawable;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipe_detail);

        favoritesDB = new IngredientDatabase();

        Intent intent = getIntent();

        Typeface typefaceIngredientDescription = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
        Typeface typefaceCalorieAndIngredient = Typeface.createFromAsset(getAssets(), "fonts/Lobster-Regular.ttf");

        names = intent.getStringExtra("name");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle(names);

        favorites = favoritesDB.getFavorite(names);

        largeImage = (ImageView) findViewById(R.id.large_image);
        image = intent.getStringExtra("photo");
        Picasso.with(this)
                .load(image)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.timeleft128)
                .error(R.drawable.noconnection128)
                .into(largeImage);

        ingredient = (TextView) findViewById(R.id.ingredients_field);
        ingredients = intent.getStringExtra("ingredients");
        ingredient.setTypeface(typefaceCalorieAndIngredient);

//        final Spannable text = new SpannableString(ingredients);
//        final ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.GREEN);
//
//        for (int i = 0; i < SelectedIngredient.getSelectedIngredient().size(); i++) {
//            if (ingredients.contains(SelectedIngredient.getSelectedIngredient().get(i))) {
//                int position = ingredients.indexOf(SelectedIngredient.getSelectedIngredient().get(i));
//                text.setSpan(colorSpan, position, position + SelectedIngredient.getSelectedIngredient().get(i).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//        }
        ingredient.setText(ingredients);


        description = (TextView) findViewById(R.id.description_field);
        descriptions = intent.getStringExtra("description");
        description.setText(descriptions);
        description.setTypeface(typefaceIngredientDescription);

        calorie = (TextView) findViewById(R.id.calories_field);
        calories = intent.getStringExtra("calories");
        calorie.setText(calories);
        calorie.setTypeface(typefaceCalorieAndIngredient);

        category = intent.getStringExtra("category");
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
                    myDrawable = ContextCompat.getDrawable(this, R.drawable.ic_favorites_selected);
                    item.setIcon(myDrawable);
                    ArrayList<Favorites> newFavorites = new ArrayList<>();
                    newFavorites.add(new Favorites(names, ingredients, category, descriptions, calories, image));
                    favoritesDB.copyOrUpdateFavorites(newFavorites);
                    toast = Toast.makeText(this, R.string.toast_favorites, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else if (favorites.size()==1) {
                    favoritesDB.deleteFavoritePosition(names);
                    myDrawable = ContextCompat.getDrawable(this, R.drawable.ic_favourites);
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
