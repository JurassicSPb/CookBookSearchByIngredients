package com.github.jurassicspb.cookbooksearchbyingredients;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Мария on 04.11.2016.
 */

public class Ingredient extends RealmObject implements Parcelable {
    @PrimaryKey
    private String ingredient;
    private int category;
    private int image;
    private int state;

    public Ingredient() {
    }

    public Ingredient(int category, String ingredient, int image, int state) {
        this.category = category;
        this.ingredient = ingredient;
        this.image = image;
        this.state = state;
    }

    public int getCategory() {
        return category;
    }

    public String getIngredient() {
        return ingredient;
    }

    public int getImage() {
        return image;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setImage(int category) {
        this.image = image;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    private Ingredient(Parcel in) {
        ingredient = in.readString();
        category = in.readInt();
        image = in.readInt();
        state = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(ingredient);
        out.writeInt(category);
        out.writeInt(image);
        out.writeInt(state);
    }
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}

