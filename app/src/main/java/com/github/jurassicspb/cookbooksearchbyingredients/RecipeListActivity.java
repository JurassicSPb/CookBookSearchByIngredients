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
import java.util.Collections;
import java.util.Comparator;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(RecipeListActivity.class.getSimpleName(), "kakkak " + performRecipes());
        adapter = new RecipeListAdapter(performRecipes(), clickListener);
        recyclerView.setAdapter(adapter);

    }
    private List<Recipe> performRecipes() {
        recipes = recipeDB.getRecipe(SelectedIngredient.getSelectedIngredient());
        List<Recipe> newRecipes = recipeDB.copyFromRealmRecipe(recipes);
        int count;
        for (int i = 0; i < newRecipes.size(); i++) {
            count = 0;
            for (int k = 0; k < SelectedIngredient.getSelectedIngredient().size(); k++) {
                if (newRecipes.get(i).getIngredient().contains(SelectedIngredient.getSelectedIngredient().get(k))) {
                    count++;
                }
            }
            newRecipes.get(i).setCount(String.valueOf(count));
        }
        Comparator<Recipe> compare = (name1, name2) -> name2.getCount().compareTo(name1.getCount());
        Collections.sort(newRecipes, compare);
        Log.d(RecipeListActivity.class.getSimpleName(), "pukpuk " + newRecipes);
        return newRecipes;
    }
    private void createRecipes(){
        ArrayList<Recipe> newRecipe = new ArrayList<>();
        newRecipe.add(new Recipe("Борщ классический"
                ,
                "На 3 литра воды:\n" +
                "Свинина\n" +
                "Говядина на кости — 700-800 грамм\n" +
                "Капуста свежая — 300 грамм\n" +
                "Картофель — 2-3 средних картофелины (200-300 грамм)\n" +
                "Свекла — 2 маленьких или 1 средняя (100-150 грамм)\n" +
                "Морковь — 1  штука среднего размера (75-100 грамм)\n" +
                "Лук репчатый — 1 луковица среднего размера (75-100 грамм)\n" +
                "Масло растительное для обжарки\n" +
                "Чеснок — 2 зубчика\n" +
                "Специи: соль, перец черный молотый, лавровый лист, зелень (укроп, петрушка, базилик).\n"
                ,
                "1. Первым делом нужно сварить бульон. Для этого говядину на кости промыть под проточной водой, положить ее в кастрюлю и залить холодной водой. Можно добавить 1 чайную ложку соли. Довести до кипения и убавить огонь до минимума. Борщ нужно готовить на медленном огне, тогда овощи в нем не разварятся и не превратятся в кашу. Говядина варится около 1 часа. Готовность мяса можно проверить по тому, насколько легко оно отделяется от кости. \n"
                ,
                "http://sovetexpert.com/wp-content/uploads/2015/05/103676899_large_borsch-302x250.jpg"));
        newRecipe.add(new Recipe("Cельдь под шубой"
                ,
                "Говядина\n" +
                "Свинина\n" +
                "Огурец\n" +
                "Сельдь, 400 г (филе соленое)\n" +
                "Майонез, 200 мл\n" +
                "Томатная паста  — 1 ст. ложка, или 1 небольшой помидор\n" +
                "Картофель, 3 клубня вареных\n" +
                "Морковь, 2 вареных\n" +
                "Свекла, 1 вареная/запеченная крупная\n" +
                "Луковица, 1 шт."
                ,
                "Филе сельди тщательно осмотреть на предмет наличия косточек, удалить таковые, мелко нарезать филе кубиком.\n" +
                "Отваренные в мундирах овощи (30мин, свекла, возможно – дольше) остудить, затем очистить."
                ,
                "http://foodandhealth.ru/wp-content/uploads/2016/07/seld-pod-shuboy-300x300.jpg"
                ));
        newRecipe.add(new Recipe("Голубцы"
                ,
                "Говядина\n" +
                "Свинина\n" +
                "Огурец\n"
                ,
                "blah-blah"
                ,
                "http://www.1001eda.com/wp-content/uploads/2013/10/410_11_10_2013_4072.jpg"
                ));
        recipeDB.copyOrUpdateRecipe(newRecipe);
    }
    @Override
    protected void onDestroy() {
        recipeDB.close();
        super.onDestroy();
    }
}
