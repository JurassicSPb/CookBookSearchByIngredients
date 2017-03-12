package com.github.jurassicspb.cookbooksearchbyingredients;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;

import java.util.List;

/**
 * Created by Мария on 11.03.2017.
 */

public class IngredientFavoritesActivity extends AppCompatActivity {
    private IngredientDatabase ingrFavoritesDB;
    private List<IngredientFavorites> ingrFavorites;
    private GridView gridview;
    private IngredientFavoritesAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ingr_favorites_gridview);

        if (savedInstanceState != null) {
            SelectedIngredient.copyAllIngr(savedInstanceState.getStringArrayList("ingr"));
            SelectedIngredient.copyAllImage(savedInstanceState.getStringArrayList("image"));
            SelectedIngredient.setCount(savedInstanceState.getInt("count"));
        }

        gridview = (GridView) findViewById(R.id.gridview);

        ingrFavoritesDB = new IngredientDatabase();

        performIngrFavorites();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle(R.string.drawer_menu_ingr_favorites);

        adapter = new IngredientFavoritesAdapter(this, ingrFavorites);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String sel = ingrFavorites.get((int) id).getIngredient();
                String image = String.valueOf(ingrFavorites.get((int) id).getImage());

                int ingredientPosition = SelectedIngredient.getSelectedIngredient().indexOf(sel);

                if (ingredientPosition == -1) {
                    if (SelectedIngredient.showCount() < 15) {
                        SelectedIngredient.addSelectedIngredient(sel, image);
                        ingrFavorites.get((int) id).setState(1);
                    } else if (SelectedIngredient.showCount() == 15) {
                        Toast toast = Toast.makeText(getApplication(), R.string.no_more_than_15, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } else {
                    SelectedIngredient.removeSelectedIngredient(sel, image);
                    ingrFavorites.get((int) id).setState(0);
                }
                adapter.notifyDataSetChanged();
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
        refreshCheckboxState();
    }

    public void refreshState() {
        for (int i = 0; i < ingrFavorites.size(); i++) {
            String sel = ingrFavorites.get(i).getIngredient();
            int ingredientPosition = SelectedIngredient.getSelectedIngredient().indexOf(sel);
            if (ingredientPosition > -1) {
                ingrFavorites.get(i).setState(1);
            } else {
                ingrFavorites.get(i).setState(0);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void refreshCheckboxState() {
        List<IngredientFavorites> ingredientFavorites = ingrFavoritesDB.getAllIngrFavorites();

        for (int i = 0; i < ingredientFavorites.size(); i++) {
            ingredientFavorites.get(i).setCheckboxState(1);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("ingr", SelectedIngredient.getSelectedIngredient());
        outState.putStringArrayList("image", SelectedIngredient.getSelectedImage());
        outState.putInt("count", SelectedIngredient.showCount());
    }

    @Override
    protected void onDestroy() {
        ingrFavoritesDB.close();
        super.onDestroy();
    }
}
