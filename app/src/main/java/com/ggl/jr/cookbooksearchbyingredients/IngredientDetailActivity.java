package com.ggl.jr.cookbooksearchbyingredients;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ggl.jr.cookbooksearchbyingredients.storage.MyPreferences;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Мария on 27.11.2016.
 */

public class IngredientDetailActivity extends AppCompatActivity implements IngredientDetailAdapter.MenuListener {
    private Intent intent;
    private MenuItem menuItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ingredientdetail_recyclerview);

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
        getSupportActionBar().setTitle(R.string.selected_ingredients);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        IngredientDetailAdapter adapter = new IngredientDetailAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setMenuListener(this);

        AdView mAdView = (AdView) findViewById(R.id.adFragment);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        if (Metrics.smallestWidth() >= 600) {
            inflater.inflate(R.menu.toolbar_buttons_second_activity_tablets, menu);
        } else {
            inflater.inflate(R.menu.toolbar_buttons_second_activity_phones, menu);
        }
        menuItem = menu.findItem(R.id.item3);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        intent = new Intent(this, ProgressBarActivity.class);
        startActivity(intent);
        new Handler().postDelayed(() -> {
            intent = new Intent(this, RecipeListActivity.class);
            startActivity(intent);
        }, 800);

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("ingr", SelectedIngredient.getSelectedIngredient());
        outState.putStringArrayList("image", SelectedIngredient.getSelectedImage());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        setBufferPreferences();
    }

    public void setBufferPreferences() {
        MyPreferences preferences = new MyPreferences(this);

        StringBuilder bufferIngr = new StringBuilder();
        StringBuilder bufferImage = new StringBuilder();
        for (String s : SelectedIngredient.getSelectedIngredient()) {
            bufferIngr.append(s).append(",");
        }
        for (String s : SelectedIngredient.getSelectedImage()) {
            bufferImage.append(s).append(",");
        }
        preferences.setBufferedIngredients(bufferIngr.toString());
        preferences.setBufferedImage(bufferImage.toString());

        if (bufferIngr.toString().equals("")) {
            preferences.setBufferedFlag(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setMenuItemVisible(boolean state) {
        menuItem.setVisible(state);
    }
}
