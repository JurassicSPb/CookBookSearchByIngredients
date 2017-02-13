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
//            toolbarTextView.setMarqueeRepeatLimit(2);
            toolbarTextView.setText(R.string.ingredient_list);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(12);
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
//            deleteRecipe();

        if (preferences.getFlag()) {
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                createIngredientsRU();
                createCategoryTablesRU();
                createRecipes("rus");
//            }
//            else {
//                createIngredientsENG();
//                createCategoryTablesENG();
//                createRecipes("eng");
//            }
            preferences.setFlag(false);
        }
        performCategoryTables();
        performIngredients();

        for (int i=0; i<categoryTables.size(); i++){
            IngredientFragment m = new IngredientFragment();
            ingredients = ingredientDB.getCategory(categoryTables.get(i).getNum());
            m.setIngrbycategory(ingredientDB.copyFromRealm(ingredients));
//            m.setIngrbycategory(createFakeRecipes());
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

//    public List <Ingredient>createFakeIngrs(){
//        ArrayList<Ingredient> newIngredient = new ArrayList<>();
//        for (int i=0; i<1000; i++){
//            newIngredient.add(new Ingredient("e9", 4, "морковь", R.drawable.carrot, 0));
//        }
//        return newIngredient;
//    }
    private void createIngredientsRU(){
        ArrayList<Ingredient> newIngredient = new ArrayList<>();

            newIngredient.add(new Ingredient("a1", 0, "колбаса", R.drawable.kolbasi, 0));
            newIngredient.add(new Ingredient("a2", 0, "колбаса копченая", R.drawable.kolbasa_kopchenaya, 0));
            newIngredient.add(new Ingredient("a3", 0, "мясо", R.drawable.meat, 0));
            newIngredient.add(new Ingredient("a4", 0, "свинина", R.drawable.pork, 0));

            newIngredient.add(new Ingredient("b1", 1, "крабовые палочки", R.drawable.krabovie_palochki, 0));

            newIngredient.add(new Ingredient("c1", 2, "куриная грудка копченая", R.drawable.grudka_kopchenaja, 0));
            newIngredient.add(new Ingredient("c2", 2, "куриное филе", R.drawable.chicken_fillet, 0));
            newIngredient.add(new Ingredient("c3", 2, "яйца куриные", R.drawable.eggs, 0));

            newIngredient.add(new Ingredient("d1", 3, "сметана", R.drawable.sour_cream, 0));
            newIngredient.add(new Ingredient("d2", 3, "сыр", R.drawable.cheese, 0));
            newIngredient.add(new Ingredient("d3", 3, "плавленый сыр", R.drawable.plavl_sir, 0));

            newIngredient.add(new Ingredient("e1", 4, "зелень", R.drawable.zelen, 0));
            newIngredient.add(new Ingredient("e2", 4, "горошек консервированный", R.drawable.canned_pea, 0));
            newIngredient.add(new Ingredient("e3", 4, "кабачок", R.drawable.cabachok, 0));
            newIngredient.add(new Ingredient("e4", 4, "капуста", R.drawable.cabbage, 0));
            newIngredient.add(new Ingredient("e5", 4, "капуста пекинская", R.drawable.pekinskaya_kapusta, 0));
            newIngredient.add(new Ingredient("e6", 4, "картофель", R.drawable.potato, 0));
            newIngredient.add(new Ingredient("e7", 4, "кукуруза консервированная", R.drawable.canned_corn, 0));
            newIngredient.add(new Ingredient("e8", 4, "лук", R.drawable.onion, 0));
            newIngredient.add(new Ingredient("e9", 4, "морковь", R.drawable.carrot, 0));
            newIngredient.add(new Ingredient("e10", 4, "огурец", R.drawable.cucumber, 0));
            newIngredient.add(new Ingredient("e11", 4, "помидор", R.drawable.tomat, 0));
            newIngredient.add(new Ingredient("e12", 4, "укроп", R.drawable.ukrop, 0));
            newIngredient.add(new Ingredient("e13", 4, "фасоль красная консервированная", R.drawable.fasol_red, 0));
            newIngredient.add(new Ingredient("e14", 4, "чеснок", R.drawable.garlic, 0));

            newIngredient.add(new Ingredient("f1", 5, "ананас консервированный", R.drawable.ananas_konserv, 0));
            newIngredient.add(new Ingredient("f2", 5, "киви", R.drawable.qiwi, 0));
            newIngredient.add(new Ingredient("f3", 5, "яблоко", R.drawable.apple, 0));

//        newIngredient.add(new Ingredient("g1", 6, "грибы", R.drawable.ic_circle, 0));

//        newIngredient.add(new Ingredient("h1", 7, "Рис", R.drawable.ic_circle, 0));
//        newIngredient.add(new Ingredient("h1", 7, "Греча", R.drawable.ic_circle, 0));

            newIngredient.add(new Ingredient("i1", 8, "сухари из белого хлеба", R.drawable.suhari_bel, 0));

//        newIngredient.add(new Ingredient("j1", 9, "орехи", R.drawable.suhari_bel, 0));

            newIngredient.add(new Ingredient("k1", 10, "корица", R.drawable.koritsa, 0));
            newIngredient.add(new Ingredient("k2", 10, "майонез", R.drawable.mayonese, 0));
            newIngredient.add(new Ingredient("k3", 10, "перец", R.drawable.pepper, 0));
            newIngredient.add(new Ingredient("k4", 10, "сахар", R.drawable.sugar, 0));
            newIngredient.add(new Ingredient("k5", 10, "соль", R.drawable.salt, 0));
            newIngredient.add(new Ingredient("k6", 10, "томатная паста", R.drawable.tomat_pasta, 0));

            newIngredient.add(new Ingredient("l1", 11, "сок лимона", R.drawable.lemon_juice, 0));
            newIngredient.add(new Ingredient("l2", 11, "сок яблочный", R.drawable.apple_juice, 0));

        ingredientDB.copyOrUpdate(newIngredient);
    }

    private void performIngredients(){
        ingredients = ingredientDB.getAll();
    }

    private void createCategoryTablesRU(){
        ArrayList<CategoryTable> categoryTables = new ArrayList<>();

        categoryTables.add(new CategoryTable(0, "Мясное"));
        categoryTables.add(new CategoryTable(1, "Морепродукты"));
        categoryTables.add(new CategoryTable(2, "Птица/Яйца"));
        categoryTables.add(new CategoryTable(3, "Молочное/Сыр"));
        categoryTables.add(new CategoryTable(4, "Овощи"));
        categoryTables.add(new CategoryTable(5, "Фрукты"));
        categoryTables.add(new CategoryTable(6, "Грибы"));
        categoryTables.add(new CategoryTable(7, "Крупы"));
        categoryTables.add(new CategoryTable(8, "Хлебобул. изделия"));
        categoryTables.add(new CategoryTable(9, "Орехи"));
        categoryTables.add(new CategoryTable(10, "Приправы/Соусы"));
        categoryTables.add(new CategoryTable(11, "Жидкости"));
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
        SelectedIngredient.getSelectedImage().clear();
        SelectedIngredient.resetCount();
        ingredientDB.close();
        super.onDestroy();
    }
    //    private void createIngredientsENG(){
//        ArrayList<Ingredient> newIngredient = new ArrayList<>();
//        newIngredient.add(new Ingredient("0.1", 0, "Beef", R.drawable.beef, 0));
//        newIngredient.add(new Ingredient("0.2", 0, "Pork", R.drawable.ic_circle, 0));
//        ingredientDB.copyOrUpdate(newIngredient);
//    }

//    private void createCategoryTablesENG(){
//        ArrayList<CategoryTable> categoryTables = new ArrayList<>();
//        categoryTables.add(new CategoryTable(0, "Meat"));
//        categoryTables.add(new CategoryTable(1, "Fish"));
//        categoryTables.add(new CategoryTable(2, "Bird"));
//        categoryTables.add(new CategoryTable(3, "Milk"));
//        categoryTables.add(new CategoryTable(4, "Vegetables"));
//        categoryTables.add(new CategoryTable(5, "Fruits"));
//        categoryTables.add(new CategoryTable(6, "Cereal"));
//        ingredientDB.copyOrUpdateCategoryTable(categoryTables);
//    }
}
