package com.github.jurassicspb.cookbooksearchbyingredients;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Мария on 04.11.2016.
 */

public class Ingredient extends RealmObject{
    @PrimaryKey
    private String id;
    private int category;
    private String ingredient;

    public Ingredient() {}

    public Ingredient(String id, int category, String ingredient){
        this.id=id;
        this.category=category;
        this.ingredient=ingredient;
    }

    public String getId() {
        return id;
    }

    public int getCategory() {
        return category;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
