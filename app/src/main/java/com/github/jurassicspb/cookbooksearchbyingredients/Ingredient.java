package com.github.jurassicspb.cookbooksearchbyingredients;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Мария on 04.11.2016.
 */

public class Ingredient extends RealmObject{
    @PrimaryKey
    private String id;
    private String category;
    private String ingredient;

    public Ingredient() {}

    public Ingredient(String id, String category, String ingredient){
        this.id=id;
        this.category=category;
        this.ingredient=ingredient;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
