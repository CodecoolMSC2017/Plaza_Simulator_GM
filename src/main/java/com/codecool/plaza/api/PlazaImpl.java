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

    public String getName() {
        return name;
    }

    @Override
    public List<Shop> getShops() throws PlazaIsClosedException {
        if(this.isOpen() == true) {
            return shops;
        }
        throw new PlazaIsClosedException("Plaza is closed, sorry!");
    }

    @Override
    public void addShop(Shop shop) throws ShopAlreadyExistsException, PlazaIsClosedException {
        if(this.isOpen() == false) {
            throw new PlazaIsClosedException("Plaza is closed, sorry!");
        }
        if(shops.contains(shop)) {
            throw new ShopAlreadyExistsException("This shop already exists!");
        }
        shops.add(shop);
    }

    @Override
    public void removeShop(Shop shop) throws NoSuchShopException, PlazaIsClosedException {
        if(this.isOpen() == false) {
            throw new PlazaIsClosedException("Plaza is closed, sorry!");
        }
        if(!shops.contains(shop)) {
            throw new NoSuchShopException("There's no such shop!");
        }
        shops.remove(shop);
    }

    @Override
    public Shop findShopByName(String name) throws NoSuchShopException, PlazaIsClosedException {
        if(this.isOpen() == false) {
            throw new PlazaIsClosedException("Plaza is closed, sorry!");
        }
        for (int i = 0; i < shops.size(); i++) {
            if (shops.get(i).getName().equals(name)) {
                return shops.get(i);
            }
        }
        throw new NoSuchShopException("There's no such shop");
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

