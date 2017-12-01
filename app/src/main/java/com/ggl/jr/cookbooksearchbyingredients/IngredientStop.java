package com.ggl.jr.cookbooksearchbyingredients;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yuri on 11/29/17.
 */

public class IngredientStop extends RealmObject {
    @PrimaryKey
    private String ingredient;
    private int image;
    private int checkboxState;

    public IngredientStop() {
    }

    public IngredientStop(String ingredient, int image, int checkboxState) {
        this.ingredient = ingredient;
        this.image = image;
        this.checkboxState = checkboxState;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getCheckboxState() {
        return checkboxState;
    }

    public void setCheckboxState(int checkboxState) {
        this.checkboxState = checkboxState;
    }
}
