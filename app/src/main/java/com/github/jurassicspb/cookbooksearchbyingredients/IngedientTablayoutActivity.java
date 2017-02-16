package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
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

import com.github.jurassicspb.cookbooksearchbyingredients.fragments.FragmentInterface;
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

        pager.setOffscreenPageLimit(10);

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
                createCategoriesRU();
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

        for (int i=0; i<categoryTables.size(); i++){
            IngredientFragment m = new IngredientFragment();
            if (categoryTables.get(i).getNum()==0){
                ingredients = ingredientDB.getAll();
            }
            else {
                ingredients = ingredientDB.getCategory(categoryTables.get(i).getNum());
            }
            m.setIngrbycategory(ingredientDB.copyFromRealm(ingredients));
            adapter.addFragment(m, categoryTables.get(i).getName());
        }

        pager.setAdapter(adapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                FragmentInterface fragment = (FragmentInterface) adapter.instantiateItem(pager, position);
                if (fragment != null) {
                    fragment.fragmentBecameVisible();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(pager);

//        tabLayout.setTabTextColors( лишнее - есть в XML
//                ContextCompat.getColor(this, R.color.tabLayoutTextColorUnselected),
//                ContextCompat.getColor(this, R.color.tabLayoutTextColorSelected)
//        );
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
        else if (id==R.id.fr6){
            intent = new Intent(this, CategoriesActivity.class);
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

//        newIngredient.add(new Ingredient(1, "грибы", R.drawable., 0));

//        newIngredient.add(new Ingredient(2, "злаки", R.drawable., 0));

//        newIngredient.add(new Ingredient(3, "крупы", R.drawable., 0));

        newIngredient.add(new Ingredient(4, "спагетти", R.drawable.spagetti, 0));

        newIngredient.add(new Ingredient(5, "масло оливковое", R.drawable.maslo_oliv, 0));
        newIngredient.add(new Ingredient(5, "масло подсолнечное", R.drawable.maslo_podsoln, 0));
        newIngredient.add(new Ingredient(5, "масло растительное", R.drawable.oils, 0));

        newIngredient.add(new Ingredient(6, "молоко", R.drawable.milk, 0));
        newIngredient.add(new Ingredient(6, "сметана", R.drawable.sour_cream, 0));

        newIngredient.add(new Ingredient(7, "крабовые палочки", R.drawable.krab_pal, 0));

        newIngredient.add(new Ingredient(8, "мука", R.drawable.muka, 0));

        newIngredient.add(new Ingredient(9, "ветчина", R.drawable.vetchina, 0));
        newIngredient.add(new Ingredient(9, "говядина", R.drawable.beef, 0));
        newIngredient.add(new Ingredient(9, "колбаса", R.drawable.kolbasi, 0));
        newIngredient.add(new Ingredient(9, "колбаса вареная", R.drawable.kolbas_var, 0));
        newIngredient.add(new Ingredient(9, "колбаса копченая", R.drawable.kolbasa_kopch, 0));
        newIngredient.add(new Ingredient(9, "мясо", R.drawable.meat, 0));
        newIngredient.add(new Ingredient(9, "мясо копченое", R.drawable.maso_kopch, 0));
        newIngredient.add(new Ingredient(9, "сарделька", R.drawable.sardelki, 0));
        newIngredient.add(new Ingredient(9, "свинина", R.drawable.pork, 0));
        newIngredient.add(new Ingredient(9, "сосиска", R.drawable.sosiska, 0));

        newIngredient.add(new Ingredient(10, "пиво светлое", R.drawable.beer_light, 0));
        newIngredient.add(new Ingredient(10, "сок лимона", R.drawable.lemon_juice, 0));
        newIngredient.add(new Ingredient(10, "сок яблочный", R.drawable.apple_juice, 0));

        newIngredient.add(new Ingredient(11, "зелень", R.drawable.zelen, 0));
        newIngredient.add(new Ingredient(11, "горошек консервированный", R.drawable.canned_pea, 0));
        newIngredient.add(new Ingredient(11, "кабачок", R.drawable.cabachok, 0));
        newIngredient.add(new Ingredient(11, "капуста", R.drawable.cabbage, 0));
        newIngredient.add(new Ingredient(11, "капуста пекинская", R.drawable.pekin_kap, 0));
        newIngredient.add(new Ingredient(11, "картофель", R.drawable.potato, 0));
        newIngredient.add(new Ingredient(11, "корень сельдерея", R.drawable.selder_root, 0));
        newIngredient.add(new Ingredient(11, "кукуруза консервированная", R.drawable.canned_corn, 0));
        newIngredient.add(new Ingredient(11, "лук", R.drawable.onion, 0));
        newIngredient.add(new Ingredient(11, "лук зеленый", R.drawable.onion_green, 0));
        newIngredient.add(new Ingredient(11, "лук репчатый", R.drawable.luk_repch, 0));
        newIngredient.add(new Ingredient(11, "малосольный огурец", R.drawable.malosol, 0));
        newIngredient.add(new Ingredient(11, "морковь", R.drawable.carrot, 0));
        newIngredient.add(new Ingredient(11, "огурец", R.drawable.cucumber, 0));
        newIngredient.add(new Ingredient(11, "перец болгарский", R.drawable.perez_bolg, 0));
        newIngredient.add(new Ingredient(11, "помидор", R.drawable.tomat, 0));
        newIngredient.add(new Ingredient(11, "соленый огурец", R.drawable.soleniy, 0));
        newIngredient.add(new Ingredient(11, "укроп", R.drawable.ukrop, 0));
        newIngredient.add(new Ingredient(11, "фасоль красная консервированная", R.drawable.fasol_red, 0));
        newIngredient.add(new Ingredient(11, "чеснок", R.drawable.garlic, 0));

//        newIngredient.add(new Ingredient(12, "орехи", R.drawable.));

        newIngredient.add(new Ingredient(13, "душистый перец", R.drawable.perec_dush, 0));
        newIngredient.add(new Ingredient(13, "корица", R.drawable.koritsa, 0));
        newIngredient.add(new Ingredient(13, "лавровый лист", R.drawable.lavr, 0));
        newIngredient.add(new Ingredient(13, "перец молотый", R.drawable.perez_molot, 0));
        newIngredient.add(new Ingredient(13, "перец молотый черный", R.drawable.perez_black, 0));
        newIngredient.add(new Ingredient(13, "сахар", R.drawable.sugar, 0));
        newIngredient.add(new Ingredient(13, "соль", R.drawable.salt, 0));
        newIngredient.add(new Ingredient(13, "уксус", R.drawable.uksus, 0));

        newIngredient.add(new Ingredient(14, "куриная грудка копченая", R.drawable.grudka_kopch, 0));
        newIngredient.add(new Ingredient(14, "куриное филе", R.drawable.chicken_fillet, 0));

//        newIngredient.add(new Ingredient(15, "рыба", R.drawable.));

        newIngredient.add(new Ingredient(16, "кетчуп", R.drawable.ketchup, 0));
        newIngredient.add(new Ingredient(16, "майонез", R.drawable.mayonese, 0));
        newIngredient.add(new Ingredient(16, "томатная паста", R.drawable.tomat_pasta, 0));

        newIngredient.add(new Ingredient(17, "плавленый сыр", R.drawable.plavl_sir, 0));
        newIngredient.add(new Ingredient(17, "сыр", R.drawable.cheese, 0));
        newIngredient.add(new Ingredient(17, "сыр сливочный", R.drawable.sir_sliv, 0));

        newIngredient.add(new Ingredient(18, "ананас консервированный", R.drawable.ananas_kons, 0));
        newIngredient.add(new Ingredient(18, "киви", R.drawable.qiwi, 0));
        newIngredient.add(new Ingredient(18, "яблоко", R.drawable.apple, 0));

        newIngredient.add(new Ingredient(19, "батон", R.drawable.baton, 0));
        newIngredient.add(new Ingredient(19, "сухари из белого хлеба", R.drawable.suhari_bel, 0));
        newIngredient.add(new Ingredient(19, "французский багет", R.drawable.baget_fr, 0));

        newIngredient.add(new Ingredient(20, "яйцо куриное", R.drawable.eggs, 0));

        ingredientDB.copyOrUpdate(newIngredient);
    }

    private void createCategoryTablesRU(){
        ArrayList<CategoryTable> categoryTables = new ArrayList<>();

        categoryTables.add(new CategoryTable(0, "Все"));
        categoryTables.add(new CategoryTable(1, "Грибы"));
        categoryTables.add(new CategoryTable(2, "Злаки"));
        categoryTables.add(new CategoryTable(3, "Крупы"));
        categoryTables.add(new CategoryTable(4, "Макароны"));
        categoryTables.add(new CategoryTable(5, "Масла растит."));
        categoryTables.add(new CategoryTable(6, "Молочное"));
        categoryTables.add(new CategoryTable(7, "Морепр-ты"));
        categoryTables.add(new CategoryTable(8, "Мука"));
        categoryTables.add(new CategoryTable(9, "Мясное"));
        categoryTables.add(new CategoryTable(10, "Напитки"));
        categoryTables.add(new CategoryTable(11, "Овощи"));
        categoryTables.add(new CategoryTable(12, "Орехи"));
        categoryTables.add(new CategoryTable(13, "Приправы"));
        categoryTables.add(new CategoryTable(14, "Птица"));
        categoryTables.add(new CategoryTable(15, "Рыба"));
        categoryTables.add(new CategoryTable(16, "Соусы"));
        categoryTables.add(new CategoryTable(17, "Сыр"));
        categoryTables.add(new CategoryTable(18, "Фрукты"));
        categoryTables.add(new CategoryTable(19, "Хлебобул. изд."));
        categoryTables.add(new CategoryTable(20, "Яйца"));


        ingredientDB.copyOrUpdateCategoryTable(categoryTables);
    }
    private void createCategoriesRU(){
        ArrayList<Categories> categories = new ArrayList<>();
        categories.add(new Categories("блины", R.drawable.pancaces));
        categories.add(new Categories("блюда на завтрак", R.drawable.zavtrak));
        categories.add(new Categories("вторые блюда", R.drawable.vtor_bludo));
        categories.add(new Categories("салаты", R.drawable.salads));
        categories.add(new Categories("супы", R.drawable.soups));
        ingredientDB.copyOrUpdateCategories(categories);
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
//        ingredientDB.copyOrUpdateCategoryTable(categoryTables);
//    }
}
