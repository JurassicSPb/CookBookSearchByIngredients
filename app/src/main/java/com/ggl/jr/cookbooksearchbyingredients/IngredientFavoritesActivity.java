package com.ggl.jr.cookbooksearchbyingredients;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ggl.jr.cookbooksearchbyingredients.storage.IngredientDatabase;
import com.ggl.jr.cookbooksearchbyingredients.storage.MyPreferences;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;


/**
 * Created by Мария on 11.03.2017.
 */

public class IngredientFavoritesActivity extends AppCompatActivity {
    private IngredientDatabase ingrFavoritesDB;
    private List<IngredientFavorites> ingrFavorites;
    private IngredientFavoritesAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ingr_favorites_gridview);

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
        getSupportActionBar().setTitle(R.string.drawer_menu_ingr_favorites);

        if (savedInstanceState != null) {
            SelectedIngredient.copyAllIngr(savedInstanceState.getStringArrayList("ingr"));
            SelectedIngredient.copyAllImage(savedInstanceState.getStringArrayList("image"));
            if (SelectedIngredient.showCount() == 0) {
                getSupportActionBar().setTitle(R.string.drawer_menu_ingr_favorites);
            } else {
                getSupportActionBar().setTitle("Выбрано" + ": " + SelectedIngredient.showCount());
            }
        }

        GridView gridview = (GridView) findViewById(R.id.gridview);

        ingrFavoritesDB = new IngredientDatabase();

        MyPreferences preferences = new MyPreferences(this);
        if (preferences.getFlagIngrFavV2_9()) {
            updateIngredientFavorites();
            preferences.setFlagIngrFavV2_9(false);
        }

        performIngrFavorites();

        adapter = new IngredientFavoritesAdapter(this, ingrFavorites, ingrFavoritesDB);
        gridview.setAdapter(adapter);

        AdView mAdView = (AdView) findViewById(R.id.adFragment);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        gridview.setOnItemClickListener((parent, view, position, id) -> {
            String selectedToString = getString(R.string.selected);

            String sel = ingrFavorites.get((int) id).getIngredient();
            String image = String.valueOf(ingrFavorites.get((int) id).getImage());

            if (ingrFavoritesDB.getIngredientStopByName(sel) == null) {

                int ingredientPosition = SelectedIngredient.getSelectedIngredient().indexOf(sel);

                if (ingredientPosition == -1) {
                    if (SelectedIngredient.showCount() < 50) {
                        SelectedIngredient.addSelectedIngredient(sel, image);
                        ingrFavorites.get((int) id).setState(1);
                    } else if (SelectedIngredient.showCount() == 50) {
                        Toast toast = Toast.makeText(getApplication(), R.string.no_more_then_50_ingrs, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } else {
                    SelectedIngredient.removeSelectedIngredient(sel, image);
                    ingrFavorites.get((int) id).setState(0);
                }
                getSupportActionBar().setTitle(selectedToString + ": " + SelectedIngredient.showCount());
                if (SelectedIngredient.showCount() == 0) {
                    getSupportActionBar().setTitle(R.string.drawer_menu_ingr_favorites);
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast toast = Toast.makeText(getApplication(), R.string.remove_from_stop, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    private void performIngrFavorites() {
        ingrFavorites = ingrFavoritesDB.getAllIngrFavoritesSorted();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshState();
        if (SelectedIngredient.showCount() == 0) {
            getSupportActionBar().setTitle(R.string.drawer_menu_ingr_favorites);
        } else {
            getSupportActionBar().setTitle("Выбрано" + ": " + SelectedIngredient.showCount());
        }
    }

    public void refreshState() {
        for (int i = 0; i < ingrFavorites.size(); i++) {
            String sel = ingrFavorites.get(i).getIngredient();
            if (SelectedIngredient.getSelectedIngredient().contains(sel)) {
                ingrFavorites.get(i).setState(1);
            } else {
                ingrFavorites.get(i).setState(0);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void updateIngredientFavorites() {
        ingrFavorites = ingrFavoritesDB.getAllIngrFavoritesUnsorted();
        for (int i = 0; i < ingrFavorites.size(); i++) {
            Ingredient ingredient = ingrFavoritesDB.getIngredientByName(ingrFavorites.get(i).getIngredient());
            if (ingredient != null) {
                IngredientFavorites ingredientFavorite = new IngredientFavorites(ingredient.getIngredient(),
                        ingredient.getImage(), ingredient.getState(), ingredient.getCheckboxState());
                ingrFavoritesDB.copyOrUpdateIngrFavorites(ingredientFavorite);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
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
        ingrFavoritesDB.close();
        super.onDestroy();
    }
}
