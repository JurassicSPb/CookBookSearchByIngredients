package com.ggl.jr.cookbooksearchbyingredients;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by yuri on 12/6/17.
 */

public class IngredientsFromRecipe extends RealmObject {
    private int recipeId;
    @Required
    private String name;
    private int state;

    public IngredientsFromRecipe() {
    }

    public IngredientsFromRecipe(int recipeId, String name, int state) {
        this.recipeId = recipeId;
        this.name = name;
        this.state = state;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
