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
import com.github.jurassicspb.cookbooksearchbyingredients.storage.MyPreferences;
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
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle(R.string.drawer_menu_ingr_favorites);

        if (savedInstanceState != null) {
            SelectedIngredient.copyAllIngr(savedInstanceState.getStringArrayList("ingr"));
            SelectedIngredient.copyAllImage(savedInstanceState.getStringArrayList("image"));
//            SelectedIngredient.setCount(savedInstanceState.getInt("count"));
            if (SelectedIngredient.showCount() == 0) {
                getSupportActionBar().setTitle(R.string.drawer_menu_ingr_favorites);
            } else {
                getSupportActionBar().setTitle("Выбрано" + ": " + SelectedIngredient.showCount());
            }
        }

        GridView gridview = (GridView) findViewById(R.id.gridview);

        ingrFavoritesDB = new IngredientDatabase();

        performIngrFavorites();

        adapter = new IngredientFavoritesAdapter(this, ingrFavorites);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedToString = getString(R.string.selected);

                String sel = ingrFavorites.get((int) id).getIngredient();
                String image = String.valueOf(ingrFavorites.get((int) id).getImage());

                int ingredientPosition = SelectedIngredient.getSelectedIngredient().indexOf(sel);

                if (ingredientPosition == -1) {
                    if (SelectedIngredient.showCount() < 15) {
                        SelectedIngredient.addSelectedIngredient(sel, image);
                        SelectedIngredient.showCount();
                        ingrFavorites.get((int) id).setState(1);
                    } else if (SelectedIngredient.showCount() == 15) {
                        Toast toast = Toast.makeText(getApplication(), R.string.no_more_than_15, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } else {
                    SelectedIngredient.removeSelectedIngredient(sel, image);
                    SelectedIngredient.showCount();
                    ingrFavorites.get((int) id).setState(0);
                }
                getSupportActionBar().setTitle(selectedToString + ": " + SelectedIngredient.showCount());
                if (SelectedIngredient.showCount() == 0) {
                    getSupportActionBar().setTitle(R.string.drawer_menu_ingr_favorites);
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
        if (SelectedIngredient.showCount() == 0) {
            getSupportActionBar().setTitle(R.string.drawer_menu_ingr_favorites);
        } else {
            getSupportActionBar().setTitle("Выбрано" + ": " + SelectedIngredient.showCount());
        }
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        outState.putStringArrayList("ingr", SelectedIngredient.getSelectedIngredient());
        outState.putStringArrayList("image", SelectedIngredient.getSelectedImage());
//        outState.putInt("count", SelectedIngredient.showCount());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        setBufferPreferences();
    }

    public void setBufferPreferences(){
        MyPreferences preferences = new MyPreferences(this);

        if (SelectedIngredient.getSelectedIngredient().size() > 0) {
            String bufferIngr = "";
            String bufferImage = "";
            for (String s : SelectedIngredient.getSelectedIngredient()) {
                bufferIngr += s + ",";
            }
            for (String s : SelectedIngredient.getSelectedImage()) {
                bufferImage += s + ",";
            }
            preferences.setBufferedIngredients(bufferIngr);
            preferences.setBufferedImage(bufferImage);
        }
    }

    @Override
    protected void onDestroy() {
        ingrFavoritesDB.close();
        super.onDestroy();
    }
}
