package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
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
import com.github.jurassicspb.cookbooksearchbyingredients.custom_dialogs.CustomDialog6;
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
    private List<CategoryTable> categoryTables;
    private DrawerLayout drawer;
    private Intent intent;
    private MyPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SelectedIngredient.getSelectedIngredient().clear();
        SelectedIngredient.getSelectedImage().clear();

        setContentView(R.layout.tablayout_with_viewpager);

        preferences = new MyPreferences(this);
//        preferences.clearFlag();

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
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        pager.setOffscreenPageLimit(8);

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
            preferences.setFlag(false);
        }
        performCategoryTables();

        List<Ingredient> ingredients;
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
            if (preferences.getFlagRating()) {
                new CustomDialog6(this).show();
            } else
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
        } else if (id == R.id.fr8) {
            intent = new Intent(this, IngredientToBuyActivity.class);
            startActivity(intent);
        } else if (id == R.id.fr9) {
            Uri address = Uri.parse("https://vk.com/club146092602");
            intent = new Intent(Intent.ACTION_VIEW, address);
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
        ArrayList<Ingredient> bufferIngredient = new ArrayList<>();

        bufferIngredient.add(new Ingredient(1, "белые грибы", R.drawable.beliy, 0, 0));
        bufferIngredient.add(new Ingredient(1, "грибы", R.drawable.mushrooms, 0, 0));
        bufferIngredient.add(new Ingredient(1, "лисички", R.drawable.lisichki, 0, 0));
        bufferIngredient.add(new Ingredient(1, "опята", R.drawable.opyata, 0, 0));
        bufferIngredient.add(new Ingredient(1, "опята маринованные", R.drawable.opjata_mar, 0, 0));
        bufferIngredient.add(new Ingredient(1, "сушеные грибы", R.drawable.grib_suh, 0, 0));
        bufferIngredient.add(new Ingredient(1, "шампиньоны", R.drawable.shamp, 0, 0));
        bufferIngredient.add(new Ingredient(1, "шампиньоны консервированные", R.drawable.shamp_kons, 0, 0));

        bufferIngredient.add(new Ingredient(2, "булгур", R.drawable.bulgur, 0, 0));
        bufferIngredient.add(new Ingredient(2, "гречневая крупа", R.drawable.grech_krupa, 0, 0));
        bufferIngredient.add(new Ingredient(2, "крупа", R.drawable.krupa, 0, 0));
        bufferIngredient.add(new Ingredient(2, "отруби", R.drawable.otrubi, 0, 0));
        bufferIngredient.add(new Ingredient(2, "овсяные отруби", R.drawable.otrubi_ovs, 0, 0));
        bufferIngredient.add(new Ingredient(2, "овсяные хлопья", R.drawable.oves, 0, 0));
        bufferIngredient.add(new Ingredient(2, "манная крупа", R.drawable.manna, 0, 0));
        bufferIngredient.add(new Ingredient(2, "перловая крупа", R.drawable.perlov, 0, 0));
        bufferIngredient.add(new Ingredient(2, "пшеничные отруби", R.drawable.otrubi_psh, 0, 0));
        bufferIngredient.add(new Ingredient(2, "рис ", R.drawable.rice, 0, 0));

        bufferIngredient.add(new Ingredient(3, "вермишель", R.drawable.vermishel, 0, 0));
        bufferIngredient.add(new Ingredient(3, "лапша", R.drawable.lapsha, 0, 0));
        bufferIngredient.add(new Ingredient(3, "макароны", R.drawable.makaroni, 0, 0));
        bufferIngredient.add(new Ingredient(3, "спагетти", R.drawable.spagetti, 0, 0));

        bufferIngredient.add(new Ingredient(4, "масло кунжутное", R.drawable.maslo_kunzh, 0, 0));
        bufferIngredient.add(new Ingredient(4, "масло оливковое", R.drawable.maslo_oliv, 0, 0));
        bufferIngredient.add(new Ingredient(4, "масло подсолнечное", R.drawable.maslo_podsoln, 0, 0));
        bufferIngredient.add(new Ingredient(4, "масло растительное", R.drawable.oils, 0, 0));

        bufferIngredient.add(new Ingredient(5, "вареное сгущенное молоко", R.drawable.sgush_var, 0, 0));
        bufferIngredient.add(new Ingredient(5, "взбитые сливки", R.drawable.slivki_vz, 0, 0));
        bufferIngredient.add(new Ingredient(5, "йогурт", R.drawable.jogurt, 0, 0));
        bufferIngredient.add(new Ingredient(5, "йогурт фруктовый", R.drawable.jogurt_sweet, 0, 0));
        bufferIngredient.add(new Ingredient(5, "кефир", R.drawable.kefir, 0, 0));
        bufferIngredient.add(new Ingredient(5, "маргарин", R.drawable.margarin, 0, 0));
        bufferIngredient.add(new Ingredient(5, "масло сливочное", R.drawable.maslo_sliv, 0, 0));
        bufferIngredient.add(new Ingredient(5, "молоко", R.drawable.milk, 0, 0));
        bufferIngredient.add(new Ingredient(5, "молоко сухое", R.drawable.milk_suh, 0, 0));
        bufferIngredient.add(new Ingredient(5, "мороженое", R.drawable.ice_cream, 0, 0));
        bufferIngredient.add(new Ingredient(5, "простокваша", R.drawable.prostokv, 0, 0));
        bufferIngredient.add(new Ingredient(5, "сгущенное молоко", R.drawable.sgusch_moloko, 0, 0));
        bufferIngredient.add(new Ingredient(5, "сливки", R.drawable.slivki, 0, 0));
        bufferIngredient.add(new Ingredient(5, "сметана", R.drawable.sour_cream, 0, 0));
        bufferIngredient.add(new Ingredient(5, "топленое молоко", R.drawable.topl_milk, 0, 0));
        bufferIngredient.add(new Ingredient(5, "творог", R.drawable.tvorog, 0, 0));

        bufferIngredient.add(new Ingredient(6, "водоросли", R.drawable.vodorosli, 0, 0));
        bufferIngredient.add(new Ingredient(6, "икра красная", R.drawable.red_caviar, 0, 0));
        bufferIngredient.add(new Ingredient(6, "кальмар", R.drawable.kalmar, 0, 0));
        bufferIngredient.add(new Ingredient(6, "крабовое мясо", R.drawable.krab_meat, 0, 0));
        bufferIngredient.add(new Ingredient(6, "крабовые палочки", R.drawable.krab_pal, 0, 0));
        bufferIngredient.add(new Ingredient(6, "креветки", R.drawable.krevetki, 0, 0));

        bufferIngredient.add(new Ingredient(7, "мука", R.drawable.muka, 0, 0));
        bufferIngredient.add(new Ingredient(7, "мука гречневая", R.drawable.muka_grech, 0, 0));
        bufferIngredient.add(new Ingredient(7, "мука кукурузная", R.drawable.kukuruz_muka, 0, 0));
        bufferIngredient.add(new Ingredient(7, "мука овсяная", R.drawable.oves_muka, 0, 0));
        bufferIngredient.add(new Ingredient(7, "мука пшеничная", R.drawable.muka_pshen, 0, 0));
        bufferIngredient.add(new Ingredient(7, "мука рисовая", R.drawable.ris_muka, 0, 0));
        bufferIngredient.add(new Ingredient(7, "мука цельнозерновая", R.drawable.muka_pshen, 0, 0));
        bufferIngredient.add(new Ingredient(7, "слоеное тесто", R.drawable.sloenoe_testo, 0, 0));

        bufferIngredient.add(new Ingredient(8, "баранина", R.drawable.lamb, 0, 0));
        bufferIngredient.add(new Ingredient(8, "бекон", R.drawable.backon, 0, 0));
        bufferIngredient.add(new Ingredient(8, "ветчина", R.drawable.vetchina, 0, 0));
        bufferIngredient.add(new Ingredient(8, "говядина", R.drawable.beef, 0, 0));
        bufferIngredient.add(new Ingredient(8, "говяжьи ребра", R.drawable.gov_rebra, 0, 0));
        bufferIngredient.add(new Ingredient(8, "жир ", R.drawable.fat, 0, 0));
        bufferIngredient.add(new Ingredient(8, "колбаса", R.drawable.kolbasi, 0, 0));
        bufferIngredient.add(new Ingredient(8, "колбаса вареная", R.drawable.kolbas_var, 0, 0));
        bufferIngredient.add(new Ingredient(8, "колбаса копченая", R.drawable.kolbasa_kopch, 0, 0));
        bufferIngredient.add(new Ingredient(8, "мясо", R.drawable.meat, 0, 0));
        bufferIngredient.add(new Ingredient(8, "мясо копченое", R.drawable.maso_kopch, 0, 0));
        bufferIngredient.add(new Ingredient(8, "охотничьи колбаски", R.drawable.ohot_kolb, 0, 0));
        bufferIngredient.add(new Ingredient(8, "пельмени", R.drawable.pelmeni, 0, 0));
        bufferIngredient.add(new Ingredient(8, "печень ", R.drawable.liver, 0, 0));
        bufferIngredient.add(new Ingredient(8, "салями", R.drawable.salami, 0, 0));
        bufferIngredient.add(new Ingredient(8, "сарделька", R.drawable.sardelki, 0, 0));
        bufferIngredient.add(new Ingredient(8, "свиная нога", R.drawable.nogi_svin, 0, 0));
        bufferIngredient.add(new Ingredient(8, "свинина", R.drawable.pork, 0, 0));
        bufferIngredient.add(new Ingredient(8, "свиные ребра", R.drawable.svin_rebr, 0, 0));
        bufferIngredient.add(new Ingredient(8, "сосиска", R.drawable.sosiska, 0, 0));
        bufferIngredient.add(new Ingredient(8, "суповой набор", R.drawable.sup_nabor, 0, 0));
        bufferIngredient.add(new Ingredient(8, "телятина", R.drawable.telatina, 0, 0));
        bufferIngredient.add(new Ingredient(8, "фарш бараний", R.drawable.farsh_baran, 0, 0));
        bufferIngredient.add(new Ingredient(8, "фарш говяжий", R.drawable.beef_mince, 0, 0));
        bufferIngredient.add(new Ingredient(8, "фарш мясной", R.drawable.farsch, 0, 0));
        bufferIngredient.add(new Ingredient(8, "фарш свиной", R.drawable.pork_mince, 0, 0));

        bufferIngredient.add(new Ingredient(9, "ананасовый сок", R.drawable.ananas_sok, 0, 0));
        bufferIngredient.add(new Ingredient(9, "апельсиновый сок", R.drawable.orange_juice, 0, 0));
        bufferIngredient.add(new Ingredient(9, "бульон", R.drawable.buljen, 0, 0));
        bufferIngredient.add(new Ingredient(9, "бурбон", R.drawable.burbon, 0, 0));
        bufferIngredient.add(new Ingredient(9, "вино белое", R.drawable.vino_bel, 0, 0));
        bufferIngredient.add(new Ingredient(9, "вино красное", R.drawable.vino_red, 0, 0));
        bufferIngredient.add(new Ingredient(9, "виноград", R.drawable.grape, 0, 0));
        bufferIngredient.add(new Ingredient(9, "виноградный сок", R.drawable.grape_juice, 0, 0));
        bufferIngredient.add(new Ingredient(9, "виски", R.drawable.whiskey, 0, 0));
        bufferIngredient.add(new Ingredient(9, "вишневый сок", R.drawable.vishn_sok, 0, 0));
        bufferIngredient.add(new Ingredient(9, "вода", R.drawable.water, 0, 0));
        bufferIngredient.add(new Ingredient(9, "вода газированная", R.drawable.water_spark, 0, 0));
        bufferIngredient.add(new Ingredient(9, "водка", R.drawable.vodka, 0, 0));
        bufferIngredient.add(new Ingredient(9, "зеленый чай", R.drawable.zel_chai, 0, 0));
        bufferIngredient.add(new Ingredient(9, "коньяк", R.drawable.conjak, 0, 0));
        bufferIngredient.add(new Ingredient(9, "кофе", R.drawable.coffee, 0, 0));
        bufferIngredient.add(new Ingredient(9, "лед ", R.drawable.ice, 0, 0));
        bufferIngredient.add(new Ingredient(9, "ликер", R.drawable.liker, 0, 0));
        bufferIngredient.add(new Ingredient(9, "лимонный сок", R.drawable.lemon_juice, 0, 0));
        bufferIngredient.add(new Ingredient(9, "мартини", R.drawable.martini, 0, 0));
        bufferIngredient.add(new Ingredient(9, "огуречный рассол", R.drawable.rassol, 0, 0));
        bufferIngredient.add(new Ingredient(9, "пиво светлое", R.drawable.beer_light, 0, 0));
        bufferIngredient.add(new Ingredient(9, "ром ", R.drawable.rom, 0, 0));
        bufferIngredient.add(new Ingredient(9, "сок лайма", R.drawable.laim_juice, 0, 0));
        bufferIngredient.add(new Ingredient(9, "спирт", R.drawable.spirt, 0, 0));
        bufferIngredient.add(new Ingredient(9, "текила", R.drawable.tekila, 0, 0));
        bufferIngredient.add(new Ingredient(9, "томатный сок", R.drawable.tomat_sok, 0, 0));
        bufferIngredient.add(new Ingredient(9, "чай каркаде", R.drawable.karkade, 0, 0));
        bufferIngredient.add(new Ingredient(9, "черный чай", R.drawable.black_tea, 0, 0));
        bufferIngredient.add(new Ingredient(9, "шампанское", R.drawable.shampan, 0, 0));
        bufferIngredient.add(new Ingredient(9, "яблочный сок", R.drawable.apple_juice, 0, 0));

        bufferIngredient.add(new Ingredient(10, "базилик", R.drawable.bazil_fresh, 0, 0));
        bufferIngredient.add(new Ingredient(10, "баклажан", R.drawable.baklazhan, 0, 0));
        bufferIngredient.add(new Ingredient(10, "болгарский перец", R.drawable.perez_bolg, 0, 0));
        bufferIngredient.add(new Ingredient(10, "брокколи", R.drawable.broccoli, 0, 0));
        bufferIngredient.add(new Ingredient(10, "зелень", R.drawable.zelen, 0, 0));
        bufferIngredient.add(new Ingredient(10, "горох", R.drawable.goroh, 0, 0));
        bufferIngredient.add(new Ingredient(10, "горошек зеленый", R.drawable.goroshek, 0, 0));
        bufferIngredient.add(new Ingredient(10, "горошек консервированный", R.drawable.canned_pea, 0, 0));
        bufferIngredient.add(new Ingredient(10, "кабачок", R.drawable.cabachok, 0, 0));
        bufferIngredient.add(new Ingredient(10, "каперсы", R.drawable.kapersi, 0, 0));
        bufferIngredient.add(new Ingredient(10, "капуста", R.drawable.cabbage, 0, 0));
        bufferIngredient.add(new Ingredient(10, "картофель", R.drawable.potato, 0, 0));
        bufferIngredient.add(new Ingredient(10, "квашеная капуста", R.drawable.kvash_kap, 0, 0));
        bufferIngredient.add(new Ingredient(10, "квашеный огурец", R.drawable.ogurec_kvash, 0, 0));
        bufferIngredient.add(new Ingredient(10, "кинза", R.drawable.kinza, 0, 0));
        bufferIngredient.add(new Ingredient(10, "кукуруза", R.drawable.corn, 0, 0));
        bufferIngredient.add(new Ingredient(10, "кукуруза консервированная", R.drawable.canned_corn, 0, 0));
        bufferIngredient.add(new Ingredient(10, "лук", R.drawable.onion, 0, 0));
        bufferIngredient.add(new Ingredient(10, "лук зеленый", R.drawable.onion_green, 0, 0));
        bufferIngredient.add(new Ingredient(10, "лук красный", R.drawable.onion_red, 0, 0));
        bufferIngredient.add(new Ingredient(10, "лук репчатый", R.drawable.luk_repch, 0, 0));
        bufferIngredient.add(new Ingredient(10, "лук-порей", R.drawable.porej, 0, 0));
        bufferIngredient.add(new Ingredient(10, "лук-шалот", R.drawable.shalot, 0, 0));
        bufferIngredient.add(new Ingredient(10, "малосольный огурец", R.drawable.malosol, 0, 0));
        bufferIngredient.add(new Ingredient(10, "маринованный огурец", R.drawable.ogur_mar, 0, 0));
        bufferIngredient.add(new Ingredient(10, "маринованный помидор", R.drawable.pomidor_kons, 0, 0));
        bufferIngredient.add(new Ingredient(10, "маслины", R.drawable.maslini, 0, 0));
        bufferIngredient.add(new Ingredient(10, "морковь", R.drawable.carrot, 0, 0));
        bufferIngredient.add(new Ingredient(10, "морковь по-корейски", R.drawable.morkov_kor, 0, 0));
        bufferIngredient.add(new Ingredient(10, "нут ", R.drawable.nut, 0, 0));
        bufferIngredient.add(new Ingredient(10, "огурец", R.drawable.cucumber, 0, 0));
        bufferIngredient.add(new Ingredient(10, "оливки", R.drawable.olivki, 0, 0));
        bufferIngredient.add(new Ingredient(10, "пекинская капуста", R.drawable.pekin_kap, 0, 0));
        bufferIngredient.add(new Ingredient(10, "перец чили", R.drawable.krasn_perec, 0, 0));
        bufferIngredient.add(new Ingredient(10, "петрушка", R.drawable.petrushka, 0, 0));
        bufferIngredient.add(new Ingredient(10, "помидор", R.drawable.tomat, 0, 0));
        bufferIngredient.add(new Ingredient(10, "помидоры черри", R.drawable.pomidor_cherry, 0, 0));
        bufferIngredient.add(new Ingredient(10, "салат", R.drawable.salat, 0, 0));
        bufferIngredient.add(new Ingredient(10, "свекла", R.drawable.beets, 0, 0));
        bufferIngredient.add(new Ingredient(10, "сельдерей", R.drawable.selderey, 0, 0));
        bufferIngredient.add(new Ingredient(10, "соленый огурец", R.drawable.soleniy, 0, 0));
        bufferIngredient.add(new Ingredient(10, "тимьян (чабрец)", R.drawable.timjan_fresh, 0, 0));
        bufferIngredient.add(new Ingredient(10, "турнепс", R.drawable.turneps, 0, 0));
        bufferIngredient.add(new Ingredient(10, "тыква", R.drawable.tikva, 0, 0));
        bufferIngredient.add(new Ingredient(10, "укроп", R.drawable.ukrop, 0, 0));
        bufferIngredient.add(new Ingredient(10, "фасоль белая", R.drawable.fasol_white, 0, 0));
        bufferIngredient.add(new Ingredient(10, "фасоль белая консервированная", R.drawable.fasol_bel_kons, 0, 0));
        bufferIngredient.add(new Ingredient(10, "фасоль в томатном соусе", R.drawable.fasol_tomat, 0, 0));
        bufferIngredient.add(new Ingredient(10, "фасоль красная", R.drawable.fasol_kr, 0, 0));
        bufferIngredient.add(new Ingredient(10, "фасоль красная консервированная", R.drawable.fasol_red, 0, 0));
        bufferIngredient.add(new Ingredient(10, "фасоль стручковая", R.drawable.fasol_str, 0, 0));
        bufferIngredient.add(new Ingredient(10, "хрен", R.drawable.hren, 0, 0));
        bufferIngredient.add(new Ingredient(10, "цветная капуста", R.drawable.cvet_kapusta, 0, 0));
        bufferIngredient.add(new Ingredient(10, "цукини", R.drawable.zuccini, 0, 0));
        bufferIngredient.add(new Ingredient(10, "чеснок", R.drawable.garlic, 0, 0));
        bufferIngredient.add(new Ingredient(10, "шпинат", R.drawable.spinach, 0, 0));
        bufferIngredient.add(new Ingredient(10, "щавель", R.drawable.sorrel, 0, 0));

        bufferIngredient.add(new Ingredient(11, "арахис", R.drawable.arahis, 0, 0));
        bufferIngredient.add(new Ingredient(11, "грецкие орехи", R.drawable.grec_oreh, 0, 0));
        bufferIngredient.add(new Ingredient(11, "кедровые орехи", R.drawable.kedr, 0, 0));
        bufferIngredient.add(new Ingredient(11, "мускатный орех", R.drawable.muskat, 0, 0));
        bufferIngredient.add(new Ingredient(11, "орехи", R.drawable.orehi, 0, 0));
        bufferIngredient.add(new Ingredient(11, "миндаль", R.drawable.mindal, 0, 0));
        bufferIngredient.add(new Ingredient(11, "семена льна", R.drawable.len, 0, 0));
        bufferIngredient.add(new Ingredient(11, "семечки подсолнечника", R.drawable.semechki_pods, 0, 0));
        bufferIngredient.add(new Ingredient(11, "семечки тыквенные", R.drawable.tikv_sem, 0, 0));
        bufferIngredient.add(new Ingredient(11, "фисташки", R.drawable.fistahki, 0, 0));
        bufferIngredient.add(new Ingredient(11, "фундук", R.drawable.funduk, 0, 0));

        bufferIngredient.add(new Ingredient(12, "агар-агар", R.drawable.agar, 0, 0));
        bufferIngredient.add(new Ingredient(12, "аджика сухая", R.drawable.adjika_suh, 0, 0));
        bufferIngredient.add(new Ingredient(12, "бадьян", R.drawable.badjan, 0, 0));
        bufferIngredient.add(new Ingredient(12, "базилик сушеный", R.drawable.bazilik, 0, 0));
        bufferIngredient.add(new Ingredient(12, "бальзамический уксус", R.drawable.balsam_uksus, 0, 0));
        bufferIngredient.add(new Ingredient(12, "барбарис", R.drawable.barbaris, 0, 0));
        bufferIngredient.add(new Ingredient(12, "белый молотый перец", R.drawable.perec_bel_mol, 0, 0));
        bufferIngredient.add(new Ingredient(12, "бульонный кубик", R.drawable.kubik, 0, 0));
        bufferIngredient.add(new Ingredient(12, "ванилин", R.drawable.vanilin, 0, 0));
        bufferIngredient.add(new Ingredient(12, "ваниль ", R.drawable.vanil, 0, 0));
        bufferIngredient.add(new Ingredient(12, "ванильная эссенция", R.drawable.vanil_ess, 0, 0));
        bufferIngredient.add(new Ingredient(12, "ванильный экстракт", R.drawable.vanil_extract, 0, 0));
        bufferIngredient.add(new Ingredient(12, "винный уксус", R.drawable.uksus_vin, 0, 0));
        bufferIngredient.add(new Ingredient(12, "гвоздика", R.drawable.gvozdika, 0, 0));
        bufferIngredient.add(new Ingredient(12, "горчичное семя", R.drawable.gorch_semja, 0, 0));
        bufferIngredient.add(new Ingredient(12, "горчичный порошок", R.drawable.gorch_por, 0, 0));
        bufferIngredient.add(new Ingredient(12, "дрожжи", R.drawable.drozzi, 0, 0));
        bufferIngredient.add(new Ingredient(12, "душистый перец", R.drawable.perec_dush, 0, 0));
        bufferIngredient.add(new Ingredient(12, "желатин", R.drawable.jelatin, 0, 0));
        bufferIngredient.add(new Ingredient(12, "жидкий дым", R.drawable.liquid_smoke, 0, 0));
        bufferIngredient.add(new Ingredient(12, "имбирь", R.drawable.imbir_root, 0, 0));
        bufferIngredient.add(new Ingredient(12, "итальянские травы", R.drawable.ital_trav, 0, 0));
        bufferIngredient.add(new Ingredient(12, "кардамон", R.drawable.kardamon, 0, 0));
        bufferIngredient.add(new Ingredient(12, "карри", R.drawable.karri, 0, 0));
        bufferIngredient.add(new Ingredient(12, "кокосовая стружка", R.drawable.kokos_str, 0, 0));
        bufferIngredient.add(new Ingredient(12, "кориандр", R.drawable.coriandr, 0, 0));
        bufferIngredient.add(new Ingredient(12, "корица", R.drawable.koritsa, 0, 0));
        bufferIngredient.add(new Ingredient(12, "краситель", R.drawable.krasitel, 0, 0));
        bufferIngredient.add(new Ingredient(12, "красный молотый перец", R.drawable.perec_mol_kr, 0, 0));
        bufferIngredient.add(new Ingredient(12, "крахмал", R.drawable.krahmal, 0, 0));
        bufferIngredient.add(new Ingredient(12, "кунжут ", R.drawable.kunzut, 0, 0));
        bufferIngredient.add(new Ingredient(12, "кумин (зира)", R.drawable.kumin, 0, 0));
        bufferIngredient.add(new Ingredient(12, "куркума", R.drawable.kurkuma, 0, 0));
        bufferIngredient.add(new Ingredient(12, "лавровый лист", R.drawable.lavr, 0, 0));
        bufferIngredient.add(new Ingredient(12, "лимонная кислота", R.drawable.limon_kisl, 0, 0));
        bufferIngredient.add(new Ingredient(12, "листья вишни", R.drawable.list_cherry, 0, 0));
        bufferIngredient.add(new Ingredient(12, "листья малины", R.drawable.list_malina, 0, 0));
        bufferIngredient.add(new Ingredient(12, "листья смородины", R.drawable.list_smor, 0, 0));
        bufferIngredient.add(new Ingredient(12, "лук сушеный", R.drawable.luk_sush, 0, 0));
        bufferIngredient.add(new Ingredient(12, "майоран сушеный", R.drawable.mayoran_suh, 0, 0));
        bufferIngredient.add(new Ingredient(12, "мак ", R.drawable.mak, 0, 0));
        bufferIngredient.add(new Ingredient(12, "молотый имбирь", R.drawable.imbir_mol, 0, 0));
        bufferIngredient.add(new Ingredient(12, "молотый кориандр", R.drawable.koriandr, 0, 0));
        bufferIngredient.add(new Ingredient(12, "молотый перец", R.drawable.perez_molot, 0, 0));
        bufferIngredient.add(new Ingredient(12, "мята", R.drawable.mint, 0, 0));
        bufferIngredient.add(new Ingredient(12, "орегано", R.drawable.oregano, 0, 0));
        bufferIngredient.add(new Ingredient(12, "паприка", R.drawable.paprika_sweet, 0, 0));
        bufferIngredient.add(new Ingredient(12, "приправа для курицы", R.drawable.priprava_kur, 0, 0));
        bufferIngredient.add(new Ingredient(12, "приправа для моркови по-корейски", R.drawable.priprav_kor, 0, 0));
        bufferIngredient.add(new Ingredient(12, "приправа для плова", R.drawable.priprava_plov, 0, 0));
        bufferIngredient.add(new Ingredient(12, "прованские травы", R.drawable.provans, 0, 0));
        bufferIngredient.add(new Ingredient(12, "разрыхлитель", R.drawable.razrihl, 0, 0));
        bufferIngredient.add(new Ingredient(12, "рисовый уксус", R.drawable.uksus_ris, 0, 0));
        bufferIngredient.add(new Ingredient(12, "розмарин", R.drawable.rozmarin, 0, 0));
        bufferIngredient.add(new Ingredient(12, "смесь перцев", R.drawable.smes_perc, 0, 0));
        bufferIngredient.add(new Ingredient(12, "сода", R.drawable.soda, 0, 0));
        bufferIngredient.add(new Ingredient(12, "соль ", R.drawable.salt, 0, 0));
        bufferIngredient.add(new Ingredient(12, "сумах", R.drawable.sumak, 0, 0));
        bufferIngredient.add(new Ingredient(12, "тимьян (чабрец) сушеный", R.drawable.timjan, 0, 0));
        bufferIngredient.add(new Ingredient(12, "тмин", R.drawable.tmin, 0, 0));
        bufferIngredient.add(new Ingredient(12, "уксус", R.drawable.uksus, 0, 0));
        bufferIngredient.add(new Ingredient(12, "хмели-сунели", R.drawable.hmeli, 0, 0));
        bufferIngredient.add(new Ingredient(12, "цедра апельсина", R.drawable.cedra_orange, 0, 0));
        bufferIngredient.add(new Ingredient(12, "цедра лимона", R.drawable.cedra_lemon, 0, 0));
        bufferIngredient.add(new Ingredient(12, "чабер", R.drawable.chaber, 0, 0));
        bufferIngredient.add(new Ingredient(12, "черный молотый перец", R.drawable.perez_black, 0, 0));
        bufferIngredient.add(new Ingredient(12, "черный перец горошек", R.drawable.pepper_gor, 0, 0));
        bufferIngredient.add(new Ingredient(12, "чеснок сушеный", R.drawable.garlic_suh, 0, 0));
        bufferIngredient.add(new Ingredient(12, "яблочный уксус", R.drawable.jabl_uksus, 0, 0));

        bufferIngredient.add(new Ingredient(13, "индейка", R.drawable.indi, 0, 0));
        bufferIngredient.add(new Ingredient(13, "куриная грудка", R.drawable.kur_grud, 0, 0));
        bufferIngredient.add(new Ingredient(13, "куриная грудка копченая", R.drawable.grudka_kopch, 0, 0));
        bufferIngredient.add(new Ingredient(13, "куриная печень ", R.drawable.kur_pechen, 0, 0));
        bufferIngredient.add(new Ingredient(13, "куриное бедро", R.drawable.kur_bedro, 0, 0));
        bufferIngredient.add(new Ingredient(13, "куриное филе", R.drawable.chicken_fillet, 0, 0));
        bufferIngredient.add(new Ingredient(13, "куриные крылышки", R.drawable.kur_kril, 0, 0));
        bufferIngredient.add(new Ingredient(13, "куриные ножки", R.drawable.kur_nozki, 0, 0));
        bufferIngredient.add(new Ingredient(13, "куриные сердечки", R.drawable.kur_serd, 0, 0));
        bufferIngredient.add(new Ingredient(13, "курица", R.drawable.chicken, 0, 0));
        bufferIngredient.add(new Ingredient(13, "курица копченая", R.drawable.chick_kopch, 0, 0));
        bufferIngredient.add(new Ingredient(13, "окорочок", R.drawable.okoroch, 0, 0));
        bufferIngredient.add(new Ingredient(13, "окорочок копченый", R.drawable.okorochok, 0, 0));
        bufferIngredient.add(new Ingredient(13, "фарш индейки", R.drawable.farsch_ind, 0, 0));
        bufferIngredient.add(new Ingredient(13, "фарш куриный", R.drawable.farsh_kur, 0, 0));
        bufferIngredient.add(new Ingredient(13, "филе индейки", R.drawable.file_ind, 0, 0));

        bufferIngredient.add(new Ingredient(14, "белая рыба", R.drawable.white_fish, 0, 0));
        bufferIngredient.add(new Ingredient(14, "горбуша", R.drawable.gorbusha, 0, 0));
        bufferIngredient.add(new Ingredient(14, "карп", R.drawable.carp, 0, 0));
        bufferIngredient.add(new Ingredient(14, "красная рыба", R.drawable.red_fish, 0, 0));
        bufferIngredient.add(new Ingredient(14, "лосось", R.drawable.salmon, 0, 0));
        bufferIngredient.add(new Ingredient(14, "минтай", R.drawable.mintai, 0, 0));
        bufferIngredient.add(new Ingredient(14, "окунь", R.drawable.okun, 0, 0));
        bufferIngredient.add(new Ingredient(14, "печень трески", R.drawable.pechen_treski, 0, 0));
        bufferIngredient.add(new Ingredient(14, "пикша", R.drawable.piksha, 0, 0));
        bufferIngredient.add(new Ingredient(14, "рыба", R.drawable.fish, 0, 0));
        bufferIngredient.add(new Ingredient(14, "рыбные консервы", R.drawable.rib_kons, 0, 0));
        bufferIngredient.add(new Ingredient(14, "сазан", R.drawable.sazan, 0, 0));
        bufferIngredient.add(new Ingredient(14, "сайра консервированная", R.drawable.sayra_kons, 0, 0));
        bufferIngredient.add(new Ingredient(14, "сельдь", R.drawable.seld, 0, 0));
        bufferIngredient.add(new Ingredient(14, "семга", R.drawable.semga, 0, 0));
        bufferIngredient.add(new Ingredient(14, "соленая рыба", R.drawable.salt_fish, 0, 0));
        bufferIngredient.add(new Ingredient(14, "скумбрия", R.drawable.skumbr, 0, 0));
        bufferIngredient.add(new Ingredient(14, "скумбрия консервированная", R.drawable.skumbr_kons, 0, 0));
        bufferIngredient.add(new Ingredient(14, "тилапия", R.drawable.tilapia, 0, 0));
        bufferIngredient.add(new Ingredient(14, "треска", R.drawable.treska, 0, 0));
        bufferIngredient.add(new Ingredient(14, "тунец", R.drawable.tunets, 0, 0));
        bufferIngredient.add(new Ingredient(14, "тунец консервированный", R.drawable.tuna_kons, 0, 0));
        bufferIngredient.add(new Ingredient(14, "фарш рыбный", R.drawable.rib_farsh, 0, 0));
        bufferIngredient.add(new Ingredient(14, "форель", R.drawable.forel, 0, 0));
        bufferIngredient.add(new Ingredient(14, "хек", R.drawable.hek, 0, 0));
        bufferIngredient.add(new Ingredient(14, "шпроты", R.drawable.sprot, 0, 0));

        bufferIngredient.add(new Ingredient(15, "арахисовая паста", R.drawable.arahis_pasta, 0, 0));
        bufferIngredient.add(new Ingredient(15, "ванильный сахар", R.drawable.vanil_sugar, 0, 0));
        bufferIngredient.add(new Ingredient(15, "ванильный сироп", R.drawable.vanil_sirop, 0, 0));
        bufferIngredient.add(new Ingredient(15, "гранатовый сироп", R.drawable.granat_syrop, 0, 0));
        bufferIngredient.add(new Ingredient(15, "грушевый сироп", R.drawable.grush_sypup, 0, 0));
        bufferIngredient.add(new Ingredient(15, "варенье", R.drawable.varenje, 0, 0));
        bufferIngredient.add(new Ingredient(15, "варенье из айвы", R.drawable.ayva_var, 0, 0));
        bufferIngredient.add(new Ingredient(15, "вишневое варенье", R.drawable.vishn_var, 0, 0));
        bufferIngredient.add(new Ingredient(15, "джем", R.drawable.jam, 0, 0));
        bufferIngredient.add(new Ingredient(15, "желе ", R.drawable.jele, 0, 0));
        bufferIngredient.add(new Ingredient(15, "зефир", R.drawable.zefir, 0, 0));
        bufferIngredient.add(new Ingredient(15, "какао", R.drawable.cacao, 0, 0));
        bufferIngredient.add(new Ingredient(15, "клубничное варенье", R.drawable.var_kl, 0, 0));
        bufferIngredient.add(new Ingredient(15, "мармелад", R.drawable.marmelad, 0, 0));
        bufferIngredient.add(new Ingredient(15, "маршмеллоу", R.drawable.marhmellow, 0, 0));
        bufferIngredient.add(new Ingredient(15, "мед", R.drawable.honey, 0, 0));
        bufferIngredient.add(new Ingredient(15, "печенье", R.drawable.cookies, 0, 0));
        bufferIngredient.add(new Ingredient(15, "повидло", R.drawable.povidlo, 0, 0));
        bufferIngredient.add(new Ingredient(15, "подсластитель", R.drawable.sweetener, 0, 0));
        bufferIngredient.add(new Ingredient(15, "пряники", R.drawable.praniki, 0, 0));
        bufferIngredient.add(new Ingredient(15, "пудинг", R.drawable.puding, 0, 0));
        bufferIngredient.add(new Ingredient(15, "сахар", R.drawable.sugar, 0, 0));
        bufferIngredient.add(new Ingredient(15, "сахар коричневый", R.drawable.sahar_kor, 0, 0));
        bufferIngredient.add(new Ingredient(15, "сахарная пудра", R.drawable.pudra, 0, 0));
        bufferIngredient.add(new Ingredient(15, "сахарный сироп", R.drawable.sugar_sirup, 0, 0));
        bufferIngredient.add(new Ingredient(15, "сироп", R.drawable.syrup, 0, 0));
        bufferIngredient.add(new Ingredient(15, "смородиновый сироп", R.drawable.smorod_syrop, 0, 0));
        bufferIngredient.add(new Ingredient(15, "стевия", R.drawable.stevia, 0, 0));
        bufferIngredient.add(new Ingredient(15, "цукаты", R.drawable.zukat, 0, 0));
        bufferIngredient.add(new Ingredient(15, "шоколад", R.drawable.chocolate, 0, 0));
        bufferIngredient.add(new Ingredient(15, "шоколадный сироп", R.drawable.choc_syrup, 0, 0));

        bufferIngredient.add(new Ingredient(16, "васаби", R.drawable.wasabi, 0, 0));
        bufferIngredient.add(new Ingredient(16, "вустерский соус", R.drawable.wourchester, 0, 0));
        bufferIngredient.add(new Ingredient(16, "горчица", R.drawable.gorchiza, 0, 0));
        bufferIngredient.add(new Ingredient(16, "кетчуп", R.drawable.ketchup, 0, 0));
        bufferIngredient.add(new Ingredient(16, "майонез", R.drawable.mayonese, 0, 0));
        bufferIngredient.add(new Ingredient(16, "майонез легкий", R.drawable.mayon_light, 0, 0));
        bufferIngredient.add(new Ingredient(16, "острый соус чили", R.drawable.chili, 0, 0));
        bufferIngredient.add(new Ingredient(16, "соус барбекю", R.drawable.sauce_bbq, 0, 0));
        bufferIngredient.add(new Ingredient(16, "соевый соус", R.drawable.soev_sous, 0, 0));
        bufferIngredient.add(new Ingredient(16, "томатная паста", R.drawable.tomat_pasta, 0, 0));
        bufferIngredient.add(new Ingredient(16, "тхина", R.drawable.thina, 0, 0));

        bufferIngredient.add(new Ingredient(17, "изюм", R.drawable.izum, 0, 0));
        bufferIngredient.add(new Ingredient(17, "курага", R.drawable.kuraga, 0, 0));
        bufferIngredient.add(new Ingredient(17, "сухофрукты", R.drawable.suhofrukti, 0, 0));
        bufferIngredient.add(new Ingredient(17, "финики", R.drawable.finiki, 0, 0));
        bufferIngredient.add(new Ingredient(17, "чернослив", R.drawable.prunes, 0, 0));

        bufferIngredient.add(new Ingredient(18, "брынза", R.drawable.brynza, 0, 0));
        bufferIngredient.add(new Ingredient(18, "маскарпоне", R.drawable.maskarpone, 0, 0));
        bufferIngredient.add(new Ingredient(18, "моцарелла", R.drawable.mozarella, 0, 0));
        bufferIngredient.add(new Ingredient(18, "пармезан", R.drawable.parmezan, 0, 0));
        bufferIngredient.add(new Ingredient(18, "копченый сыр", R.drawable.kopch_sir, 0, 0));
        bufferIngredient.add(new Ingredient(18, "плавленый сыр", R.drawable.plavl_sir, 0, 0));
        bufferIngredient.add(new Ingredient(18, "рикотта", R.drawable.ricotta, 0, 0));
        bufferIngredient.add(new Ingredient(18, "сливочный сыр", R.drawable.sir_sliv, 0, 0));
        bufferIngredient.add(new Ingredient(18, "сулугуни", R.drawable.suluguni, 0, 0));
        bufferIngredient.add(new Ingredient(18, "сыр", R.drawable.cheese, 0, 0));
        bufferIngredient.add(new Ingredient(18, "фета", R.drawable.feta, 0, 0));
        bufferIngredient.add(new Ingredient(18, "филадельфия", R.drawable.phil, 0, 0));
        bufferIngredient.add(new Ingredient(18, "чеддер", R.drawable.cheddar, 0, 0));

        bufferIngredient.add(new Ingredient(19, "абрикос", R.drawable.abrikos, 0, 0));
        bufferIngredient.add(new Ingredient(19, "ананас", R.drawable.ananas, 0, 0));
        bufferIngredient.add(new Ingredient(19, "ананас консервированный", R.drawable.ananas_kons, 0, 0));
        bufferIngredient.add(new Ingredient(19, "апельсин", R.drawable.orange, 0, 0));
        bufferIngredient.add(new Ingredient(19, "банан", R.drawable.banan, 0, 0));
        bufferIngredient.add(new Ingredient(19, "гранат", R.drawable.granat, 0, 0));
        bufferIngredient.add(new Ingredient(19, "грейпфрут", R.drawable.grapefruit, 0, 0));
        bufferIngredient.add(new Ingredient(19, "груша", R.drawable.grusha, 0, 0));
        bufferIngredient.add(new Ingredient(19, "дыня", R.drawable.dinja, 0, 0));
        bufferIngredient.add(new Ingredient(19, "киви", R.drawable.qiwi, 0, 0));
        bufferIngredient.add(new Ingredient(19, "мандарин", R.drawable.mandarin, 0, 0));
        bufferIngredient.add(new Ingredient(19, "маракуйя", R.drawable.marakuya, 0, 0));
        bufferIngredient.add(new Ingredient(19, "лайм", R.drawable.laim, 0, 0));
        bufferIngredient.add(new Ingredient(19, "лимон", R.drawable.lemon, 0, 0));
        bufferIngredient.add(new Ingredient(19, "персик", R.drawable.peach, 0, 0));
        bufferIngredient.add(new Ingredient(19, "персик консервированный", R.drawable.persik_kons, 0, 0));
        bufferIngredient.add(new Ingredient(19, "слива", R.drawable.sliva, 0, 0));
        bufferIngredient.add(new Ingredient(19, "фрукты ", R.drawable.frukti, 0, 0));
        bufferIngredient.add(new Ingredient(19, "яблоко", R.drawable.apple, 0, 0));

        bufferIngredient.add(new Ingredient(20, "багет", R.drawable.baget_fr, 0, 0));
        bufferIngredient.add(new Ingredient(20, "батон", R.drawable.baton, 0, 0));
        bufferIngredient.add(new Ingredient(20, "белый хлеб", R.drawable.bel_hleb, 0, 0));
        bufferIngredient.add(new Ingredient(20, "булочка", R.drawable.bulochka, 0, 0));
        bufferIngredient.add(new Ingredient(20, "корж", R.drawable.korzh, 0, 0));
        bufferIngredient.add(new Ingredient(20, "кукурузные палочки", R.drawable.kukur_pal, 0, 0));
        bufferIngredient.add(new Ingredient(20, "лаваш толстый", R.drawable.lavash_tol, 0, 0));
        bufferIngredient.add(new Ingredient(20, "лаваш тонкий", R.drawable.lavash, 0, 0));
        bufferIngredient.add(new Ingredient(20, "лепешка", R.drawable.lepeshka, 0, 0));
        bufferIngredient.add(new Ingredient(20, "пита ", R.drawable.pita, 0, 0));
        bufferIngredient.add(new Ingredient(20, "сухари  ", R.drawable.suhari, 0, 0));
        bufferIngredient.add(new Ingredient(20, "сухари панировочные", R.drawable.suh_panir, 0, 0));
        bufferIngredient.add(new Ingredient(20, "сухарики", R.drawable.suhar, 0, 0));
        bufferIngredient.add(new Ingredient(20, "сухарики из белого хлеба", R.drawable.suhari_bel, 0, 0));
        bufferIngredient.add(new Ingredient(20, "сухарики из черного хлеба", R.drawable.suhariki_black, 0, 0));
        bufferIngredient.add(new Ingredient(20, "тарталетки", R.drawable.tartaletki, 0, 0));
        bufferIngredient.add(new Ingredient(20, "цельнозерновой хлеб", R.drawable.celn_hleb, 0, 0));
        bufferIngredient.add(new Ingredient(20, "хлеб", R.drawable.hleb, 0, 0));
        bufferIngredient.add(new Ingredient(20, "черный хлеб", R.drawable.chern_hleb, 0, 0));
        bufferIngredient.add(new Ingredient(20, "чипсы", R.drawable.chern_hleb, 0, 0));

        bufferIngredient.add(new Ingredient(21, "вишня", R.drawable.cherry, 0, 0));
        bufferIngredient.add(new Ingredient(21, "голубика", R.drawable.golubika, 0, 0));
        bufferIngredient.add(new Ingredient(21, "ежевика", R.drawable.ezhevika, 0, 0));
        bufferIngredient.add(new Ingredient(21, "клубника", R.drawable.klubnika, 0, 0));
        bufferIngredient.add(new Ingredient(21, "клюква", R.drawable.klukva, 0, 0));
        bufferIngredient.add(new Ingredient(21, "облепиха", R.drawable.oblepikha, 0, 0));
        bufferIngredient.add(new Ingredient(21, "смородина", R.drawable.smorodina, 0, 0));
        bufferIngredient.add(new Ingredient(21, "черника", R.drawable.chernika, 0, 0));
        bufferIngredient.add(new Ingredient(21, "ягоды", R.drawable.yagodi, 0, 0));

        bufferIngredient.add(new Ingredient(22, "яйцо", R.drawable.egg, 0, 0));
        bufferIngredient.add(new Ingredient(22, "яйцо куриное", R.drawable.eggs, 0, 0));
        bufferIngredient.add(new Ingredient(22, "яйцо перепелиное", R.drawable.egg_perepel, 0, 0));

        ingredientDB.copyOrUpdate(bufferIngredient);

        bufferIngredient = null;
    }

    private void createCategoryTablesRU() {
        ArrayList<CategoryTable> bufferCategoryTables = new ArrayList<>();

        bufferCategoryTables.add(new CategoryTable(0, "Все"));
        bufferCategoryTables.add(new CategoryTable(1, "Грибы"));
        bufferCategoryTables.add(new CategoryTable(2, "Крупы и злаки"));
        bufferCategoryTables.add(new CategoryTable(3, "Макароны"));
        bufferCategoryTables.add(new CategoryTable(4, "Масла растит."));
        bufferCategoryTables.add(new CategoryTable(5, "Молочное"));
        bufferCategoryTables.add(new CategoryTable(6, "Морепр-ты"));
        bufferCategoryTables.add(new CategoryTable(7, "Мука и тесто"));
        bufferCategoryTables.add(new CategoryTable(8, "Мясное"));
        bufferCategoryTables.add(new CategoryTable(9, "Напитки"));
        bufferCategoryTables.add(new CategoryTable(10, "Овощи"));
        bufferCategoryTables.add(new CategoryTable(11, "Орехи и семечки"));
        bufferCategoryTables.add(new CategoryTable(12, "Приправы"));
        bufferCategoryTables.add(new CategoryTable(13, "Птица"));
        bufferCategoryTables.add(new CategoryTable(14, "Рыба"));
        bufferCategoryTables.add(new CategoryTable(15, "Сладости"));
        bufferCategoryTables.add(new CategoryTable(16, "Соусы"));
        bufferCategoryTables.add(new CategoryTable(17, "Сухофрукты"));
        bufferCategoryTables.add(new CategoryTable(18, "Сыры"));
        bufferCategoryTables.add(new CategoryTable(19, "Фрукты"));
        bufferCategoryTables.add(new CategoryTable(20, "Хлебобул. изд."));
        bufferCategoryTables.add(new CategoryTable(21, "Ягоды"));
        bufferCategoryTables.add(new CategoryTable(22, "Яйца"));

        ingredientDB.copyOrUpdateCategoryTable(bufferCategoryTables);

        bufferCategoryTables = null;
    }

    private void createCategoriesRU() {
        ArrayList <Categories> bufferCategories = new ArrayList<>();
        bufferCategories.add(new Categories("Блины и оладьи", R.drawable.pancaces));
        bufferCategories.add(new Categories("Блюда для мультиварки", R.drawable.multi));
        bufferCategories.add(new Categories("Блюда на завтрак", R.drawable.zavtrak));
        bufferCategories.add(new Categories("Вторые блюда", R.drawable.vtor_bludo));
        bufferCategories.add(new Categories("Гарниры", R.drawable.garnir));
        bufferCategories.add(new Categories("Десерты", R.drawable.desserts));
        bufferCategories.add(new Categories("Заготовки", R.drawable.zagotov));
        bufferCategories.add(new Categories("Закуски", R.drawable.zakuski));
        bufferCategories.add(new Categories("Здоровое питание", R.drawable.polezn));
        bufferCategories.add(new Categories("Каши", R.drawable.kashi));
        bufferCategories.add(new Categories("Напитки", R.drawable.drinks));
        bufferCategories.add(new Categories("Несладкая выпечка", R.drawable.nesl_vip));
        bufferCategories.add(new Categories("Рыба и морепродукты", R.drawable.bluda_fish));
        bufferCategories.add(new Categories("Салаты", R.drawable.salads));
        bufferCategories.add(new Categories("Сладкая выпечка", R.drawable.vipechka));
        bufferCategories.add(new Categories("Соусы", R.drawable.sauces));
        bufferCategories.add(new Categories("Супы", R.drawable.soups));

        ingredientDB.copyOrUpdateCategories(bufferCategories);

        bufferCategories = null;
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
        ArrayList<Recipe> bufferRecipe = new ArrayList<>();
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
                    int id = obj.getInt("id");
                    String name = obj.getString("name");
                    String ingredients = obj.getString("ingredients");
                    String category = obj.getString("category");
                    String description = obj.getString("description");
                    String calories = obj.getString("calories");
                    String image = obj.getString("image");
                    bufferRecipe.add(new Recipe(id, name, ingredients, category, description, calories, image));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ingredientDB.copyOrUpdateRecipe(bufferRecipe);

        bufferRecipe = null;
    }

    @Override
    public void onBackPressed() {
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (preferences.getFlagRating()) {
            new CustomDialog6(this).show();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        ingredientDB.close();
        super.onDestroy();
    }
}