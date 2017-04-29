package com.github.jurassicspb.cookbooksearchbyingredients;

/**
 * Created by Мария on 29.04.2017.
 */

public class RecipeCount {
    private int count;
    private Recipe recipe;

    public RecipeCount(int count, Recipe recipe) {
        this.count = count;
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
