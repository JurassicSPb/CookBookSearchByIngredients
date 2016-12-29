package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Intent;
import android.content.res.AssetManager;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

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
            String name = adapter.getRecipe(position).getName();
            String photo = adapter.getRecipe(position).getImage();
            String ingredients = adapter.getRecipe(position).getIngredient();
            String description = adapter.getRecipe(position).getDescription();
            String calories = adapter.getRecipe(position).getCalories();

            Intent intent = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("photo", photo);
            intent.putExtra("ingredients", ingredients);
            intent.putExtra("description", description);
            intent.putExtra("calories", calories);
            startActivity(intent);

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeDB = new IngredientDatabase();
        preferences = new MyPreferences(this);
//        delete();
//        preferences.clearPrefs();

        setContentView(R.layout.recipelist_recyclerview);

        if (preferences.getFlagRecipes()) {
            if (Locale.getDefault().getLanguage().equals("ru")) {
                createRecipes("rus");
            }
            else {
                createRecipes("eng");
            }
            preferences.setFlagRecipe(false);
            Log.d(RecipeListActivity.class.getSimpleName(), "hello");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle("");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        return newRecipes;
    }

    public void delete(){
        ArrayList <Recipe> newRecipe = new ArrayList<>();
        recipeDB.deleteRecipes(newRecipe);
    }

    private void createRecipes(String language){
        ArrayList<Recipe> newRecipe = new ArrayList<>();
        AssetManager am = getApplicationContext().getAssets();

        try {
            String fileList [] = am.list(language);
            Log.d(RecipeListActivity.class.getSimpleName(), "herehere"+ fileList.length);
                for (int i=0; i<fileList.length; i++){
                    InputStream is = am.open(fileList[i]);
                    Log.d(RecipeListActivity.class.getSimpleName(), "cccccc"+ fileList[i]);
                    int size = is.available();
                    byte [] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    String json = new String(buffer, "UTF-8");
                    Log.d(RecipeListActivity.class.getSimpleName(), "bbbbb"+ json);
                    try {
                        JSONObject obj = new JSONObject(json);
                        String name = obj.getString("name");
                        String ingredients = obj.getString("ingredients");
                        String description = obj.getString("description");
                        String calories = obj.getString("calories");
                        String image = obj.getString("image");
                        newRecipe.add(new Recipe(name, ingredients, description, calories, image));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
    }
        recipeDB.copyOrUpdateRecipe(newRecipe);
//        try {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getAssets().open("borscht.txt")));
//                while ((line = reader.readLine()) != null) {
//                    String[] information = line.split("~");
//                    String name = information[0];
//                    Log.d(RecipeListActivity.class.getSimpleName(), "herehere" + name);
//                    String ingredients = information[1];
//                    String description = information[2];
//                    String calories = information[3];
//                    String image = information[4];
//                    newRecipe.add(new Recipe(name, ingredients, description, calories, image));
//                }
//
//                reader.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        newRecipe.add(new Recipe("Сельдь под шубой"
//                ,
//                "Говядина\n" +
//                "Свинина\n" +
//                "Огурец\n" +
//                "Сельдь, 400 г (филе соленое)\n" +
//                "Майонез, 200 мл\n" +
//                "Томатная паста  — 1 ст. ложка, или 1 небольшой помидор\n" +
//                "Картофель, 3 клубня вареных\n" +
//                "Морковь, 2 вареных\n" +
//                "Свекла, 1 вареная/запеченная крупная\n" +
//                "Луковица, 1 шт."
//                ,
//                "Филе сельди тщательно осмотреть на предмет наличия косточек, удалить таковые, мелко нарезать филе кубиком.\n" +
//                "Отваренные в мундирах овощи (30мин, свекла, возможно – дольше) остудить, затем очистить."
//                ,
//                "120"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316UXdDYzBTT2hCM2M/view?usp=drivesdk]seld-pod-shuboy-300x300.jpg"
//                ));
//        newRecipe.add(new Recipe("Голубцы"
//                ,
//                "Говядина\n" +
//                "Свинина\n" +
//                "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//                ));
//        newRecipe.add(new Recipe("цымцм"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("ммм"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("цуацуацу"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("ваиукпи"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("вйцвйцмйп"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("икенео"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("ваивиу"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("цйукцук"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("ывмывмывмывмывмывм"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("йцвйцвйцвйцв"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("ссцсцфсфс"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("ывмывмсцы"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("ккуп"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("мыы"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("йцвйвйвйцв"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("рпукупупцукп"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("ывц"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("фсысйфцвайвай"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("фвйц"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("йцвйцвйц"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("фысфцв"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("цукцуацу"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("ясячс"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("йайцайц"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("йцайцйца"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("ыаывыва"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
//        newRecipe.add(new Recipe("цуа"
//                ,
//                "Говядина\n" +
//                        "Свинина\n" +
//                        "Огурец"
//                ,
//                "blah-blah"
//                ,
//                "125"
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316enc0djJXZkw0Zms/view?usp=drivesdk]410_11_10_2013_4072.jpg"
//        ));
        }
//    private void createRecipesENG(){
//        ArrayList<Recipe> newRecipe = new ArrayList<>();
//        newRecipe.add(new Recipe("Borscht"
//                ,
//                "Beef\n"+
//                "Pork"
//                ,
//                "hey-hey"
//                ,
//                ""
//                ,
//                "https://drive.google.com/file/d/0B0e6uiJx0316WjhLYzk4cHZJc2s/view?usp=drivesdk]103676899_borsch.jpg"
//        ));
//        recipeDB.copyOrUpdateRecipe(newRecipe);
//    }
    @Override
    protected void onDestroy() {
        recipeDB.close();
        super.onDestroy();
    }
}
