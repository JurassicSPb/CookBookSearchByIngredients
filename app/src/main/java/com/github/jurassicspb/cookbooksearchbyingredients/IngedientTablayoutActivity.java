package com.github.jurassicspb.cookbooksearchbyingredients;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.jurassicspb.cookbooksearchbyingredients.fragments.IngredientFragment;
import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by Мария on 12.11.2016.
 */

public class IngedientTablayoutActivity extends AppCompatActivity {
    private IngredientDatabase ingredientDB;
    private List<Ingredient> ingredients;
    private List<CategoryTable> categoryTables;
    private ArrayList<String> categories = new ArrayList<>();
    private LinkedHashSet<String> set;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout_with_viewpager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle("Список ингредиентов");


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        ingredientDB = new IngredientDatabase();
//        createIngredients();
        performIngredients();

        for (int i = 0; i<ingredients.size(); i++) {
            categories.add(String.valueOf(ingredients.get(i).getCategory()));
        }
        set = new LinkedHashSet<>(categories);
        categories.clear();
        categories.addAll(set);

        for (int i=0; i<categories.size(); i++){
            IngredientFragment m = new IngredientFragment();
            ingredients = ingredientDB.getCategory(Integer.valueOf(categories.get(i)));
            m.setIngrbycategory(ingredients);
            adapter.addFragment(m, categories.get(i));
        }

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

    }
    private void createIngredients(){
        ArrayList<Ingredient> newIngredient = new ArrayList<>();
        newIngredient.add(new Ingredient("0.1", 0, "говядина"));
        newIngredient.add(new Ingredient("0.2", 0, "свинина"));
        newIngredient.add(new Ingredient("1.1", 1, "сельдь"));
        newIngredient.add(new Ingredient("1.2", 1, "щука"));
        ingredientDB.copyOrUpdate(newIngredient);

    }
    private void performIngredients(){
        ingredients = ingredientDB.getAll();
    }

    public void delete(){
        ArrayList <Ingredient> newIngredient = new ArrayList<>();
        ingredientDB.delete(newIngredient);
    }

    @Override
    protected void onDestroy() {
        ingredientDB.close();
        super.onDestroy();
    }
}
