package com.github.jurassicspb.cookbooksearchbyingredients.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.github.jurassicspb.cookbooksearchbyingredients.GridviewImageTextAdapter;
import com.github.jurassicspb.cookbooksearchbyingredients.IngedientTablayoutActivity;
import com.github.jurassicspb.cookbooksearchbyingredients.Ingredient;
import com.github.jurassicspb.cookbooksearchbyingredients.OnListItemClickListener;
import com.github.jurassicspb.cookbooksearchbyingredients.R;
import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Мария on 12.11.2016.
 */

public class MeatFragment extends Fragment{
    private IngredientDatabase ingredientDB;
    private List<Ingredient> ingredients;
    private int [] image = {R.drawable.circle, R.drawable.circle};
    private OnListItemClickListener clickListener = (v, position) -> {
    };
    private GridView gridview;
    private Ingredient ingredient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ingredientDB = new IngredientDatabase();
//        createIngredients();
//        delete();
//        if (ingredient.getCategory().equals("мясо")){
//            performIngredients();
//        }
        performIngredients();

        View view = inflater.inflate(R.layout.gridview_list, container, false);
        gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(new GridviewImageTextAdapter(getActivity(), ingredients, image, clickListener));

        gridview.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_MOVE){
                return true;
            }
            return false;
        });
        return view;
    }
    private void createIngredients(){
        ArrayList<Ingredient> newIngredient = new ArrayList<>();
        newIngredient.add(new Ingredient("1.1", "мясо", "говядина"));
        newIngredient.add(new Ingredient("1.2", "мясо", "свинина"));
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
