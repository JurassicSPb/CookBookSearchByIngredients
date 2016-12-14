package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Мария on 13.12.2016.
 */

public class RecipeDetailActivity extends AppCompatActivity{
    ImageView largeImage;
    TextView name;
    TextView ingredient;
    TextView description;
    TextView calorie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipe_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle("");

        Intent intent = getIntent();

        largeImage = (ImageView) findViewById(R.id.large_image);
        String image = intent.getStringExtra("photo");
        Picasso.with(this)
                .load(image)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(largeImage);

        name = (TextView) findViewById(R.id.name_field);
        String names = intent.getStringExtra("name");
        name.setText(names);

        ingredient = (TextView) findViewById(R.id.ingredients_field);
        String ingredients = intent.getStringExtra("ingredients");
        ingredient.setText(ingredients);

        description = (TextView) findViewById(R.id.description_field);
        String descriptions = intent.getStringExtra("description");
        description.setText(descriptions);

        calorie = (TextView) findViewById(R.id.calories_field);
        String calories = intent.getStringExtra("calories");
        calorie.setText(calories);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_buttons_third_activity, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        return super.onOptionsItemSelected(item);
    }
}
