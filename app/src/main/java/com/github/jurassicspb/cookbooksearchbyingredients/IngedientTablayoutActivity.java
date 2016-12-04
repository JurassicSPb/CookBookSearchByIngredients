package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jurassicspb.cookbooksearchbyingredients.fragments.IngredientFragment;
import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;
import com.github.jurassicspb.cookbooksearchbyingredients.storage.MyPreferences;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Мария on 12.11.2016.
 */

public class IngedientTablayoutActivity extends AppCompatActivity {
    private IngredientDatabase ingredientDB;
    private List<Ingredient> ingredients;
    private List<CategoryTable> categoryTables;
    MyPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = new MyPreferences(this);
//        preferences.clearPrefs();

        setContentView(R.layout.tablayout_with_viewpager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle("Список ингредиентов");
        try {
            Field f = toolbar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            TextView toolbarTextView = (TextView) f.get(toolbar);
            toolbarTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            toolbarTextView.setFocusable(true);
            toolbarTextView.setFocusableInTouchMode(true);
            toolbarTextView.requestFocus();
            toolbarTextView.setSingleLine(true);
            toolbarTextView.setSelected(true);
            toolbarTextView.setMarqueeRepeatLimit(2);
            toolbarTextView.setText("Список ингредиентов");
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(7);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        ingredientDB = new IngredientDatabase();

        if (preferences.getFlag()) {
            createIngredients();
            createCategoryTables();
            preferences.setFlag(false);
        }
        performCategoryTables();
        performIngredients();
        //        delete();

        for (int i=0; i<categoryTables.size(); i++){
            IngredientFragment m = new IngredientFragment();
            ingredients = ingredientDB.getCategory(categoryTables.get(i).getNum());
            m.setIngrbycategory(ingredientDB.copyFromRealm(ingredients));

            adapter.addFragment(m, categoryTables.get(i).getName());
        }

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_buttons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                if (SelectedIngredient.showCount() == 0) {
                    Toast toast = Toast.makeText(this, "Выберите хотя бы один ингредиент", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Intent intent = new Intent(this, IngredientDetailActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.item2:
                Intent intent = new Intent(this,IngedientTablayoutActivity.class);
                startActivity(intent);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void createIngredients(){
        ArrayList<Ingredient> newIngredient = new ArrayList<>();
        newIngredient.add(new Ingredient("0.1", 0, "говядина", R.drawable.beef));
        newIngredient.add(new Ingredient("0.2", 0, "свинина", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("0.3", 0, "баранина", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("0.4", 0, "телятина", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("0.5", 0, "фарш из годядины", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("0.6", 0, "фарш из свинины", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("0.7", 0, "фарш из баранины", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("0.8", 0, "фарш из телятины", R.drawable.ic_circle));

        newIngredient.add(new Ingredient("1.1", 1, "сельдь", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("1.2", 1, "карась", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("1.2", 1, "окунь", R.drawable.ic_circle));

        newIngredient.add(new Ingredient("2.1", 2, "курица", R.drawable.chicken));
        newIngredient.add(new Ingredient("2.2", 2, "индейка", R.drawable.ic_circle));

        newIngredient.add(new Ingredient("3.1", 3, "молоко", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("3.2", 3, "сметана", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("3.3", 3, "творог", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("3.4", 3, "сливки", R.drawable.ic_circle));

        newIngredient.add(new Ingredient("4.1", 4, "огурец", R.drawable.ic_circle));

        newIngredient.add(new Ingredient("5.1", 5, "яблоко", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("5.2", 5, "груша", R.drawable.ic_circle));

        newIngredient.add(new Ingredient("6.1", 6, "рис", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("6.1", 6, "греча", R.drawable.ic_circle));

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
