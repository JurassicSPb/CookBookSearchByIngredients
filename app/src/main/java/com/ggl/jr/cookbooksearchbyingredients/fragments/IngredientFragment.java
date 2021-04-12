package com.ggl.jr.cookbooksearchbyingredients.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ggl.jr.cookbooksearchbyingredients.GridviewImageTextAdapter;
import com.ggl.jr.cookbooksearchbyingredients.IngedientTablayoutActivity;
import com.ggl.jr.cookbooksearchbyingredients.Ingredient;
import com.ggl.jr.cookbooksearchbyingredients.R;
import com.ggl.jr.cookbooksearchbyingredients.SelectedIngredient;
import com.ggl.jr.cookbooksearchbyingredients.storage.IngredientDatabase;
import com.ggl.jr.cookbooksearchbyingredients.storage.MyPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Мария on 12.11.2016.
 */

public class IngredientFragment extends Fragment implements FragmentInterface {
    private List<Ingredient> ingredients;
    private EditText searchEditText;
    private GridviewImageTextAdapter gita;
    private IngredientDatabase ingredientsDB;
    private MyPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.gridview_list, container, false);

        preferences = new MyPreferences(getActivity());
        ArrayList<String> bufferedIngredients = new ArrayList<>(Arrays.asList(preferences.getBufferedIngredients().split(",")));
        ArrayList<String> bufferedImages = new ArrayList<>(Arrays.asList(preferences.getBufferedImages().split(",")));

        if (savedInstanceState != null) {
            if (!bufferedIngredients.get(0).equals("")) {
                SelectedIngredient.copyAllIngr(bufferedIngredients);
                SelectedIngredient.copyAllImage(bufferedImages);
            } else if (preferences.getBufferedFlag()) {
                SelectedIngredient.getSelectedIngredient().clear();
                SelectedIngredient.getSelectedImage().clear();
            } else {
                SelectedIngredient.copyAllIngr(savedInstanceState.getStringArrayList("ingr"));
                SelectedIngredient.copyAllImage(savedInstanceState.getStringArrayList("image"));
            }

            ingredients = savedInstanceState.getParcelableArrayList("ingredients");
            if (SelectedIngredient.showCount() == 0) {
                ((IngedientTablayoutActivity) getActivity()).getSupportActionBar().setTitle(R.string.ingredient_list);
            } else {
                ((IngedientTablayoutActivity) getActivity()).getSupportActionBar().setTitle("Выбрано" + ": " + SelectedIngredient.showCount());
            }
        }

        ingredientsDB = new IngredientDatabase();

        GridView gridview = (GridView) view.findViewById(R.id.gridview);

        gita = new GridviewImageTextAdapter(getActivity(), getIngrbycategory(), ingredientsDB);

        gridview.setAdapter(gita);

        searchEditText = (EditText) view.findViewById(R.id.search);
        Button searchClearButton = (Button) view.findViewById(R.id.search_button);
        searchClearButton.setTypeface(Typeface.SANS_SERIF);

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

        gridview.setOnItemClickListener((parent, view1, position, id) -> {

            String selectedToString = getString(R.string.selected);

            String sel = ingredients.get((int) id).getIngredient();
            String image = String.valueOf(ingredients.get((int) id).getImage());

            if (ingredientsDB.getIngredientStopByName(sel) == null) {

                int ingredientPosition = SelectedIngredient.getSelectedIngredient().indexOf(sel);

                if (ingredientPosition == -1) {
                    if (SelectedIngredient.showCount() < 50) {
                        SelectedIngredient.addSelectedIngredient(sel, image);
                        ingredients.get((int) id).setState(1);
                    } else if (SelectedIngredient.showCount() == 50) {
                        Toast toast = Toast.makeText(getActivity(), R.string.no_more_then_50_ingrs, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } else {
                    SelectedIngredient.removeSelectedIngredient(sel, image);
                    ingredients.get((int) id).setState(0);
                }
                ((IngedientTablayoutActivity) getActivity()).getSupportActionBar().setTitle(selectedToString + ": " + SelectedIngredient.showCount());
                if (SelectedIngredient.showCount() == 0) {
                    ((IngedientTablayoutActivity) getActivity()).getSupportActionBar().setTitle(R.string.ingredient_list);
                }
                gita.notifyDataSetChanged();
            } else {
                Toast toast = Toast.makeText(getActivity(), R.string.remove_from_stop, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        preferences.clearBufferedIngredients();
        preferences.clearBufferedImage();
        preferences.setBufferedFlag(false);

        outState.putStringArrayList("ingr", SelectedIngredient.getSelectedIngredient());
        outState.putStringArrayList("image", SelectedIngredient.getSelectedImage());
        outState.putParcelableArrayList("ingredients", new ArrayList<>(ingredients));
    }

    @Override
    public void fragmentBecameVisible() {
        refreshIngredientState();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshIngredientState();
        if (SelectedIngredient.showCount() == 0) {
            ((IngedientTablayoutActivity) getActivity()).getSupportActionBar().setTitle(R.string.ingredient_list);
        } else {
            ((IngedientTablayoutActivity) getActivity()).getSupportActionBar().setTitle("Выбрано" + ": " + SelectedIngredient.showCount());
        }
    }

    public void refreshIngredientState() {
        for (int i = 0; i < ingredients.size(); i++) {
            String sel = ingredients.get(i).getIngredient();

            if (SelectedIngredient.getSelectedIngredient().contains(sel)) {
                ingredients.get(i).setState(1);
            } else {
                ingredients.get(i).setState(0);
            }

            if (ingredientsDB.getIngredientFavoriteByName(sel) == null) {
                ingredients.get(i).setCheckboxState(0);
            } else {
                ingredients.get(i).setCheckboxState(1);
            }

            if (ingredientsDB.getIngredientStopByName(sel) == null) {
                ingredients.get(i).setStopState(0);
            } else {
                ingredients.get(i).setStopState(1);
            }

        }
        gita.notifyDataSetChanged();
    }

    public void setIngrbycategory(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngrbycategory() {
        return ingredients;
    }

    @Override
    public void onDestroy() {
        ingredientsDB.close();
        super.onDestroy();
    }
}