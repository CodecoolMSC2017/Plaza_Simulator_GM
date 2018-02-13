package com.codecool.plaza.api;

import java.util.HashMap;

public class ShopImpl implements Shop {
    private String name;
    private String owner;
    private boolean isOpen;
    private HashMap<Long, ShopImpl.ShopEntry> products;

    public ShopImpl(String name, String owner) {
        this.name = name;
        this.owner = owner;
        isOpen = false;
        products = new HashMap<Long, ShopImpl.ShopEntry>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOwner() {
        return owner;
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
    public Product findByName(String name) throws ShopIsClosedException {

    }

    @Override
    public boolean hasProduct(long barcode) throws ShopIsClosedException {
        return false;
    }

    @Override
    public void addNewProduct(Product product, int quantity, float price) throws ProductAlreadyExistsException, ShopIsClosedException {

    }

    @Override
    public void addProduct(long barcode, int quantity) throws NoSuchProductException, ShopIsClosedException {

    }

    @Override
    public Product buyProduct(long barcode) throws NoSuchProductException, OutOfStockException, ShopIsClosedException {
        return null;
    }

    @Override
    public List<Product> buyProducts(long barcode, int quantity) throws NoSuchProductException, OutOfStockException, ShopIsClosedException {
        return null;
    }

    class ShopEntry {
        private Product product;
        private int quantity;
        private float price;

        ShopEntry(Product product, int quantity, float price) {
            this.product = product;
            this.quantity = quantity;
            this.price = price;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void increaseQuantity(int amount) {
            this.quantity += amount;
        }

        public void decreaseQuantity(int amount) {
            this.quantity -= amount;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "ShopEntry{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
        }
    }
}
