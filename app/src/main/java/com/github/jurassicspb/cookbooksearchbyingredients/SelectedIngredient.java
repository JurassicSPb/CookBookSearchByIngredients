package com.github.jurassicspb.cookbooksearchbyingredients;

import java.util.ArrayList;

/**
 * Created by Мария on 23.11.2016.
 */

public class SelectedIngredient {
    private static ArrayList <String> selectedIngredient = new ArrayList<>();
    private static ArrayList <String> selectedImage = new ArrayList<>();
    private static int count=0;

    public static ArrayList<String> getSelectedIngredient() {
        return selectedIngredient;
    }

    public static ArrayList<String>copyAllIngr(ArrayList<String> newIngr){
        selectedIngredient = newIngr;
        return selectedIngredient;
    }
    public static ArrayList<String>copyAllImage(ArrayList<String> newImage){
        selectedImage = newImage;
        return selectedImage;
    }
    public static int setCount(int value) {
        count = value;
        return count;
    }

    public static void addSelectedIngredient(String selected, String image){
        selectedIngredient.add(selected);
        selectedImage.add(image);
    }

    public static void removeSelectedIngredient (String selected, String image){
        selectedIngredient.remove(selected);
        selectedImage.remove(image);
    }

    public static ArrayList<String> getSelectedImage() {return selectedImage;}

    public static int resetCount() { return count=0;}

    public static int showCount() { return count=SelectedIngredient.getSelectedIngredient().size();}
}
