package com.codecool.plaza.api;

import java.util.List;

public interface Shop {

    public String getName();

    public String getOwner();

    public boolean isOpen();

    public void open();

    public void close();

    public Product findByName(String name) throws ShopIsClosedException;

    public boolean hasProduct(long barcode) throws ShopIsClosedException;

    public void addNewProduct(Product product, int quantity, float price)
        throws ProductAlreadyExistsException, ShopIsClosedException;

    public void addProduct(long barcode, int quantity) throws NoSuchProductException, ShopIsClosedException;

    public Product buyProduct(long barcode) throws NoSuchProductException, OutOfStockException, ShopIsClosedException;

    public List<Product> buyProducts(long barcode, int quantity) throws NoSuchProductException, OutOfStockException, ShopIsClosedException;

    public String toString();
}
