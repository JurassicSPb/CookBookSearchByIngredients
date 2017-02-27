package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
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
        if(getResources().getBoolean(R.bool.portrait_for_phones)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

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

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelableArrayList("ingredientList", (ArrayList) ingredients);
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
                if (SelectedIngredient.showCount()==0){
//                if (SelectedIngredient.showCount() == 0) {
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

        newIngredient.add(new Ingredient(2, "овсяные хлопья", R.drawable.oves, 0));

        newIngredient.add(new Ingredient(3, "спагетти", R.drawable.spagetti, 0));

        newIngredient.add(new Ingredient(4, "масло оливковое", R.drawable.maslo_oliv, 0));
        newIngredient.add(new Ingredient(4, "масло подсолнечное", R.drawable.maslo_podsoln, 0));
        newIngredient.add(new Ingredient(4, "масло растительное", R.drawable.oils, 0));

        newIngredient.add(new Ingredient(5, "йогурт натуральный", R.drawable.jogurt, 0));
        newIngredient.add(new Ingredient(5, "молоко", R.drawable.milk, 0));
        newIngredient.add(new Ingredient(5, "сметана", R.drawable.sour_cream, 0));

        newIngredient.add(new Ingredient(6, "кальмар", R.drawable.kalmar, 0));
        newIngredient.add(new Ingredient(6, "крабовое мясо", R.drawable.krab_meat, 0));
        newIngredient.add(new Ingredient(6, "крабовые палочки", R.drawable.krab_pal, 0));
        newIngredient.add(new Ingredient(6, "креветки", R.drawable.krevetki, 0));

        newIngredient.add(new Ingredient(7, "овсяная мука", R.drawable.oves_muka, 0));
        newIngredient.add(new Ingredient(7, "мука", R.drawable.muka, 0));

        newIngredient.add(new Ingredient(8, "ветчина", R.drawable.vetchina, 0));
        newIngredient.add(new Ingredient(8, "говядина", R.drawable.beef, 0));
        newIngredient.add(new Ingredient(8, "колбаса", R.drawable.kolbasi, 0));
        newIngredient.add(new Ingredient(8, "колбаса вареная", R.drawable.kolbas_var, 0));
        newIngredient.add(new Ingredient(8, "колбаса копченая", R.drawable.kolbasa_kopch, 0));
        newIngredient.add(new Ingredient(8, "мясо", R.drawable.meat, 0));
        newIngredient.add(new Ingredient(8, "мясо копченое", R.drawable.maso_kopch, 0));
        newIngredient.add(new Ingredient(8, "сарделька", R.drawable.sardelki, 0));
        newIngredient.add(new Ingredient(8, "свинина", R.drawable.pork, 0));
        newIngredient.add(new Ingredient(8, "сосиска", R.drawable.sosiska, 0));

        newIngredient.add(new Ingredient(9, "вода", R.drawable.water, 0));
        newIngredient.add(new Ingredient(9, "пиво светлое", R.drawable.beer_light, 0));
        newIngredient.add(new Ingredient(9, "яблочный сок", R.drawable.apple_juice, 0));

        newIngredient.add(new Ingredient(10, "болгарский перец", R.drawable.perez_bolg, 0));
        newIngredient.add(new Ingredient(10, "зелень", R.drawable.zelen, 0));
        newIngredient.add(new Ingredient(10, "горошек консервированный", R.drawable.canned_pea, 0));
        newIngredient.add(new Ingredient(10, "кабачок", R.drawable.cabachok, 0));
        newIngredient.add(new Ingredient(10, "капуста", R.drawable.cabbage, 0));
        newIngredient.add(new Ingredient(10, "капуста пекинская", R.drawable.pekin_kap, 0));
        newIngredient.add(new Ingredient(10, "картофель", R.drawable.potato, 0));
        newIngredient.add(new Ingredient(10, "корень сельдерея", R.drawable.selder_root, 0));
        newIngredient.add(new Ingredient(10, "кукуруза консервированная", R.drawable.canned_corn, 0));
        newIngredient.add(new Ingredient(10, "лук", R.drawable.onion, 0));
        newIngredient.add(new Ingredient(10, "лук зеленый", R.drawable.onion_green, 0));
        newIngredient.add(new Ingredient(10, "лук репчатый", R.drawable.luk_repch, 0));
        newIngredient.add(new Ingredient(10, "малосольный огурец", R.drawable.malosol, 0));
        newIngredient.add(new Ingredient(10, "маслины без косточки", R.drawable.maslini, 0));
        newIngredient.add(new Ingredient(10, "морковь", R.drawable.carrot, 0));
        newIngredient.add(new Ingredient(10, "огурец", R.drawable.cucumber, 0));
        newIngredient.add(new Ingredient(10, "петрушка", R.drawable.petrushka, 0));
        newIngredient.add(new Ingredient(10, "помидор", R.drawable.tomat, 0));
        newIngredient.add(new Ingredient(10, "салат", R.drawable.salat, 0));
        newIngredient.add(new Ingredient(10, "соленый огурец", R.drawable.soleniy, 0));
        newIngredient.add(new Ingredient(10, "укроп", R.drawable.ukrop, 0));
        newIngredient.add(new Ingredient(10, "фасоль красная консервированная", R.drawable.fasol_red, 0));
        newIngredient.add(new Ingredient(10, "цветная капуста", R.drawable.cvet_kapusta, 0));
        newIngredient.add(new Ingredient(10, "чеснок", R.drawable.garlic, 0));

        newIngredient.add(new Ingredient(11, "грецкие орехи", R.drawable.grec_oreh, 0));
        newIngredient.add(new Ingredient(11, "кедровые орехи", R.drawable.kedr, 0));

        newIngredient.add(new Ingredient(12, "душистый перец", R.drawable.perec_dush, 0));
        newIngredient.add(new Ingredient(12, "итальянские травы", R.drawable.ital_trav, 0));
        newIngredient.add(new Ingredient(12, "кориандр молотый", R.drawable.koriandr, 0));
        newIngredient.add(new Ingredient(12, "корица", R.drawable.koritsa, 0));
        newIngredient.add(new Ingredient(12, "кунжут", R.drawable.kunzut, 0));
        newIngredient.add(new Ingredient(12, "лавровый лист", R.drawable.lavr, 0));
        newIngredient.add(new Ingredient(12, "паприка сладкая", R.drawable.paprika_sweet, 0));
        newIngredient.add(new Ingredient(12, "перец молотый", R.drawable.perez_molot, 0));
        newIngredient.add(new Ingredient(12, "перец молотый черный", R.drawable.perez_black, 0));
        newIngredient.add(new Ingredient(12, "сахар", R.drawable.sugar, 0));
        newIngredient.add(new Ingredient(12, "сахарозаменитель", R.drawable.sweetener, 0));
        newIngredient.add(new Ingredient(12, "сок лимона", R.drawable.lemon_juice, 0));
        newIngredient.add(new Ingredient(12, "соль", R.drawable.salt, 0));
        newIngredient.add(new Ingredient(12, "уксус", R.drawable.uksus, 0));

        newIngredient.add(new Ingredient(13, "куриная грудка копченая", R.drawable.grudka_kopch, 0));
        newIngredient.add(new Ingredient(13, "куриное филе", R.drawable.chicken_fillet, 0));

        newIngredient.add(new Ingredient(14, "треска", R.drawable.treska, 0));
        newIngredient.add(new Ingredient(14, "филе белой рыбы", R.drawable.white_fish, 0));

        newIngredient.add(new Ingredient(15, "кетчуп", R.drawable.ketchup, 0));
        newIngredient.add(new Ingredient(15, "майонез", R.drawable.mayonese, 0));
        newIngredient.add(new Ingredient(15, "майонез легкий", R.drawable.mayon_light, 0));
        newIngredient.add(new Ingredient(15, "острый соус чили", R.drawable.chili, 0));
        newIngredient.add(new Ingredient(15, "томатная паста", R.drawable.tomat_pasta, 0));

        newIngredient.add(new Ingredient(16, "плавленый сыр", R.drawable.plavl_sir, 0));
        newIngredient.add(new Ingredient(16, "сыр", R.drawable.cheese, 0));
        newIngredient.add(new Ingredient(16, "сыр пармезан", R.drawable.parmezan, 0));
        newIngredient.add(new Ingredient(16, "сыр сливочный", R.drawable.sir_sliv, 0));
        newIngredient.add(new Ingredient(16, "сыр фета", R.drawable.feta, 0));

        newIngredient.add(new Ingredient(17, "ананас консервированный", R.drawable.ananas_kons, 0));
        newIngredient.add(new Ingredient(17, "киви", R.drawable.qiwi, 0));
        newIngredient.add(new Ingredient(17, "финики", R.drawable.finiki, 0));
        newIngredient.add(new Ingredient(17, "яблоко", R.drawable.apple, 0));

        newIngredient.add(new Ingredient(18, "батон", R.drawable.baton, 0));
        newIngredient.add(new Ingredient(18, "сухарики", R.drawable.suhar, 0));
        newIngredient.add(new Ingredient(18, "сухарики из белого хлеба", R.drawable.suhari_bel, 0));
        newIngredient.add(new Ingredient(18, "французский багет", R.drawable.baget_fr, 0));

        newIngredient.add(new Ingredient(19, "яйцо куриное", R.drawable.eggs, 0));

        ingredientDB.copyOrUpdate(newIngredient);
    }

    private void createCategoryTablesRU(){
        ArrayList<CategoryTable> categoryTables = new ArrayList<>();

        categoryTables.add(new CategoryTable(0, "Все"));
        categoryTables.add(new CategoryTable(1, "Грибы"));
        categoryTables.add(new CategoryTable(2, "Крупы и злаки"));
        categoryTables.add(new CategoryTable(3, "Макароны"));
        categoryTables.add(new CategoryTable(4, "Масла растит."));
        categoryTables.add(new CategoryTable(5, "Молочное"));
        categoryTables.add(new CategoryTable(6, "Морепр-ты"));
        categoryTables.add(new CategoryTable(7, "Мука"));
        categoryTables.add(new CategoryTable(8, "Мясное"));
        categoryTables.add(new CategoryTable(9, "Напитки"));
        categoryTables.add(new CategoryTable(10, "Овощи"));
        categoryTables.add(new CategoryTable(11, "Орехи"));
        categoryTables.add(new CategoryTable(12, "Приправы"));
        categoryTables.add(new CategoryTable(13, "Птица"));
        categoryTables.add(new CategoryTable(14, "Рыба"));
        categoryTables.add(new CategoryTable(15, "Соусы"));
        categoryTables.add(new CategoryTable(16, "Сыр"));
        categoryTables.add(new CategoryTable(17, "Фрукты"));
        categoryTables.add(new CategoryTable(18, "Хлебобул. изд."));
        categoryTables.add(new CategoryTable(19, "Яйца"));


        ingredientDB.copyOrUpdateCategoryTable(categoryTables);
    }
    private void createCategoriesRU(){
        ArrayList<Categories> categories = new ArrayList<>();
        categories.add(new Categories("блины и оладьи", R.drawable.pancaces));
        categories.add(new Categories("блюда для мультиварки", R.drawable.multi));
        categories.add(new Categories("блюда на завтрак", R.drawable.zavtrak));
        categories.add(new Categories("вторые блюда", R.drawable.vtor_bludo));
        categories.add(new Categories("выпечка", R.drawable.vipechka));
        categories.add(new Categories("напитки", R.drawable.drinks));
        categories.add(new Categories("рыба и морепродукты", R.drawable.bluda_fish));
        categories.add(new Categories("салаты", R.drawable.salads));
        categories.add(new Categories("соусы", R.drawable.sauces));
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
