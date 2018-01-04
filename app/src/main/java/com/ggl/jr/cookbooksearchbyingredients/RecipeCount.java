package com.ggl.jr.cookbooksearchbyingredients;

/**
 * Created by Мария on 29.04.2017.
 */

public class RecipeCount {
    private int count;
    private int id;
    private String name;
    private String ingredient;
    private String category;
    private String description;
    private String image;
    private String calories;

    public RecipeCount(int count, int id, String name, String ingredient, String category, String description, String image, String calories) {
        this.count = count;
        this.id = id;
        this.name = name;
        this.ingredient = ingredient;
        this.category = category;
        this.description = description;
        this.image = image;
        this.calories = calories;
    }

    public int getCount() {
        return count;
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
