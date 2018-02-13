package com.codecool.plaza.api;

public abstract class Product {
    protected String name;
    protected long barcode;
    protected String manufacturer;

    protected Product(String name, long barcode, String manufacturer) {
        this.name = name;
        this.barcode = barcode;
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public long getBarcode() {
        return barcode;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public String toString() {
        return "Product{" +
            "name='" + name + '\'' +
            ", barcode=" + barcode +
            ", manufacturer='" + manufacturer + '\'' +
            '}';
    }
}
