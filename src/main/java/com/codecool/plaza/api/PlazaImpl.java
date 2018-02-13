package com.codecool.plaza.api;

import java.util.ArrayList;
import java.util.List;

public class PlazaImpl implements Plaza {
    private String name;
    private List<Shop> shops;
    private boolean isOpen;

    public PlazaImpl(String name) {
        this.name = name;
        this.shops = new ArrayList<Shop>();
        this.isOpen = false;
    }

    @Override
    public List<Shop> getShops() throws PlazaIsClosedException {
        return shops;
    }

    @Override
    public void addShop(Shop shop) throws ShopAlreadyExistsException, PlazaIsClosedException {
        shops.add(shop);
    }

    @Override
    public void removeShop(Shop shop) throws NoSuchShopException, PlazaIsClosedException {
        shops.remove(shop);
    }

    @Override
    public Shop findShopByName(String name) throws NoSuchShopException, PlazaIsClosedException {
        for (int i = 0; i < shops.size(); i++) {
            if (shops.get(i).getName().equals(name)) {
                return shops.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void open() {
        isOpen = true;
    }

    @Override
    public void close() {
        isOpen = false;
    }

    @Override
    public String toString() {
        return "PlazaImpl{" +
            "name='" + name + '\'' +
            ", shops=" + shops +
            '}';
    }
}

