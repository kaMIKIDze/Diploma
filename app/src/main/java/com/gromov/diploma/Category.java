package com.gromov.diploma;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Category {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int required;


    public int getRequired() {
        return required;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
