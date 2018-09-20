package com.ggl.jr.cookbooksearchbyingredients.user_recipes

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class UserRecipe(
        @PrimaryKey var id: Long = 0,
        @Required var name: String = "",
        @Required var ingredients: String = "",
        @Required var description: String = "",
        @Required var image: String = ""
) : RealmObject()