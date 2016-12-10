package com.github.jurassicspb.cookbooksearchbyingredients;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;
import com.github.jurassicspb.cookbooksearchbyingredients.storage.MyPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Мария on 07.12.2016.
 */

public class RecipeListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeListAdapter adapter;
    private IngredientDatabase recipeDB;
    private List <Recipe> recipes;
    MyPreferences preferences;
    private OnListItemClickListener clickListener = new OnListItemClickListener() {
        @Override
        public void onClick(View v, int position) {

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeDB = new IngredientDatabase();
        preferences = new MyPreferences(this);
        preferences.clearPrefs();

        setContentView(R.layout.recipelist_recyclerview);

        if (preferences.getFlagRecipes()) {
            createRecipes();
            preferences.setFlagRecipe(false);
            Log.d(RecipeListActivity.class.getSimpleName(), "hello");
        }
        performRecipes();
//        performAll();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipeListAdapter(recipes, clickListener);
        recyclerView.setAdapter(adapter);

    }
    private void performRecipes(){
            recipes = recipeDB.getRecipe(SelectedIngredient.getSelectedIngredient());
            Log.d(RecipeListActivity.class.getSimpleName(), "buybuy" + recipes);
        }
    private void performAll(){
        recipes = recipeDB.getAllRecipes();
    }

    private void createRecipes(){
        ArrayList<Recipe> newRecipe = new ArrayList<>();
        newRecipe.add(new Recipe("Борщ классический"
                ,"На 3 литра воды:\n" +
                "Говядина на кости — 700-800 грамм\n" +
                "Капуста свежая — 300 грамм\n" +
                "Картофель — 2-3 средних картофелины (200-300 грамм)\n" +
                "Свекла — 2 маленьких или 1 средняя (100-150 грамм)\n" +
                "Морковь — 1  штука среднего размера (75-100 грамм)\n" +
                "Лук репчатый — 1 луковица среднего размера (75-100 грамм)\n" +
                "Томатная паста  — 1 ст. ложка, или 1 небольшой помидор\n" +
                "Масло растительное для обжарки\n" +
                "Чеснок — 2 зубчика\n" +
                "Специи: соль, перец черный молотый, лавровый лист, зелень (укроп, петрушка, базилик).\n",
                "1. Первым делом нужно сварить бульон. Для этого говядину на кости промыть под проточной водой, положить ее в кастрюлю и залить холодной водой. Можно добавить 1 чайную ложку соли. Довести до кипения и убавить огонь до минимума. Борщ нужно готовить на медленном огне, тогда овощи в нем не разварятся и не превратятся в кашу. Говядина варится около 1 часа. Готовность мяса можно проверить по тому, насколько легко оно отделяется от кости. \n"
                , "https://4.downloader.disk.yandex.ru/disk/fbd70e6bbfa574f00b0b096998e7d17339ccd5237b4084ed3e0e128b464c9b38/5849fb7d/TG2GetQi-fDXK7Q4RQ8h5gjTPfFuOneZpAoeS48IjKpyTdDXe-ka7ARqIC_3hbu13qDM1iQIsREuiIY0zniFrg%3D%3D?uid=0&filename=103676899_borsch.jpg&disposition=inline&hash=&limit=0&content_type=image%2Fjpeg&fsize=66577&hid=765620d0c340727a10828372273c14a5&media_type=image&tknv=v2&etag=c8b2375a27de5d6b07b8126e1d114647"));
        recipeDB.copyOrUpdateRecipe(newRecipe);
    }
    @Override
    protected void onDestroy() {
        recipeDB.close();
        super.onDestroy();
    }
}
