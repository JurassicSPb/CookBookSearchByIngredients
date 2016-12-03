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
    private int image;

    public Ingredient() {}

    public Ingredient(String id, int category, String ingredient, int image){
        this.id=id;
        this.category=category;
        this.ingredient=ingredient;
        this.image=image;
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

    public int getImage() {return image;}

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setImage(int category) {
        this.image = image;
    }
}
