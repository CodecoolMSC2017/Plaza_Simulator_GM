package com.codecool.plaza.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FoodProduct extends Product {
    private int calories;
    private Date bestBefore;

    public FoodProduct(String name, long barcode, String manufacturer, int calories, Date bestBefore) {
        super(name,barcode,manufacturer);
        this.calories = calories;
        this.bestBefore = bestBefore;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isStillConsumable() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date actualDate = new Date();
        if (actualDate.after(bestBefore)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FoodProduct{" +
            "calories=" + calories +
            ", bestBefore=" + bestBefore +
            ", name='" + name + '\'' +
            ", barcode=" + barcode +
            ", manufacturer='" + manufacturer + '\'' +
            '}';
    }
}
