package com.github.jurassicspb.cookbooksearchbyingredients;

/**
 * Created by Мария on 29.04.2017.
 */

public class RecipeFilter {
    private int id;
    private String name;
    private String ingredient;
    private String category;
    private String description;
    private String image;
    private String calories;

    public RecipeFilter(int id, String name, String ingredient, String category, String description, String calories, String image) {
        this.id=id;
        this.name = name;
        this.ingredient = ingredient;
        this.category=category;
        this.description=description;
        this.calories=calories;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getCategory() {
        return category;
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
}
