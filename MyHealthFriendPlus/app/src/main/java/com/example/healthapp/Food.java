package com.example.healthapp;

import java.io.Serializable;

public class Food implements Serializable {
    private String name;
    private int calories;
    private int protein;
    private int fat;
    private int carbs;

    public Food(String name, int calories, int protein, int fat, int carbs) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public int getProtein() {
        return protein;
    }

    public int getFat() {
        return fat;
    }

    public int getCarbs() {
        return carbs;
    }
}
