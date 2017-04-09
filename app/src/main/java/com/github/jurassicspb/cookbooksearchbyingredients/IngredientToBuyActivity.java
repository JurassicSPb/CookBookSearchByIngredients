package com.github.jurassicspb.cookbooksearchbyingredients;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;

import java.util.List;

/**
 * Created by Мария on 08.04.2017.
 */

public class IngredientToBuyActivity extends AppCompatActivity {
    private List<IngredientToBuy> ingredientsToBuy;
    private IngredientDatabase ingrsToBuyDB;
    private String nameToBuy;
    private String weightToBuy;
    private String weightToBuy2;
    private String amountToBuy;
    private final static String KILOS = "кг.";
    private final static String GRAMS = "гр.";
    private final static String PIECES = "шт.";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ingredients_to_buy_recyclerview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle(R.string.shopping_list);

        ingrsToBuyDB = new IngredientDatabase();
        performIngredientsToBuy();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        IngredientToBuyAdapter adapter = new IngredientToBuyAdapter(ingredientsToBuy);
        recyclerView.setAdapter(adapter);

        EditText name = (EditText) findViewById(R.id.edit_name);
        EditText weight = (EditText) findViewById(R.id.edit_weight);
        EditText weight2 = (EditText) findViewById(R.id.edit_weight2);
        EditText amount = (EditText) findViewById(R.id.edit_count);


        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nameToBuy = name.getText().toString();
            }
        });

        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                weightToBuy = weight.getText() + " " + GRAMS;
            }
        });

        weight2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                weightToBuy2 = weight2.getText() + " " + KILOS;
            }
        });

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                amountToBuy = amount.getText() + " " + PIECES;
            }
        });

        Button okButton = (Button) findViewById(R.id.edit_button);

        okButton.setOnClickListener(v -> {
            Toast toast;
            if (nameToBuy == null || nameToBuy.trim().equals("")) {
                toast = Toast.makeText(getApplication(), R.string.empty_name, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else if (ingredientsToBuy.size() < 50) {
                IngredientToBuy ingredientToBuy;
                if (weightToBuy2 == null || weightToBuy2.trim().equals(KILOS)) {
                    if (amountToBuy == null || amountToBuy.trim().equals(PIECES)) {
                        ingredientToBuy = new IngredientToBuy(nameToBuy, weightToBuy, "", 0);
                    } else
                        ingredientToBuy = new IngredientToBuy(nameToBuy, weightToBuy, amountToBuy, 0);
                } else if (weightToBuy == null || weightToBuy.trim().equals(GRAMS)) {
                    if (amountToBuy == null || amountToBuy.trim().equals(PIECES)) {
                        ingredientToBuy = new IngredientToBuy(nameToBuy, weightToBuy2, "", 0);
                    } else
                        ingredientToBuy = new IngredientToBuy(nameToBuy, weightToBuy2, amountToBuy, 0);
                } else {
                    if (amountToBuy == null || amountToBuy.trim().equals(PIECES)) {
                        ingredientToBuy = new IngredientToBuy(nameToBuy, weightToBuy, "", 0);
                    } else
                        ingredientToBuy = new IngredientToBuy(nameToBuy, weightToBuy, amountToBuy, 0);
                }
                ingrsToBuyDB.copyIngredientToBuy(ingredientToBuy);
                ingredientsToBuy = ingrsToBuyDB.getAllIngrToBuy();
                adapter.notifyDataSetChanged();
                toast = Toast.makeText(getApplication(), R.string.added_successfully, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                toast = Toast.makeText(getApplication(), R.string.no_more_than_50, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    private void performIngredientsToBuy() {
        ingredientsToBuy = ingrsToBuyDB.getAllIngrToBuy();
    }

    @Override
    protected void onDestroy() {
        ingrsToBuyDB.close();
        super.onDestroy();
    }
}
