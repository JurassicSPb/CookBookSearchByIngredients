package com.github.jurassicspb.cookbooksearchbyingredients;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Мария on 11.03.2017.
 */

public class IngredientFavorites extends RealmObject{
    @PrimaryKey
    private String ingredient;
    private int image;
    private int state;

    public IngredientFavorites(){}

    public IngredientFavorites(String ingredient, int image, int state) {
        this.ingredient = ingredient;
        this.image = image;
        this.state = state;
    }

    public String getIngredient() {
        return ingredient;
    }

    public int getImage() {
        return image;
    }

    public int getState() {
        return state;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setState(int state) {
        this.state = state;
    }
}
