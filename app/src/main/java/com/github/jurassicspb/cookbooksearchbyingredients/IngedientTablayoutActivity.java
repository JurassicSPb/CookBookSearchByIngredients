package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
        if (SelectedIngredient.showCount()==0){
            Toast toast = Toast.makeText(this, "Выберите хотя бы один ингредиент",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else {
            Intent intent = new Intent(this, IngredientDetailActivity.class);
//        intent.putExtra("selected_ingr", SelectedIngredient.getSelectedIngredient());
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void createIngredients(){
        ArrayList<Ingredient> newIngredient = new ArrayList<>();
        newIngredient.add(new Ingredient("0.1", 0, "говядина"));
        newIngredient.add(new Ingredient("0.2", 0, "свинина"));
        newIngredient.add(new Ingredient("0.3", 0, "баранина"));
        newIngredient.add(new Ingredient("0.4", 0, "телятина"));
        newIngredient.add(new Ingredient("0.5", 0, "фарш из годядины"));
        newIngredient.add(new Ingredient("0.6", 0, "фарш из свинины"));
        newIngredient.add(new Ingredient("0.7", 0, "фарш из баранины"));
        newIngredient.add(new Ingredient("0.8", 0, "фарш из телятины"));


        newIngredient.add(new Ingredient("1.1", 1, "сельдь"));
        newIngredient.add(new Ingredient("1.2", 1, "карась"));
        newIngredient.add(new Ingredient("1.2", 1, "окунь"));

        newIngredient.add(new Ingredient("2.1", 2, "курица"));
        newIngredient.add(new Ingredient("2.2", 2, "индейка"));

        newIngredient.add(new Ingredient("3.1", 3, "молоко"));
        newIngredient.add(new Ingredient("3.2", 3, "сметана"));
        newIngredient.add(new Ingredient("3.3", 3, "творог"));
        newIngredient.add(new Ingredient("3.4", 3, "сливки"));

        newIngredient.add(new Ingredient("4.1", 4, "огурец"));

        newIngredient.add(new Ingredient("5.1", 5, "яблоко"));
        newIngredient.add(new Ingredient("5.2", 5, "груша"));

        newIngredient.add(new Ingredient("6.1", 6, "рис"));
        newIngredient.add(new Ingredient("6.1", 6, "греча"));


        ingredientDB.copyOrUpdate(newIngredient);
    }

    private void performIngredients(){
        ingredients = ingredientDB.getAll();
    }

    private void createCategoryTables(){
        ArrayList<CategoryTable> categoryTables = new ArrayList<>();
        categoryTables.add(new CategoryTable(0, "Мясо"));
        categoryTables.add(new CategoryTable(1, "Рыба"));
        categoryTables.add(new CategoryTable(2, "Птица"));
        categoryTables.add(new CategoryTable(3, "Молочное"));
        categoryTables.add(new CategoryTable(4, "Овощи"));
        categoryTables.add(new CategoryTable(5, "Фрукты"));
        categoryTables.add(new CategoryTable(6, "Крупы"));
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
