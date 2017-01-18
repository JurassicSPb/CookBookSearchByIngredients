package com.github.jurassicspb.cookbooksearchbyingredients;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Мария on 17.12.2016.
 */

public class Favorites extends RealmObject{
    @PrimaryKey
    private String name;
    private String ingredient;
    private String category;
    private String description;
    private String image;
    private String calories;

    public Favorites() {}

    public Favorites(String name, String ingredient, String category, String description, String calories, String image) {
        this.name = name;
        this.ingredient = ingredient;
        this.category = category;
        this.description=description;
        this.calories=calories;
        this.image = image;
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

    public String getImage() {
        return image;
    }


    public String getCalories() {
        return calories;
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

    public void setImage(String image) {
        this.image = image;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}
