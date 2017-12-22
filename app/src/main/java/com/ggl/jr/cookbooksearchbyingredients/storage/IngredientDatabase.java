package com.ggl.jr.cookbooksearchbyingredients.storage;

import com.ggl.jr.cookbooksearchbyingredients.Categories;
import com.ggl.jr.cookbooksearchbyingredients.CategoryTable;
import com.ggl.jr.cookbooksearchbyingredients.Favorites;
import com.ggl.jr.cookbooksearchbyingredients.Ingredient;
import com.ggl.jr.cookbooksearchbyingredients.IngredientFavorites;
import com.ggl.jr.cookbooksearchbyingredients.IngredientStop;
import com.ggl.jr.cookbooksearchbyingredients.IngredientToBuy;
import com.ggl.jr.cookbooksearchbyingredients.IngredientsFromRecipe;
import com.ggl.jr.cookbooksearchbyingredients.Recipe;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Мария on 04.11.2016.
 */

public class IngredientDatabase {
    private Realm realm;

    public IngredientDatabase() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("ingredient_db")
                .schemaVersion(2)
                .migration(new Migration())
                .build();
//               Realm.deleteRealm(configuration);
        realm = Realm.getInstance(configuration);
    }

    public void copyOrUpdate(List<Ingredient> ingredient) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(ingredient);
        realm.commitTransaction();
    }

    public void copyOrUpdateRecipe(List<Recipe> recipe) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(recipe);
        realm.commitTransaction();
    }

    public void copyOrUpdateFavorites(Favorites favorite) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(favorite);
        realm.commitTransaction();
    }

    public void copyOrUpdateIngrFavorites(IngredientFavorites ingrFavorites) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(ingrFavorites);
        realm.commitTransaction();
    }

    public void copyOrUpdateIngrStop(IngredientStop ingredientStop) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(ingredientStop);
        realm.commitTransaction();
    }

    public List<IngredientFavorites> getAllIngrFavoritesSorted() {
        realm.beginTransaction();
        RealmResults<IngredientFavorites> results = realm.where(IngredientFavorites.class).findAllSorted("ingredient", Sort.ASCENDING);
        List<IngredientFavorites> newIngrFav = realm.copyFromRealm(results);
        realm.commitTransaction();
        return newIngrFav;
    }

    public List<IngredientStop> getAllIngrStopSorted() {
        realm.beginTransaction();
        RealmResults<IngredientStop> results = realm.where(IngredientStop.class).findAllSorted("ingredient", Sort.ASCENDING);
        List<IngredientStop> newIngrStop = realm.copyFromRealm(results);
        realm.commitTransaction();
        return newIngrStop;
    }

    public List<IngredientFavorites> getAllIngrFavoritesUnsorted() {
        return realm.where(IngredientFavorites.class).findAll();
    }

    public List<IngredientStop> getAllIngrStopUnsorted() {
        return realm.where(IngredientStop.class).findAll();
    }


    public void deleteIngrFavoritePosition(String name) {
        realm.beginTransaction();
        IngredientFavorites results = realm.where(IngredientFavorites.class).equalTo("ingredient", name).findFirst();
        results.deleteFromRealm();
        realm.commitTransaction();
    }

    public void deleteIngrStopPosition(String name) {
        realm.beginTransaction();
        IngredientStop results = realm.where(IngredientStop.class).equalTo("ingredient", name).findFirst();
        results.deleteFromRealm();
        realm.commitTransaction();
    }

    public void copyOrUpdateCategories(List<Categories> categories) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(categories);
        realm.commitTransaction();
    }

    public List<Favorites> getAllFavorites() {
        return realm.where(Favorites.class).findAll();
    }

    public List<Favorites> getFavorite(Integer id) {
        return realm.where(Favorites.class).equalTo("id", id).findAll();
    }

    public void deleteFavoritePosition(Integer id) {
        realm.beginTransaction();
        Favorites results = realm.where(Favorites.class).equalTo("id", id).findFirst();
        results.deleteFromRealm();
        realm.commitTransaction();
    }

    public List<Ingredient> copyFromRealm(List<Ingredient> ingredient) {
        realm.beginTransaction();
        List<Ingredient> newingr = realm.copyFromRealm(ingredient);
        realm.commitTransaction();
        return newingr;
    }

    public void copyOrUpdateCategoryTable(List<CategoryTable> categoryTable) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(categoryTable);
        realm.commitTransaction();
    }

    public void delete(List<Ingredient> ingredient) {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public void deleteRecipes(List<Recipe> recipe) {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public List<Ingredient> getAll() {
        return realm.where(Ingredient.class).findAllSorted("ingredient", Sort.ASCENDING);
    }

    public Ingredient getIngredientByName(String name) {
        return realm.where(Ingredient.class).equalTo("ingredient", name).findFirst();
    }

    public IngredientFavorites getIngredientFavoriteByName(String name) {
        return realm.where(IngredientFavorites.class).equalTo("ingredient", name).findFirst();
    }

    public IngredientStop getIngredientStopByName(String name) {
        return realm.where(IngredientStop.class).equalTo("ingredient", name).findFirst();
    }


    public List<CategoryTable> getAllCategoryTables() {
        return realm.where(CategoryTable.class).findAllSorted("num", Sort.ASCENDING);
    }

    public List<Categories> getAllCategories() {
        return realm.where(Categories.class).findAllSorted("name", Sort.ASCENDING);
    }

    public List<Ingredient> getCategory(int i) {
        return realm.where(Ingredient.class).equalTo("category", i).findAllSorted("ingredient", Sort.ASCENDING);
    }

    public List<Recipe> getRecipe(List<IngredientStop> forbidden, ArrayList<String> selected) {
        RealmQuery<Recipe> query = realm.where(Recipe.class);

        query.beginGroup().contains("ingredient", selected.get(0));
        for (int i = 1; i < selected.size(); i++) {
            query.or().contains("ingredient", selected.get(i));
        }
        query.endGroup();

        if (!forbidden.isEmpty()) {
            query.not().contains("ingredient", forbidden.get(0).getIngredient());
            for (int i = 1; i < forbidden.size(); i++) {
                query.not().contains("ingredient", forbidden.get(i).getIngredient());
            }
        }
        return query.findAll();
    }

    public Recipe getRecipeById(int id) {
        return realm.where(Recipe.class).equalTo("id", id).findFirst();
    }


    public List<Recipe> getRecipesSorted() {
        String[] fieldNames = {"category", "name"};
        Sort sort[] = {Sort.ASCENDING, Sort.ASCENDING};
        return realm.where(Recipe.class).findAllSorted(fieldNames, sort);
    }

    public List<Recipe> getRecipesByCategories(String name) {
        return realm.where(Recipe.class).contains("category", name).findAllSorted("name", Sort.ASCENDING);
    }

    public void close() {
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    public void copyIngredientToBuy(IngredientToBuy ingrToBuy) {
        realm.beginTransaction();
        realm.copyToRealm(ingrToBuy);
        realm.commitTransaction();
    }

    public void copyIngredientToCart(IngredientToBuy ingrToCart, int id) {
        if (getIngredientToBuyById(id) == null) {
            realm.beginTransaction();
            realm.copyToRealm(ingrToCart);
            realm.commitTransaction();
        }
    }

    public IngredientToBuy getIngredientToBuyById(int id) {
        return realm.where(IngredientToBuy.class).equalTo("recipeId", id).findFirst();
    }

    public void copyIngredientToBuyList(List<IngredientToBuy> ingrToBuy) {
        realm.beginTransaction();
        realm.copyToRealm(ingrToBuy);
        realm.commitTransaction();
    }

    public List<IngredientToBuy> getAllIngrToBuy() {
        return realm.where(IngredientToBuy.class).findAll();
    }

    public List<IngredientToBuy> copyIngrToBuyFromRealm() {
        realm.beginTransaction();
        RealmResults<IngredientToBuy> results = realm.where(IngredientToBuy.class).findAll();
        List<IngredientToBuy> newIngrsToBuy = realm.copyFromRealm(results);
        realm.commitTransaction();
        return newIngrsToBuy;
    }

    public void clearIngrToBuy() {
        realm.beginTransaction();
        RealmResults<IngredientToBuy> results = realm.where(IngredientToBuy.class).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public void clearAllIngredientsFromRecipeById(int recipeId) {
        realm.beginTransaction();
        RealmResults<IngredientsFromRecipe> results = realm.where(IngredientsFromRecipe.class).equalTo("recipeId", recipeId).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public void updateIngredientsFromRecipe(RealmList<IngredientsFromRecipe> ingredientsFromRecipes,
                                            IngredientsFromRecipe newIngredient, int position) {
        realm.beginTransaction();
        ingredientsFromRecipes.set(position, newIngredient);
        realm.commitTransaction();
    }

    public void updateIngredientFromRecipe(IngredientsFromRecipe ingredientsFromRecipe, int state) {
        realm.beginTransaction();
        ingredientsFromRecipe.setState(state);
        realm.commitTransaction();
    }

    public void updateIngredientToBuy(IngredientToBuy ingredientToBuy, int state) {
        realm.beginTransaction();
        ingredientToBuy.setCheckboxState(state);
        realm.commitTransaction();
    }
}
