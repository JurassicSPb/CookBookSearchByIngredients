package com.github.jurassicspb.cookbooksearchbyingredients.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.github.jurassicspb.cookbooksearchbyingredients.SelectedIngredient;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Мария on 12.11.2016.
 */

public class IngredientFragment extends Fragment{
    private List<Ingredient> ingredients;
    private int [] image = {R.drawable.ic_circle, R.drawable.ic_circle, R.drawable.ic_circle, R.drawable.ic_circle};
    private GridView gridview;
    private GridviewImageTextAdapter gita;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gridview_list, container, false);
        gridview = (GridView) view.findViewById(R.id.gridview);

        gita = new GridviewImageTextAdapter(getActivity(), getIngrbycategory(), image);
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
                String selected = ingredients.get(position).getIngredient();
                int ingredientPosition = SelectedIngredient.getSelectedIngredient().indexOf(selected);
                if (ingredientPosition==-1){
                        SelectedIngredient.addCount();
                        SelectedIngredient.addSelectedIngredient(selected);
                }
                else {
                    SelectedIngredient.removeCount();
                    SelectedIngredient.removeSelectedIngredient(selected);
                }
                    Toast.makeText(getActivity(), "Ингредиенты: " + SelectedIngredient.getSelectedIngredient() + SelectedIngredient.showCount() + " ", Toast.LENGTH_SHORT).show();
                }
        });
        return view;
    }

    public void setIngrbycategory(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    public List<Ingredient> getIngrbycategory() {
        return ingredients;
    }
}
