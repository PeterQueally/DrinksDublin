package com.example.myapplication;

import java.io.Serializable;

public class Drinks implements Serializable {

    private boolean isChecked = false;
    public String name;


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
