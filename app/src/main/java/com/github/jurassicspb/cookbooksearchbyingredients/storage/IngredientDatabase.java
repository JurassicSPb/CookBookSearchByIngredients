package com.github.jurassicspb.cookbooksearchbyingredients.storage;

import com.github.jurassicspb.cookbooksearchbyingredients.Ingredient;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
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
    public void delete (List <Ingredient> ingredient) {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public List<Ingredient> getAll() {
        return realm.where(Ingredient.class).findAllSorted("id", Sort.ASCENDING);
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
