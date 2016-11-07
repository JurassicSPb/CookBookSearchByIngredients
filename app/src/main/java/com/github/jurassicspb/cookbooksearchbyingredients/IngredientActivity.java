package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Мария on 04.11.2016.
 */

public class IngredientActivity extends AppCompatActivity{
    private IngredientDatabase ingredientDB;
    private List<Ingredient> ingredients;
    private Button button;
    private GridView gridview;
    private GridLayout layout;
    private int [] image = {R.drawable.circle, R.drawable.circle, R.drawable.circle};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingredientDB = new IngredientDatabase();
        createIngredients();
        performIngredients();
        setContentView(R.layout.gridview_list);
      //  button = (Button) findViewById(R.id.button);
        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageTextAdapter(this, ingredients, image));

//        layout = (GridLayout) findViewById(R.id.layout);
       // layout.setColumnCount(2);



        gridview.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_MOVE){
                return true;
            }
            return false;
        });
//        delete();
//        button.setOnClickListener(v-> performIngredients());
        Log.d(IngredientActivity.class.getSimpleName(), "Hello");
        //Log.d(IngredientActivity.class.getSimpleName(), String.valueOf(ingredients.size()));
//        int total = ingredients.size();
//        int col = 2;
//        int rows = total/col;
//        layout.setColumnCount(col);
//        layout.setRowCount(rows + 2);
//        GridLayout.LayoutParams param =new GridLayout.LayoutParams();
//        param.setGravity(Gravity.TOP);
        //param.height = R.dimen.headline_height;
//        param.height = (int) getResources().getDimension(R.dimen.headline_height);
//        param.width = GridLayout.LayoutParams.MATCH_PARENT;
//        param.columnSpec = GridLayout.spec(0,2,GridLayout.CENTER);
//        param.rowSpec = GridLayout.spec(0);
//        TextView text = new TextView(this);
//        text.setText("Ingredients");
//        text.setGravity(Gravity.CENTER);
//        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
//        text.setBackgroundResource(R.color.colorHeadline);
//
//        text.setLayoutParams(param);
//        layout.addView(text);
//        for (int i=0, c=0,r=1; i<ingredients.size();i++,c++)
//        {
//            if (c==col)
//            {
//                c=0;
//                r++;
//            }
//            param =new GridLayout.LayoutParams();
//
//            param.setGravity(Gravity.FILL);
            //param.columnSpec = GridLayout.spec(0, 10);
            //param.rightMargin = 5;
            //param.topMargin = 5;
//            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
//            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
//            Log.d(IngredientActivity.class.getSimpleName(), String.valueOf(c));
//            Log.d(IngredientActivity.class.getSimpleName(), String.valueOf(r));
//            param.columnSpec = GridLayout.spec(c);
//            param.rowSpec = GridLayout.spec(r);
            //Log.d(IngredientActivity.class.getSimpleName(), ingredients.get(i).getId());
//            button = new Button(this);
//            button.setText(ingredients.get(i).getIngredient());
//            button.setId(Integer.valueOf(ingredients.get(i).getId()));
//
//            button.setLayoutParams(param);
//            layout.addView(button);
//        }
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

        //button.setText(String.valueOf(ingredients));
    }

    @Override
    protected void onDestroy() {
        ingredientDB.close();
        super.onDestroy();
    }
}
