package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.github.jurassicspb.cookbooksearchbyingredients.fragments.IngredientFragment;
import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Мария on 12.11.2016.
 */

public class IngedientTablayoutActivity extends AppCompatActivity {
    private IngredientDatabase ingredientDB;
    private List<Ingredient> ingredients;
    private List<CategoryTable> categoryTables;

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

//        createCategoryTables();
        performCategoryTables();
        for (int i=0; i<categoryTables.size(); i++){
            IngredientFragment m = new IngredientFragment();
            ingredients = ingredientDB.getCategory(categoryTables.get(i).getNum());
            m.setIngrbycategory(ingredients);
            adapter.addFragment(m, categoryTables.get(i).getName());
        }

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, IngredientDetailActivity.class);
//        intent.putExtra("selected_ingr", SelectedIngredient.getSelectedIngredient());
        startActivity(intent);
        return super.onOptionsItemSelected(item);
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

    private void createCategoryTables(){
        ArrayList<CategoryTable> categoryTables = new ArrayList<>();
        categoryTables.add(new CategoryTable(0, "Мясо"));
        categoryTables.add(new CategoryTable(1, "Рыба"));
        ingredientDB.copyOrUpdateCategoryTable(categoryTables);
    }
    private void performCategoryTables(){
        categoryTables = ingredientDB.getAllCategoryTables();
    }

    public void delete(){
        ArrayList <Ingredient> newIngredient = new ArrayList<>();
        ingredientDB.delete(newIngredient);
    }

    @Override
    protected void onDestroy() {
        SelectedIngredient.getSelectedIngredient().clear();
        SelectedIngredient.resetCount();
        ingredientDB.close();
        super.onDestroy();
    }
}
