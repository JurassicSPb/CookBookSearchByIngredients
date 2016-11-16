package com.github.jurassicspb.cookbooksearchbyingredients.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


import com.github.jurassicspb.cookbooksearchbyingredients.GridviewImageTextAdapter;
import com.github.jurassicspb.cookbooksearchbyingredients.Ingredient;
import com.github.jurassicspb.cookbooksearchbyingredients.R;
import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;

/**
 * Created by Мария on 12.11.2016.
 */

public class MeatFragment extends Fragment{
    private IngredientDatabase ingredientDB;
    private List<Ingredient> ingredients;
    private int [] image = {R.drawable.ic_circle, R.drawable.ic_circle, R.drawable.ic_circle};
    private GridView gridview;
    private ArrayList <String> selectedIngredients = new ArrayList<>();
    private GridviewImageTextAdapter gita;
    private int count=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ingredientDB = new IngredientDatabase();
//        ingredientDB.close();

            createIngredients();
            performIngredients();
//            delete();

        View view = inflater.inflate(R.layout.gridview_list, container, false);
        gridview = (GridView) view.findViewById(R.id.gridview);
        gita = new GridviewImageTextAdapter(getActivity(), ingredients, image);
        gridview.setAdapter(gita);

        gridview.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_MOVE){
                return true;
            }
            return false;
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedIngredient = ingredients.get(position).getIngredient();
                int ingredientPosition = selectedIngredients.indexOf(selectedIngredient);
                if (ingredientPosition==-1){
                    selectedIngredients.add(selectedIngredient);
                    count = count + 1;
                }
                else
                {
                    selectedIngredients.remove(ingredientPosition);
                    count = count - 1;
                }
                    Toast.makeText(getActivity(), "Hello: " + selectedIngredients + count, Toast.LENGTH_SHORT).show();
                }
        });
        return view;
    }
    private void createIngredients(){
        ArrayList<Ingredient> newIngredient = new ArrayList<>();
        newIngredient.add(new Ingredient("1.1", "мясо", "говядина"));
        newIngredient.add(new Ingredient("1.2", "мясо", "свинина"));
        newIngredient.add(new Ingredient("1.3", "овощи", "томат"));
        ingredientDB.copyOrUpdate(newIngredient);

    }
    private void performIngredients(){
        ingredients = ingredientDB.getAll();
    }

    public void delete(){
        ArrayList <Ingredient> newIngredient = new ArrayList<>();
        ingredientDB.delete(newIngredient);
    }

    @Override
    public void onDestroyView() {
        ingredientDB.close();
        super.onDestroyView();
    }
}
