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

import com.github.jurassicspb.cookbooksearchbyingredients.custom_dialogs.CustomDialog1;
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

public class IngedientTablayoutActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private IngredientDatabase ingredientDB;
    private List<Ingredient> ingredients;
    private List<CategoryTable> categoryTables;
    private DrawerLayout drawer;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SelectedIngredient.getSelectedIngredient().clear();
        SelectedIngredient.getSelectedImage().clear();
//        SelectedIngredient.resetCount();

        setContentView(R.layout.tablayout_with_viewpager);

        MyPreferences preferences = new MyPreferences(this);
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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
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
            if (toolbar.getChildAt(i) instanceof ImageButton) {
                toolbar.getChildAt(i).setScaleX(1.3f);
                toolbar.getChildAt(i).setScaleY(1.3f);
            }
        }

        ingredientDB = new IngredientDatabase();
//            deleteIngredients();
//            deleteRecipes();

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

        for (int i = 0; i < categoryTables.size(); i++) {
            IngredientFragment m = new IngredientFragment();
            if (categoryTables.get(i).getNum() == 0) {
                ingredients = ingredientDB.getAll();
            } else {
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


        if (preferences.getFlagAlert()) {
            new CustomDialog1(this).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.fr1) {
            intent = new Intent(IngedientTablayoutActivity.this, FavoritesActivity.class);
            startActivity(intent);
        } else if (id == R.id.fr2) {
            intent = new Intent(IngedientTablayoutActivity.this, FullListActivity.class);
            startActivity(intent);
            new Handler().post(() -> {
                intent = new Intent(IngedientTablayoutActivity.this, ProgressBarActivity.class);
                startActivity(intent);
            });

        } else if (id == R.id.fr3) {
            finish();
        } else if (id == R.id.fr4) {
            intent = new Intent(this, WeightsAndMeasures.class);
            startActivity(intent);
        } else if (id == R.id.fr5) {
            intent = new Intent(this, CookingTime.class);
            startActivity(intent);
        } else if (id == R.id.fr6) {
            intent = new Intent(this, CategoriesActivity.class);
            startActivity(intent);
        } else if (id == R.id.fr7) {
            intent = new Intent(this, IngredientFavoritesActivity.class);
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
                intent = new Intent(this, IngedientTablayoutActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createIngredientsRU() {
        ArrayList<Ingredient> newIngredient = new ArrayList<>();

        newIngredient.add(new Ingredient(1, "белые грибы", R.drawable.beliy, 0, 0));
        newIngredient.add(new Ingredient(1, "грибы", R.drawable.mushrooms, 0, 0));
        newIngredient.add(new Ingredient(1, "грибы сушеные", R.drawable.grib_suh, 0, 0));
        newIngredient.add(new Ingredient(1, "шампиньоны", R.drawable.shamp, 0, 0));
        newIngredient.add(new Ingredient(1, "шампиньоны консервированные", R.drawable.shamp_kons, 0, 0));

        newIngredient.add(new Ingredient(2, "бульон", R.drawable.buljen, 0, 0));
        newIngredient.add(new Ingredient(2, "вино сухое", R.drawable.wine_suh, 0, 0));
        newIngredient.add(new Ingredient(2, "вода", R.drawable.water, 0, 0));
        newIngredient.add(new Ingredient(2, "огуречный рассол", R.drawable.rassol, 0, 0));
        newIngredient.add(new Ingredient(2, "пиво светлое", R.drawable.beer_light, 0, 0));
        newIngredient.add(new Ingredient(2, "томатный сок", R.drawable.tomat_sok, 0, 0));
        newIngredient.add(new Ingredient(2, "яблочный сок", R.drawable.apple_juice, 0, 0));

        newIngredient.add(new Ingredient(3, "гречневая крупа", R.drawable.grech_krupa, 0, 0));
        newIngredient.add(new Ingredient(3, "отруби", R.drawable.otrubi, 0, 0));
        newIngredient.add(new Ingredient(3, "овсяные отруби", R.drawable.otrubi_ovs, 0, 0));
        newIngredient.add(new Ingredient(3, "овсяные хлопья", R.drawable.oves, 0, 0));
        newIngredient.add(new Ingredient(3, "манная крупа", R.drawable.manna, 0, 0));
        newIngredient.add(new Ingredient(3, "перловая крупа", R.drawable.perlov, 0, 0));
        newIngredient.add(new Ingredient(3, "рис", R.drawable.rice, 0, 0));

        newIngredient.add(new Ingredient(4, "спагетти", R.drawable.spagetti, 0, 0));

        newIngredient.add(new Ingredient(5, "масло оливковое", R.drawable.maslo_oliv, 0, 0));
        newIngredient.add(new Ingredient(5, "масло подсолнечное", R.drawable.maslo_podsoln, 0, 0));
        newIngredient.add(new Ingredient(5, "масло растительное", R.drawable.oils, 0, 0));

        newIngredient.add(new Ingredient(6, "йогурт натуральный", R.drawable.jogurt, 0, 0));
        newIngredient.add(new Ingredient(6, "кефир", R.drawable.kefir, 0, 0));
        newIngredient.add(new Ingredient(6, "маргарин", R.drawable.margarin, 0, 0));
        newIngredient.add(new Ingredient(6, "масло сливочное", R.drawable.maslo_sliv, 0, 0));
        newIngredient.add(new Ingredient(6, "молоко", R.drawable.milk, 0, 0));
        newIngredient.add(new Ingredient(6, "простокваша", R.drawable.prostokv, 0, 0));
        newIngredient.add(new Ingredient(6, "сгущенное молоко", R.drawable.sgusch_moloko, 0, 0));
        newIngredient.add(new Ingredient(6, "сливки", R.drawable.slivki, 0, 0));
        newIngredient.add(new Ingredient(6, "сливки жирные", R.drawable.slivki_zhir, 0, 0));
        newIngredient.add(new Ingredient(6, "сметана", R.drawable.sour_cream, 0, 0));
        newIngredient.add(new Ingredient(6, "творог", R.drawable.tvorog, 0, 0));

        newIngredient.add(new Ingredient(7, "кальмар", R.drawable.kalmar, 0, 0));
        newIngredient.add(new Ingredient(7, "крабовое мясо", R.drawable.krab_meat, 0, 0));
        newIngredient.add(new Ingredient(7, "крабовые палочки", R.drawable.krab_pal, 0, 0));
        newIngredient.add(new Ingredient(7, "креветки", R.drawable.krevetki, 0, 0));

        newIngredient.add(new Ingredient(8, "мука", R.drawable.muka, 0, 0));
        newIngredient.add(new Ingredient(8, "мука овсяная", R.drawable.oves_muka, 0, 0));
        newIngredient.add(new Ingredient(8, "мука пшеничная", R.drawable.muka_pshen, 0, 0));

        newIngredient.add(new Ingredient(9, "ветчина", R.drawable.vetchina, 0, 0));
        newIngredient.add(new Ingredient(9, "говядина", R.drawable.beef, 0, 0));
        newIngredient.add(new Ingredient(9, "колбаса", R.drawable.kolbasi, 0, 0));
        newIngredient.add(new Ingredient(9, "колбаса вареная", R.drawable.kolbas_var, 0, 0));
        newIngredient.add(new Ingredient(9, "колбаса копченая", R.drawable.kolbasa_kopch, 0, 0));
        newIngredient.add(new Ingredient(9, "мясо", R.drawable.meat, 0, 0));
        newIngredient.add(new Ingredient(9, "мясо копченое", R.drawable.maso_kopch, 0, 0));
        newIngredient.add(new Ingredient(9, "охотничьи колбаски", R.drawable.ohot_kolb, 0, 0));
        newIngredient.add(new Ingredient(9, "печень", R.drawable.liver, 0, 0));
        newIngredient.add(new Ingredient(9, "салями", R.drawable.salami, 0, 0));
        newIngredient.add(new Ingredient(9, "сарделька", R.drawable.sardelki, 0, 0));
        newIngredient.add(new Ingredient(9, "свинина", R.drawable.pork, 0, 0));
        newIngredient.add(new Ingredient(9, "сосиска", R.drawable.sosiska, 0, 0));
        newIngredient.add(new Ingredient(9, "телятина", R.drawable.telatina, 0, 0));
        newIngredient.add(new Ingredient(9, "фарш бараний", R.drawable.farsh_baran, 0, 0));
        newIngredient.add(new Ingredient(9, "фарш говяжий", R.drawable.beef_mince, 0, 0));
        newIngredient.add(new Ingredient(9, "фарш мясной", R.drawable.farsch, 0, 0));
        newIngredient.add(new Ingredient(9, "фарш свиной", R.drawable.pork_mince, 0, 0));

        newIngredient.add(new Ingredient(10, "баклажан", R.drawable.baklazhan, 0, 0));
        newIngredient.add(new Ingredient(10, "болгарский перец", R.drawable.perez_bolg, 0, 0));
        newIngredient.add(new Ingredient(10, "зелень", R.drawable.zelen, 0, 0));
        newIngredient.add(new Ingredient(10, "горошек консервированный", R.drawable.canned_pea, 0, 0));
        newIngredient.add(new Ingredient(10, "кабачок", R.drawable.cabachok, 0, 0));
        newIngredient.add(new Ingredient(10, "капуста", R.drawable.cabbage, 0, 0));
        newIngredient.add(new Ingredient(10, "капуста пекинская", R.drawable.pekin_kap, 0, 0));
        newIngredient.add(new Ingredient(10, "картофель", R.drawable.potato, 0, 0));
        newIngredient.add(new Ingredient(10, "корень сельдерея", R.drawable.selder_root, 0, 0));
        newIngredient.add(new Ingredient(10, "кукуруза консервированная", R.drawable.canned_corn, 0, 0));
        newIngredient.add(new Ingredient(10, "лук", R.drawable.onion, 0, 0));
        newIngredient.add(new Ingredient(10, "лук зеленый", R.drawable.onion_green, 0, 0));
        newIngredient.add(new Ingredient(10, "лук красный", R.drawable.onion_red, 0, 0));
        newIngredient.add(new Ingredient(10, "лук репчатый", R.drawable.luk_repch, 0, 0));
        newIngredient.add(new Ingredient(10, "малосольный огурец", R.drawable.malosol, 0, 0));
        newIngredient.add(new Ingredient(10, "маслины", R.drawable.maslini, 0, 0));
        newIngredient.add(new Ingredient(10, "морковь", R.drawable.carrot, 0, 0));
        newIngredient.add(new Ingredient(10, "огурец", R.drawable.cucumber, 0, 0));
        newIngredient.add(new Ingredient(10, "петрушка", R.drawable.petrushka, 0, 0));
        newIngredient.add(new Ingredient(10, "помидор", R.drawable.tomat, 0, 0));
        newIngredient.add(new Ingredient(10, "помидоры черри", R.drawable.pomidor_cherry, 0, 0));
        newIngredient.add(new Ingredient(10, "салат", R.drawable.salat, 0, 0));
        newIngredient.add(new Ingredient(10, "соленый огурец", R.drawable.soleniy, 0, 0));
        newIngredient.add(new Ingredient(10, "тыква", R.drawable.tikva, 0, 0));
        newIngredient.add(new Ingredient(10, "укроп", R.drawable.ukrop, 0, 0));
        newIngredient.add(new Ingredient(10, "фасоль красная консервированная", R.drawable.fasol_red, 0, 0));
        newIngredient.add(new Ingredient(10, "цветная капуста", R.drawable.cvet_kapusta, 0, 0));
        newIngredient.add(new Ingredient(10, "цукини", R.drawable.zuccini, 0, 0));
        newIngredient.add(new Ingredient(10, "чеснок", R.drawable.garlic, 0, 0));

        newIngredient.add(new Ingredient(11, "грецкие орехи", R.drawable.grec_oreh, 0, 0));
        newIngredient.add(new Ingredient(11, "кедровые орехи", R.drawable.kedr, 0, 0));
        newIngredient.add(new Ingredient(11, "мускатный орех", R.drawable.muskat, 0, 0));
        newIngredient.add(new Ingredient(11, "орехи", R.drawable.orehi, 0, 0));
        newIngredient.add(new Ingredient(11, "миндаль", R.drawable.mindal, 0, 0));

        newIngredient.add(new Ingredient(12, "базилик сушеный", R.drawable.bazilik, 0, 0));
        newIngredient.add(new Ingredient(12, "бульонный кубик", R.drawable.kubik, 0, 0));
        newIngredient.add(new Ingredient(12, "ванилин", R.drawable.vanilin, 0, 0));
        newIngredient.add(new Ingredient(12, "ванильный сахар", R.drawable.vanil_sugar, 0, 0));
        newIngredient.add(new Ingredient(12, "дрожжи", R.drawable.drozzi, 0, 0));
        newIngredient.add(new Ingredient(12, "душистый перец", R.drawable.perec_dush, 0, 0));
        newIngredient.add(new Ingredient(12, "желатин", R.drawable.jelatin, 0, 0));
        newIngredient.add(new Ingredient(12, "имбирь молотый", R.drawable.imbir_mol, 0, 0));
        newIngredient.add(new Ingredient(12, "итальянские травы", R.drawable.ital_trav, 0, 0));
        newIngredient.add(new Ingredient(12, "кардамон", R.drawable.kardamon, 0, 0));
        newIngredient.add(new Ingredient(12, "карри", R.drawable.karri, 0, 0));
        newIngredient.add(new Ingredient(12, "кокосовая стружка", R.drawable.kokos_str, 0, 0));
        newIngredient.add(new Ingredient(12, "кориандр молотый", R.drawable.koriandr, 0, 0));
        newIngredient.add(new Ingredient(12, "корица", R.drawable.koritsa, 0, 0));
        newIngredient.add(new Ingredient(12, "крахмал", R.drawable.krahmal, 0, 0));
        newIngredient.add(new Ingredient(12, "кунжут", R.drawable.kunzut, 0, 0));
        newIngredient.add(new Ingredient(12, "лавровый лист", R.drawable.lavr, 0, 0));
        newIngredient.add(new Ingredient(12, "лимонная кислота", R.drawable.limon_kisl, 0, 0));
        newIngredient.add(new Ingredient(12, "мак", R.drawable.mak, 0, 0));
        newIngredient.add(new Ingredient(12, "паприка сладкая", R.drawable.paprika_sweet, 0, 0));
        newIngredient.add(new Ingredient(12, "перец молотый", R.drawable.perez_molot, 0, 0));
        newIngredient.add(new Ingredient(12, "перец молотый красный", R.drawable.perec_mol_kr, 0, 0));
        newIngredient.add(new Ingredient(12, "перец молотый черный", R.drawable.perez_black, 0, 0));
        newIngredient.add(new Ingredient(12, "перец черный горошек", R.drawable.pepper_gor, 0, 0));
        newIngredient.add(new Ingredient(12, "прованские травы", R.drawable.provans, 0, 0));
        newIngredient.add(new Ingredient(12, "разрыхлитель", R.drawable.razrihl, 0, 0));
        newIngredient.add(new Ingredient(12, "сахар", R.drawable.sugar, 0, 0));
        newIngredient.add(new Ingredient(12, "сахарозаменитель", R.drawable.sweetener, 0, 0));
        newIngredient.add(new Ingredient(12, "сахарная пудра", R.drawable.pudra, 0, 0));
        newIngredient.add(new Ingredient(12, "смесь перцев", R.drawable.smes_perc, 0, 0));
        newIngredient.add(new Ingredient(12, "сода", R.drawable.soda, 0, 0));
        newIngredient.add(new Ingredient(12, "сок лимона", R.drawable.lemon_juice, 0, 0));
        newIngredient.add(new Ingredient(12, "соль", R.drawable.salt, 0, 0));
        newIngredient.add(new Ingredient(12, "стевия", R.drawable.stevia, 0, 0));
        newIngredient.add(new Ingredient(12, "тимьян (чабрец)", R.drawable.timjan, 0, 0));
        newIngredient.add(new Ingredient(12, "уксус", R.drawable.uksus, 0, 0));
        newIngredient.add(new Ingredient(12, "хмели-сунели", R.drawable.hmeli, 0, 0));
        newIngredient.add(new Ingredient(12, "цедра лимона", R.drawable.cedra, 0, 0));
        newIngredient.add(new Ingredient(12, "яблочный уксус", R.drawable.jabl_uksus, 0, 0));

        newIngredient.add(new Ingredient(13, "куриная грудка", R.drawable.kur_grud, 0, 0));
        newIngredient.add(new Ingredient(13, "куриная грудка копченая", R.drawable.grudka_kopch, 0, 0));
        newIngredient.add(new Ingredient(13, "куриное филе", R.drawable.chicken_fillet, 0, 0));
        newIngredient.add(new Ingredient(13, "курица", R.drawable.chicken, 0, 0));
        newIngredient.add(new Ingredient(13, "курица копченая", R.drawable.chick_kopch, 0, 0));
        newIngredient.add(new Ingredient(13, "фарш индейки", R.drawable.farsch_ind, 0, 0));
        newIngredient.add(new Ingredient(13, "фарш куриный", R.drawable.farsh_kur, 0, 0));
        newIngredient.add(new Ingredient(13, "филе индейки", R.drawable.file_ind, 0, 0));

        newIngredient.add(new Ingredient(14, "рыба", R.drawable.fish, 0, 0));
        newIngredient.add(new Ingredient(14, "рыбные консервы", R.drawable.rib_kons, 0, 0));
        newIngredient.add(new Ingredient(14, "скумбрия", R.drawable.skumbr, 0, 0));
        newIngredient.add(new Ingredient(14, "скумбрия консервированная", R.drawable.skumbr_kons, 0, 0));
        newIngredient.add(new Ingredient(14, "треска", R.drawable.treska, 0, 0));
        newIngredient.add(new Ingredient(14, "тунец консервированный", R.drawable.tuna_kons, 0, 0));
        newIngredient.add(new Ingredient(14, "филе белой рыбы", R.drawable.white_fish, 0, 0));

        newIngredient.add(new Ingredient(15, "мед", R.drawable.honey, 0, 0));

        newIngredient.add(new Ingredient(16, "горчица", R.drawable.gorchiza, 0, 0));
        newIngredient.add(new Ingredient(16, "горчица дижонская", R.drawable.dijon_gor, 0, 0));
        newIngredient.add(new Ingredient(16, "кетчуп", R.drawable.ketchup, 0, 0));
        newIngredient.add(new Ingredient(16, "майонез", R.drawable.mayonese, 0, 0));
        newIngredient.add(new Ingredient(16, "майонез легкий", R.drawable.mayon_light, 0, 0));
        newIngredient.add(new Ingredient(16, "острый соус чили", R.drawable.chili, 0, 0));
        newIngredient.add(new Ingredient(16, "соевый соус", R.drawable.soev_sous, 0, 0));
        newIngredient.add(new Ingredient(16, "томатная паста", R.drawable.tomat_pasta, 0, 0));

        newIngredient.add(new Ingredient(17, "изюм", R.drawable.izum, 0, 0));
        newIngredient.add(new Ingredient(17, "финики", R.drawable.finiki, 0, 0));
        newIngredient.add(new Ingredient(17, "чернослив", R.drawable.prunes, 0, 0));

        newIngredient.add(new Ingredient(18, "моцарелла", R.drawable.mozarella, 0, 0));
        newIngredient.add(new Ingredient(18, "пармезан", R.drawable.parmezan, 0, 0));
        newIngredient.add(new Ingredient(18, "плавленый сыр", R.drawable.plavl_sir, 0, 0));
        newIngredient.add(new Ingredient(18, "сыр", R.drawable.cheese, 0, 0));
        newIngredient.add(new Ingredient(18, "сыр нежирный", R.drawable.sir_nofat, 0, 0));
        newIngredient.add(new Ingredient(18, "сыр сливочный", R.drawable.sir_sliv, 0, 0));
        newIngredient.add(new Ingredient(18, "фета", R.drawable.feta, 0, 0));

        newIngredient.add(new Ingredient(19, "ананас", R.drawable.ananas, 0, 0));
        newIngredient.add(new Ingredient(19, "ананас консервированный", R.drawable.ananas_kons, 0, 0));
        newIngredient.add(new Ingredient(19, "банан", R.drawable.banan, 0, 0));
        newIngredient.add(new Ingredient(19, "груша", R.drawable.grusha, 0, 0));
        newIngredient.add(new Ingredient(19, "киви", R.drawable.qiwi, 0, 0));
        newIngredient.add(new Ingredient(19, "лимон", R.drawable.lemon, 0, 0));
        newIngredient.add(new Ingredient(19, "яблоко", R.drawable.apple, 0, 0));

        newIngredient.add(new Ingredient(20, "батон", R.drawable.baton, 0, 0));
        newIngredient.add(new Ingredient(20, "белый хлеб", R.drawable.bel_hleb, 0, 0));
        newIngredient.add(new Ingredient(20, "лаваш тонкий", R.drawable.lavash, 0, 0));
        newIngredient.add(new Ingredient(20, "сухари панировочные", R.drawable.suh_panir, 0, 0));
        newIngredient.add(new Ingredient(20, "сухарики", R.drawable.suhar, 0, 0));
        newIngredient.add(new Ingredient(20, "сухарики из белого хлеба", R.drawable.suhari_bel, 0, 0));
        newIngredient.add(new Ingredient(20, "французский багет", R.drawable.baget_fr, 0, 0));

        newIngredient.add(new Ingredient(21, "черника", R.drawable.chernika, 0, 0));
        newIngredient.add(new Ingredient(21, "ягоды", R.drawable.yagodi, 0, 0));

        newIngredient.add(new Ingredient(22, "яйцо", R.drawable.egg, 0, 0));
        newIngredient.add(new Ingredient(22, "яйцо куриное", R.drawable.eggs, 0, 0));

        ingredientDB.copyOrUpdate(newIngredient);
    }

    private void createCategoryTablesRU() {
        ArrayList<CategoryTable> categoryTables = new ArrayList<>();

        categoryTables.add(new CategoryTable(0, "Все"));
        categoryTables.add(new CategoryTable(1, "Грибы"));
        categoryTables.add(new CategoryTable(2, "Жидкости"));
        categoryTables.add(new CategoryTable(3, "Крупы и злаки"));
        categoryTables.add(new CategoryTable(4, "Макароны"));
        categoryTables.add(new CategoryTable(5, "Масла растит."));
        categoryTables.add(new CategoryTable(6, "Молочное"));
        categoryTables.add(new CategoryTable(7, "Морепр-ты"));
        categoryTables.add(new CategoryTable(8, "Мука"));
        categoryTables.add(new CategoryTable(9, "Мясное"));
        categoryTables.add(new CategoryTable(10, "Овощи"));
        categoryTables.add(new CategoryTable(11, "Орехи"));
        categoryTables.add(new CategoryTable(12, "Приправы"));
        categoryTables.add(new CategoryTable(13, "Птица"));
        categoryTables.add(new CategoryTable(14, "Рыба"));
        categoryTables.add(new CategoryTable(15, "Сладости"));
        categoryTables.add(new CategoryTable(16, "Соусы"));
        categoryTables.add(new CategoryTable(17, "Сухофрукты"));
        categoryTables.add(new CategoryTable(18, "Сыры"));
        categoryTables.add(new CategoryTable(19, "Фрукты"));
        categoryTables.add(new CategoryTable(20, "Хлебобул. изд."));
        categoryTables.add(new CategoryTable(21, "Ягоды"));
        categoryTables.add(new CategoryTable(22, "Яйца"));


        ingredientDB.copyOrUpdateCategoryTable(categoryTables);
    }

    private void createCategoriesRU() {
        ArrayList<Categories> categories = new ArrayList<>();
        categories.add(new Categories("Блины и оладьи", R.drawable.pancaces));
        categories.add(new Categories("Блюда для мультиварки", R.drawable.multi));
        categories.add(new Categories("Блюда на завтрак", R.drawable.zavtrak));
        categories.add(new Categories("Вторые блюда", R.drawable.vtor_bludo));
        categories.add(new Categories("Гарниры", R.drawable.garnir));
        categories.add(new Categories("Десерты", R.drawable.desserts));
        categories.add(new Categories("Заготовки", R.drawable.zagotov));
        categories.add(new Categories("Закуски", R.drawable.zakuski));
        categories.add(new Categories("Здоровое питание", R.drawable.polezn));
        categories.add(new Categories("Каши", R.drawable.kashi));
        categories.add(new Categories("Напитки", R.drawable.drinks));
        categories.add(new Categories("Несладкая выпечка", R.drawable.nesl_vip));
        categories.add(new Categories("Рыба и морепродукты", R.drawable.bluda_fish));
        categories.add(new Categories("Салаты", R.drawable.salads));
        categories.add(new Categories("Сладкая выпечка", R.drawable.vipechka));
        categories.add(new Categories("Соусы", R.drawable.sauces));
        categories.add(new Categories("Супы", R.drawable.soups));
        ingredientDB.copyOrUpdateCategories(categories);
    }

    private void performCategoryTables() {
        categoryTables = ingredientDB.getAllCategoryTables();
    }

    public void deleteIngredients() {
        ArrayList<Ingredient> newIngredient = new ArrayList<>();
        ingredientDB.delete(newIngredient);
    }

    public void deleteRecipes() {
        ArrayList<Recipe> newRecipe = new ArrayList<>();
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
