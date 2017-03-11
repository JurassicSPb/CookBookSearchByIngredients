package com.github.jurassicspb.cookbooksearchbyingredients.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.github.jurassicspb.cookbooksearchbyingredients.GridviewImageTextAdapter;
import com.github.jurassicspb.cookbooksearchbyingredients.IngedientTablayoutActivity;
import com.github.jurassicspb.cookbooksearchbyingredients.Ingredient;
import com.github.jurassicspb.cookbooksearchbyingredients.IngredientFavorites;
import com.github.jurassicspb.cookbooksearchbyingredients.R;
import com.github.jurassicspb.cookbooksearchbyingredients.SelectedIngredient;
import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Мария on 12.11.2016.
 */

public class IngredientFragment extends Fragment implements FragmentInterface {
    private List<Ingredient> ingredients;
    private GridView gridview;
    private EditText searchEditText;
    private Button searchClearButton;
    private GridviewImageTextAdapter gita;
    private IngredientDatabase ingrFavoritesDB;
    private List<IngredientFavorites> ingredientFavorites;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.gridview_list, container, false);

        if (savedInstanceState != null) {
            SelectedIngredient.copyAllIngr(savedInstanceState.getStringArrayList("ingr"));
            SelectedIngredient.copyAllImage(savedInstanceState.getStringArrayList("image"));
            SelectedIngredient.setCount(savedInstanceState.getInt("count"));
            ingredients = savedInstanceState.getParcelableArrayList("ingredients");
            if (SelectedIngredient.showCount() == 0) {
                ((IngedientTablayoutActivity) getActivity()).getSupportActionBar().setTitle(R.string.ingredient_list);
            } else {
                ((IngedientTablayoutActivity) getActivity()).getSupportActionBar().setTitle("Выбрано" + ": " + SelectedIngredient.showCount());
            }
        }


        ingrFavoritesDB = new IngredientDatabase();
        ingredientFavorites = ingrFavoritesDB.getAllIngrFavorites();

        gridview = (GridView) view.findViewById(R.id.gridview);

        gita = new GridviewImageTextAdapter(getActivity(), getIngrbycategory(), getAllIngrFavs());

        gridview.setAdapter(gita);

        searchEditText = (EditText) view.findViewById(R.id.search);
        searchClearButton = (Button) view.findViewById(R.id.search_button);
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

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                TextView tv = (TextView) view.findViewById(R.id.textpart);
//                String sel = tv.getText().toString();
//                Ingredient item = gita.getItem(position);
//                String sel = item.getIngredient();
//                String image = String.valueOf(item.getImage());
//                long itemId = gita.getItemId(position);

                String selectedToString = getString(R.string.selected);

                String sel = ingredients.get((int) id).getIngredient();
                String image = String.valueOf(ingredients.get((int) id).getImage());

                int ingredientPosition = SelectedIngredient.getSelectedIngredient().indexOf(sel);

                if (ingredientPosition == -1) {
                    if (SelectedIngredient.showCount() < 15) {

//                        ArrayList<IngredientFavorites> newIngrFav = new ArrayList<>();
//                        newIngrFav.add(new IngredientFavorites(ingredients.get((int) id).getIngredient(),
//                                ingredients.get((int) id).getImage(), ingredients.get((int) id).getState()));
//                        ingrFavoritesDB.copyOrUpdateIngrFavorites(newIngrFav);

                        SelectedIngredient.addSelectedIngredient(sel, image);
                        SelectedIngredient.showCount();
                        ingredients.get((int) id).setState(1);
                        gita.notifyDataSetChanged();
                    } else if (SelectedIngredient.showCount() == 15) {
                        Toast toast = Toast.makeText(getActivity(), R.string.no_more_than_15, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
//                if (ingredientPosition>-1 &&  ingredients.get((int)id).getState()==1)
                else {

//                    ingrFavoritesDB.deleteIngrFavoritePosition(ingredients.get((int) id).getIngredient());

//                    SelectedIngredient.removeCount();
                    SelectedIngredient.removeSelectedIngredient(sel, image);
                    SelectedIngredient.showCount();
                    ingredients.get((int) id).setState(0);
                    gita.notifyDataSetChanged();
                }
                ((IngedientTablayoutActivity) getActivity()).getSupportActionBar().setTitle(selectedToString + ": " + SelectedIngredient.showCount());
                if (SelectedIngredient.showCount() == 0) {
                    ((IngedientTablayoutActivity) getActivity()).getSupportActionBar().setTitle(R.string.ingredient_list);
                }
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("ingr", SelectedIngredient.getSelectedIngredient());
        outState.putStringArrayList("image", SelectedIngredient.getSelectedImage());
        outState.putInt("count", SelectedIngredient.showCount());
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

    @Override
    public void onDestroy() {
        ingrFavoritesDB.close();
        super.onDestroy();
    }

    public void refreshIngredientState() {
        for (int i = 0; i < ingredients.size(); i++) {
            String sel = ingredients.get(i).getIngredient();
            int ingredientPosition = SelectedIngredient.getSelectedIngredient().indexOf(sel);
            if (ingredientPosition > -1) {
                ingredients.get(i).setState(1);
            } else {
                ingredients.get(i).setState(0);
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

    public List<IngredientFavorites> getAllIngrFavs() {
        return ingredientFavorites;
    }

}