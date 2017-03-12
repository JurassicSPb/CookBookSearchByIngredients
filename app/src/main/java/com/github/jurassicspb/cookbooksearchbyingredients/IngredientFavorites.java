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
    private int checkboxState;

    public IngredientFavorites(){}

    public IngredientFavorites(String ingredient, int image, int state, int checkboxState) {
        this.ingredient = ingredient;
        this.image = image;
        this.state = state;
        this.checkboxState=checkboxState;
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

    public void setCheckboxState(int checkboxState) {
        this.checkboxState = checkboxState;
    }

    public int getCheckboxState() {
        return checkboxState;
    }
}
