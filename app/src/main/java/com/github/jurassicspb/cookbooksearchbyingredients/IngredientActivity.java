package com.github.jurassicspb.cookbooksearchbyingredients;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Мария on 04.11.2016.
 */

public class IngredientActivity extends AppCompatActivity{
    private IngredientDatabase ingredientDB;
    private IngredientDatabase ingredientDBDelete;
    private List<Ingredient> ingredients;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meat_list);
        ingredientDB = new IngredientDatabase();
        button = (Button) findViewById(R.id.button);

        createIngredients();
//        delete();
        button.setOnClickListener(v-> performIngredients());

        Log.d(IngredientActivity.class.getSimpleName(), "onCreate");

    }
    private void createIngredients(){
        ArrayList <Ingredient> newIngredient = new ArrayList<>();
        newIngredient.add(new Ingredient("1", "мясо", "говядина"));
        newIngredient.add(new Ingredient("2", "мясо", "свинина"));
        newIngredient.add(new Ingredient("3", "курица", "индейка"));
        ingredientDB.copyOrUpdate(newIngredient);

    }
    public void delete(){
        ArrayList <Ingredient> newIngredient = new ArrayList<>();
        ingredientDB.delete(newIngredient);
    }

    private void performIngredients(){
        ingredients = ingredientDB.getAll();
        button.setText(String.valueOf(ingredients));
    }

    @Override
    protected void onDestroy() {
        ingredientDB.close();
        super.onDestroy();
    }
}
