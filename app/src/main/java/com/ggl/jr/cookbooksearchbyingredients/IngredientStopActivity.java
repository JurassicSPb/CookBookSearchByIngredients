package com.ggl.jr.cookbooksearchbyingredients;

import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ggl.jr.cookbooksearchbyingredients.storage.IngredientDatabase;
import com.ggl.jr.cookbooksearchbyingredients.storage.MyPreferences;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

/**
 * Created by yuri on 11/30/17.
 */

public class IngredientStopActivity extends AppCompatActivity {
    private IngredientDatabase ingredientStopDB;
    private List<IngredientStop> ingrStop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ingr_stop_gridview);

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
        getSupportActionBar().setTitle(R.string.drawer_menu_stop_list);

        GridView gridview = (GridView) findViewById(R.id.gridview);

        ingredientStopDB = new IngredientDatabase();

        MyPreferences preferences = new MyPreferences(this);
        if (preferences.getFlagStopListV2_85()) {
            updateStopList();
            preferences.setFlagStopListV2_85(false);
        }

        ingrStop = ingredientStopDB.getAllIngrStopSorted();

        IngredientStopAdapter adapter = new IngredientStopAdapter(this, ingrStop, ingredientStopDB);
        gridview.setAdapter(adapter);

        AdView mAdView = (AdView) findViewById(R.id.adFragment);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void updateStopList() {
        ingrStop = ingredientStopDB.getAllIngrStopUnsorted();
        for (int i = 0; i < ingrStop.size(); i++) {
            Ingredient ingredient = ingredientStopDB.getIngredientByName(ingrStop.get(i).getIngredient());
            if (ingredient != null) {
                IngredientStop ingredientStop = new IngredientStop(ingredient.getIngredient(),
                        ingredient.getImage(), ingredient.getStopState());
                ingredientStopDB.copyOrUpdateIngrStop(ingredientStop);
            }
        }
    }

    @Override
    protected void onDestroy() {
        ingredientStopDB.close();
        super.onDestroy();
    }
}
