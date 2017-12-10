package com.ggl.jr.cookbooksearchbyingredients.storage;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by yuri on 11/29/17.
 */

public class Migration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            schema.create("IngredientStop")
                    .addField("ingredient", String.class, FieldAttribute.PRIMARY_KEY)
                    .addField("image", Integer.class, FieldAttribute.REQUIRED)
                    .addField("checkboxState", Integer.class, FieldAttribute.REQUIRED);

            schema.get("Ingredient")
                    .addField("stopState", Integer.class, FieldAttribute.REQUIRED);

            oldVersion++;
        }

        if (oldVersion == 1) {
            RealmObjectSchema ingredientsFromRecipeSchema = schema.create("IngredientsFromRecipe")
                    .addField("recipeId", Integer.class, FieldAttribute.REQUIRED)
                    .addField("name", String.class, FieldAttribute.REQUIRED)
                    .addField("state", Integer.class, FieldAttribute.REQUIRED);

            schema.get("IngredientToBuy")
                    .addField("recipeId", Integer.class, FieldAttribute.REQUIRED)
                    .addRealmListField("ingredients", ingredientsFromRecipeSchema);

            oldVersion++;
        }
    }

    @Override
    public int hashCode() {
        return 37;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Migration);
    }
}
