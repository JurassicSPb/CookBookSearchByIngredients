package com.github.jurassicspb.cookbooksearchbyingredients;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Мария on 04.11.2016.
 */

public class IngredientActivity extends AppCompatActivity{
    private IngredientDatabase ingredientDB;
    private IngredientDatabase ingredientDBDelete;
    private List<Ingredient> ingredients;
    private Button button;
    private GridView gridview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview_list);
        ingredientDB = new IngredientDatabase();
//        button = (Button) findViewById(R.id.button);
        gridview = (GridView) findViewById(R.id.gridview);

        gridview.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_MOVE){
                return true;
            }
            return false;
        });

        createIngredients();
//        delete();
//        button.setOnClickListener(v-> performIngredients());

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
