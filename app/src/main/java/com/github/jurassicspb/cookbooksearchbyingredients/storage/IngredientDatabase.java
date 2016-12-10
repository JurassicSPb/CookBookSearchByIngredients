package com.github.jurassicspb.cookbooksearchbyingredients.storage;

import com.github.jurassicspb.cookbooksearchbyingredients.CategoryTable;
import com.github.jurassicspb.cookbooksearchbyingredients.Ingredient;
import com.github.jurassicspb.cookbooksearchbyingredients.Recipe;
import com.github.jurassicspb.cookbooksearchbyingredients.SelectedIngredient;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.Sort;

/**
 * Created by Мария on 04.11.2016.
 */

public class IngredientDatabase {
    private Realm realm;

    public IngredientDatabase() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("ingredient_db")
                .build();
//               Realm.deleteRealm(configuration);
        realm = Realm.getInstance(configuration);
    }
    public void copyOrUpdate(List <Ingredient> ingredient) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(ingredient);
        realm.commitTransaction();
    }
    public void copyOrUpdateRecipe(List <Recipe> recipe) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(recipe);
        realm.commitTransaction();
    }
    public List<Ingredient> copyFromRealm(List <Ingredient> ingredient){
        realm.beginTransaction();
        List <Ingredient> newingr = realm.copyFromRealm(ingredient);
        realm.commitTransaction();
        return newingr;
    }
    public void copyOrUpdateCategoryTable(List <CategoryTable> categoryTable) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(categoryTable);
        realm.commitTransaction();
    }
    public void delete (List <Ingredient> ingredient) {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public List<Ingredient> getAll() {
        return realm.where(Ingredient.class).findAllSorted("id", Sort.ASCENDING);
    }
    public List<CategoryTable> getAllCategoryTables() {
        return realm.where(CategoryTable.class).findAllSorted("num", Sort.ASCENDING);
    }

    public List<Ingredient>getCategory(int i){
        return realm.where(Ingredient.class).equalTo("category", i).findAllSorted("id", Sort.ASCENDING);
    }
    public List <Recipe> getRecipe(ArrayList<String> selected){
        RealmQuery <Recipe> query = realm.where(Recipe.class);
        query.contains("ingredient", selected.get(0), Case.INSENSITIVE);
        for (int i=1; i<selected.size(); i++){
            query.or().contains("ingredient", selected.get(i), Case.INSENSITIVE);
        }
        return query.findAll();
    }
    public List<Recipe> getAllRecipes() {
        return realm.where(Recipe.class).findAll();
    }

    public void close() {
        if (!realm.isClosed()) {
            realm.close();
        }
    }
    public void addChangeListener(RealmChangeListener<Realm> changeListener) {
        realm.addChangeListener(changeListener);
    }


}
