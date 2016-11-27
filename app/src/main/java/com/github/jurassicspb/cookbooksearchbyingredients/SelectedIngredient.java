package com.github.jurassicspb.cookbooksearchbyingredients;

import java.util.ArrayList;

/**
 * Created by Мария on 23.11.2016.
 */

public class SelectedIngredient {
    private static ArrayList <String> selectedIngredient = new ArrayList<>();
    private static int count=0;

    public static ArrayList<String> getSelectedIngredient() {
        return selectedIngredient;
    }

    public static void addSelectedIngredient(String selected){
        selectedIngredient.add(selected);
    }

    public static void removeSelectedIngredient (String selected){
        selectedIngredient.remove(selected);
    }
    public static int addCount (){
        return count = count+1;
    }
    public static int removeCount (){
        return count = count-1;
    }
    public static int showCount(){
        return count;
    }
    public static int resetCount() { return count=0;}

}
