package com.ggl.jr.cookbooksearchbyingredients;

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
    private int checkboxState;

    public Ingredient() {
    }

    public Ingredient(int category, String ingredient, int image, int state, int checkboxState) {
        this.category = category;
        this.ingredient = ingredient;
        this.image = image;
        this.state = state;
        this.checkboxState = checkboxState;
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

    public int getCheckboxState() {
        return checkboxState;
    }

    public void setCheckboxState(int checkboxState) {
        this.checkboxState = checkboxState;
    }

    private Ingredient(Parcel in) {
        ingredient = in.readString();
        category = in.readInt();
        image = in.readInt();
        state = in.readInt();
        checkboxState = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(ingredient);
        out.writeInt(category);
        out.writeInt(image);
        out.writeInt(state);
        out.writeInt(checkboxState);
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

