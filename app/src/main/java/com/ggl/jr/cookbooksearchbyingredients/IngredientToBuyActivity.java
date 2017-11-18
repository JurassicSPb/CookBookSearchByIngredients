package com.ggl.jr.cookbooksearchbyingredients;

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

import com.ggl.jr.cookbooksearchbyingredients.storage.IngredientDatabase;

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
        if (Metrics.smallestWidth()>600) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_tablets);
        } else {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_phones);
        }
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
        Button clearButton = (Button) findViewById(R.id.clear_button);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nameToBuy = s.toString();
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
                if (s.toString().length() == 0) {
                    weight2.setFocusable(true);
                    weight2.setFocusableInTouchMode(true);
                } else {
                    weight2.setFocusable(false);
                    weight2.setFocusableInTouchMode(false);
                }
                weightToBuy = s.toString();
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
                if (s.toString().length() == 0) {
                    weight.setFocusable(true);
                    weight.setFocusableInTouchMode(true);
                } else {
                    weight.setFocusable(false);
                    weight.setFocusableInTouchMode(false);
                }
                weightToBuy2 = s.toString();
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
                amountToBuy = s.toString();
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

                if (weightToBuy != null && weightToBuy.length() > 0) {
                    if (amountToBuy == null || amountToBuy.length() == 0) {
                        ingredientToBuy = new IngredientToBuy(nameToBuy, weightToBuy + " " + GRAMS, "", 0);
                    } else
                        ingredientToBuy = new IngredientToBuy(nameToBuy, weightToBuy + " " + GRAMS, amountToBuy + " " + PIECES, 0);
                } else if (weightToBuy2 != null && weightToBuy2.length() > 0) {
                    if (amountToBuy == null || amountToBuy.length() == 0) {
                        ingredientToBuy = new IngredientToBuy(nameToBuy, weightToBuy2 + " " + KILOS, "", 0);
                    } else
                        ingredientToBuy = new IngredientToBuy(nameToBuy, weightToBuy2 + " " + KILOS, amountToBuy + " " + PIECES, 0);
                } else if (amountToBuy != null && amountToBuy.length()>0) {
                    ingredientToBuy = new IngredientToBuy(nameToBuy, "", amountToBuy + " " + PIECES, 0);
                } else {
                    ingredientToBuy = new IngredientToBuy(nameToBuy, "", "", 0);
                }

                ingrsToBuyDB.copyIngredientToBuy(ingredientToBuy);
                performIngredientsToBuy();
                adapter.notifyDataSetChanged();
                toast = Toast.makeText(getApplication(), R.string.added_successfully, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                toast = Toast.makeText(getApplication(), R.string.no_more_then_50_records, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        clearButton.setOnClickListener(v -> name.setText(""));
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
