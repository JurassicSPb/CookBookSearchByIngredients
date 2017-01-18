package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jurassicspb.cookbooksearchbyingredients.fragments.IngredientFragment;
import com.github.jurassicspb.cookbooksearchbyingredients.nav_drawer_extras.CookingTime;
import com.github.jurassicspb.cookbooksearchbyingredients.nav_drawer_extras.WeightsAndMeasures;
import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;
import com.github.jurassicspb.cookbooksearchbyingredients.storage.MyPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Мария on 12.11.2016.
 */

public class IngedientTablayoutActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private IngredientDatabase ingredientDB;
    private List<Ingredient> ingredients;
    private List<CategoryTable> categoryTables;
    private MyPreferences preferences;
    private DrawerLayout drawer;
    private TabLayout tabLayout;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tablayout_with_viewpager);

        preferences = new MyPreferences(this);
//                preferences.clearPrefs();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.ingredient_list);
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
            toolbarTextView.setText(R.string.ingredient_list);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(7);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        for (int i = 0; i < toolbar.getChildCount(); i++) {
            if(toolbar.getChildAt(i) instanceof ImageButton){
                toolbar.getChildAt(i).setScaleX(1.3f);
                toolbar.getChildAt(i).setScaleY(1.3f);
            }
        }

        ingredientDB = new IngredientDatabase();
//            delete();
    //        deleteRecipe();


        if (preferences.getFlag()) {
            if (Locale.getDefault().getLanguage().equals("ru")) {
                createIngredientsRU();
                createCategoryTablesRU();
                createRecipes("rus");
            }
            else {
                createIngredientsENG();
                createCategoryTablesENG();
                createRecipes("eng");
            }
            preferences.setFlag(false);
        }
        performCategoryTables();
        performIngredients();

        for (int i=0; i<categoryTables.size(); i++){
            IngredientFragment m = new IngredientFragment();
            ingredients = ingredientDB.getCategory(categoryTables.get(i).getNum());
            m.setIngrbycategory(ingredientDB.copyFromRealm(ingredients));
            adapter.addFragment(m, categoryTables.get(i).getName());
        }

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabTextColors(
                ContextCompat.getColor(this, R.color.tabLayoutTextColorUnselected),
                ContextCompat.getColor(this, R.color.tabLayoutTextColorSelected)
        );
    }
//    public void customTabs(){
//        for (int i=0; i<categoryTables.size(); i++) {
//            TextView tabI = new TextView(this);
////            tabI.setTextColor(getResources().getColor(R.color.tabLayoutTextColor));
//            tabI.setText(categoryTables.get(i).getName());
//            tabI.setTypeface(Typeface.SANS_SERIF);
//            tabI.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.tabLayout_textsize));
//            tabLayout.getTabAt(i).setCustomView(tabI);
//        }
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.fr1) {
                intent = new Intent(IngedientTablayoutActivity.this,FavoritesActivity.class);
                startActivity(intent);
        }
        else if (id==R.id.fr2) {
            intent = new Intent(IngedientTablayoutActivity.this,ProgressBarActivity.class);
            startActivity(intent);
            new Handler().postDelayed(() -> {
                intent = new Intent(IngedientTablayoutActivity.this,FullListActivity.class);
                startActivity(intent);
            }, 600);
        }
        else if (id==R.id.fr3) {
            finish();
        }
        else if (id==R.id.fr4){
            intent = new Intent(this, WeightsAndMeasures.class);
            startActivity(intent);
        }
        else if (id==R.id.fr5){
            intent = new Intent(this, CookingTime.class);
            startActivity(intent);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                    Toast toast = Toast.makeText(this, R.string.select_one, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    intent = new Intent(this, IngredientDetailActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.item2:
                intent = new Intent(this,IngedientTablayoutActivity.class);
                startActivity(intent);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void createIngredientsRU(){
        ArrayList<Ingredient> newIngredient = new ArrayList<>();
        newIngredient.add(new Ingredient("0.1", 0, "Говядина", R.drawable.beef));
        newIngredient.add(new Ingredient("0.2", 0, "Свинина", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("0.3", 0, "Фарш из говядины", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("0.4", 0, "Баранина", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("0.5", 0, "Телятина", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("0.6", 0, "Фарш из свинины", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("0.7", 0, "Фарш из баранины", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("0.8", 0, "Фарш из телятины", R.drawable.ic_circle));

        newIngredient.add(new Ingredient("1.1", 1, "Сельдь", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("1.2", 1, "Карась", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("1.2", 1, "Окунь", R.drawable.ic_circle));

        newIngredient.add(new Ingredient("2.1", 2, "Курица", R.drawable.chicken));
        newIngredient.add(new Ingredient("2.2", 2, "Индейка", R.drawable.ic_circle));

        newIngredient.add(new Ingredient("3.1", 3, "Молоко", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("3.2", 3, "Сметана", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("3.3", 3, "Творог", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("3.4", 3, "Сливки", R.drawable.ic_circle));

        newIngredient.add(new Ingredient("4.1", 4, "Огурец", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("4.2", 4, "Томат", R.drawable.ic_circle));

        newIngredient.add(new Ingredient("5.1", 5, "Яблоко", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("5.2", 5, "Груша", R.drawable.ic_circle));

        newIngredient.add(new Ingredient("6.1", 6, "Рис", R.drawable.ic_circle));
        newIngredient.add(new Ingredient("6.1", 6, "Греча", R.drawable.ic_circle));

        ingredientDB.copyOrUpdate(newIngredient);
    }
    private void createIngredientsENG(){
        ArrayList<Ingredient> newIngredient = new ArrayList<>();
        newIngredient.add(new Ingredient("0.1", 0, "Beef", R.drawable.beef));
        newIngredient.add(new Ingredient("0.2", 0, "Pork", R.drawable.ic_circle));
        ingredientDB.copyOrUpdate(newIngredient);
    }

    private void performIngredients(){
        ingredients = ingredientDB.getAll();
    }

    private void createCategoryTablesRU(){
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
    private void createCategoryTablesENG(){
        ArrayList<CategoryTable> categoryTables = new ArrayList<>();
        categoryTables.add(new CategoryTable(0, "Meat"));
        categoryTables.add(new CategoryTable(1, "Fish"));
        categoryTables.add(new CategoryTable(2, "Bird"));
        categoryTables.add(new CategoryTable(3, "Milk"));
        categoryTables.add(new CategoryTable(4, "Vegetables"));
        categoryTables.add(new CategoryTable(5, "Fruits"));
        categoryTables.add(new CategoryTable(6, "Cereal"));
        ingredientDB.copyOrUpdateCategoryTable(categoryTables);
    }

    private void performCategoryTables(){
        categoryTables = ingredientDB.getAllCategoryTables();
    }

    public void delete(){
        ArrayList <Ingredient> newIngredient = new ArrayList<>();
        ingredientDB.delete(newIngredient);
    }
    public void deleteRecipe(){
        ArrayList <Recipe> newRecipe = new ArrayList<>();
        ingredientDB.deleteRecipes(newRecipe);
    }

    private void createRecipes(String language) {
        ArrayList<Recipe> newRecipe = new ArrayList<>();
        AssetManager am = getApplicationContext().getAssets();
        try {
            String fileList[] = am.list(language);
            for (int i = 0; i < fileList.length; i++) {
                InputStream is = am.open(language + "/" + fileList[i]);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String json = new String(buffer, "UTF-8");
                try {
                    JSONObject obj = new JSONObject(json);
                    String name = obj.getString("name");
                    String ingredients = obj.getString("ingredients");
                    String category = obj.getString("category");
                    String description = obj.getString("description");
                    String calories = obj.getString("calories");
                    String image = obj.getString("image");
                    newRecipe.add(new Recipe(name, ingredients, category, description, calories, image));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ingredientDB.copyOrUpdateRecipe(newRecipe);
    }
    @Override
    public void onBackPressed() {
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onDestroy() {
        SelectedIngredient.getSelectedIngredient().clear();
        SelectedIngredient.resetCount();
        ingredientDB.close();
        super.onDestroy();
    }
}
