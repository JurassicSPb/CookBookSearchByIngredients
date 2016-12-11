package com.github.jurassicspb.cookbooksearchbyingredients.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.jurassicspb.cookbooksearchbyingredients.GridviewImageTextAdapter;
import com.github.jurassicspb.cookbooksearchbyingredients.IngedientTablayoutActivity;
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
    private GridView gridview;
    private EditText searchEditText;
    private Button searchClearButton;
    private GridviewImageTextAdapter gita;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gridview_list, container, false);
        gridview = (GridView) view.findViewById(R.id.gridview);

        gita = new GridviewImageTextAdapter(getActivity(), getIngrbycategory());
        gridview.setAdapter(gita);

        searchEditText = (EditText) view.findViewById(R.id.search);
        searchClearButton = (Button) view.findViewById(R.id.search_button);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                gita.getFilter().filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchClearButton.setOnClickListener(v -> searchEditText.setText(""));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.textpart);
                String selectedToString = getString(R.string.selected);
//                String sel = tv.getText().toString();
//                String sel = ingredients.get(position).getIngredient();
                Ingredient item = gita.getItem(position);
                String sel = item.getIngredient();
//                for (int i=0; i<10; i++){
//                    if (position==i){
//                        parent.getChildAt(i).setBackgroundColor(Color.CYAN);
//                    }
//                }

//                Log.d(IngredientFragment.class.getSimpleName(), "here" + selected);
                int ingredientPosition = SelectedIngredient.getSelectedIngredient().indexOf(sel);
                if (ingredientPosition==-1) {
                    if (SelectedIngredient.showCount()<10) {
                        SelectedIngredient.addCount();
                        SelectedIngredient.addSelectedIngredient(sel);
                        tv.setBackgroundColor(Color.GREEN);

                    }
                    else if (SelectedIngredient.showCount()==10){
                        Toast toast = Toast.makeText(getActivity(), R.string.no_more_than_10,Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
                else {
                    SelectedIngredient.removeCount();
                    SelectedIngredient.removeSelectedIngredient(sel);
                    (view.findViewById(R.id.textpart)).setBackgroundResource(R.color.colorHeadline);
                }
                ((IngedientTablayoutActivity)getActivity()).getSupportActionBar().setTitle(selectedToString+": " + SelectedIngredient.showCount());
                if (SelectedIngredient.showCount()==0){
                    ((IngedientTablayoutActivity)getActivity()).getSupportActionBar().setTitle(R.string.ingredient_list);
                }
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