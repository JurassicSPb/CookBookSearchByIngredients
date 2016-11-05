package com.github.jurassicspb.cookbooksearchbyingredients;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Мария on 04.11.2016.
 */

public class Recipe extends RealmObject{
    @PrimaryKey
    private String id;
    private String name;
    private String ingredient;
    private String description;

    public Recipe() {}

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
