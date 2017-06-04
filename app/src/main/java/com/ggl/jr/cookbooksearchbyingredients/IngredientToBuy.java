package com.ggl.jr.cookbooksearchbyingredients;

import io.realm.RealmObject;

/**
 * Created by Мария on 08.04.2017.
 */

public class IngredientToBuy extends RealmObject{
    private String name;
    private String weight;
    private String amount;
    private int checkboxState;

    public IngredientToBuy() {}

    public IngredientToBuy(String name, String weight, String amount, int checkboxState) {
        this.name = name;
        this.weight = weight;
        this.amount = amount;
        this.checkboxState = checkboxState;
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
}
