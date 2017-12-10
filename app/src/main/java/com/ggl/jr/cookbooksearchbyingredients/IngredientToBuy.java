package com.ggl.jr.cookbooksearchbyingredients;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Мария on 08.04.2017.
 */

public class IngredientToBuy extends RealmObject {
    private String name;
    private String weight;
    private String amount;
    private int checkboxState;
    private int recipeId;
    private RealmList<IngredientsFromRecipe> ingredients;

    public IngredientToBuy() {
    }

    public IngredientToBuy(String name, String weight, String amount, int checkboxState) {
        this.name = name;
        this.weight = weight;
        this.amount = amount;
        this.checkboxState = checkboxState;
    }

    public IngredientToBuy(String name, int recipeId, RealmList<IngredientsFromRecipe> ingredients) {
        this.name = name;
        this.recipeId = recipeId;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public String getWeight() {
        return weight;
    }

    public String getAmount() {
        return amount;
    }

    public int getCheckboxState() {
        return checkboxState;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCheckboxState(int checkboxState) {
        this.checkboxState = checkboxState;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public RealmList<IngredientsFromRecipe> getIngredients() {
        return ingredients;
    }

    public void setIngredients(RealmList<IngredientsFromRecipe> ingredients) {
        this.ingredients = ingredients;
    }
}
