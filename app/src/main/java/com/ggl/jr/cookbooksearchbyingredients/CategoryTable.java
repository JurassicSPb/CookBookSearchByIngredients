package com.ggl.jr.cookbooksearchbyingredients;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Мария on 21.11.2016.
 */

public class CategoryTable extends RealmObject{
    @PrimaryKey
    private int num;
    private String name;

    public CategoryTable() {}

    public CategoryTable(int num, String name) {
        this.num = num;
        this.name = name;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }
}
