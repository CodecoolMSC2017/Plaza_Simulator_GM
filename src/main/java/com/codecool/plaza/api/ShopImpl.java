package com.codecool.plaza.api;

import java.util.*;

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
        if (this.isOpen() == false) {
            throw new ShopIsClosedException("Shop is closed, sorry!");
        }
        List<ShopEntry> shopEntries = (List<ShopEntry>) products.values();
        for (int i = 0; i < shopEntries.size(); i++) {
            if (shopEntries.get(i).getProduct().getName().equals(name)) {
                return shopEntries.get(i).getProduct();
            }
        }
        return null;
    }

    @Override
    public boolean hasProduct(long barcode) throws ShopIsClosedException {
        if (this.isOpen() == false) {
            throw new ShopIsClosedException("Shop is closed, sorry!");
        }
        for (int i = 0; i < products.keySet().size(); i++) {
            if (products.keySet().contains(barcode)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addNewProduct(Product product, int quantity, float price) throws ProductAlreadyExistsException, ShopIsClosedException {
        if (this.isOpen() == false) {
            throw new ShopIsClosedException("Shop is closed, sorry!");
        }
        Random rand = new Random();
        while (true) {
            int barcode = rand.nextInt(99999) + 10000;
            if (hasProduct(barcode)) {
                throw new ProductAlreadyExistsException("This product already exists!");
            }
            ShopEntry newShopEntry = new ShopEntry(product, quantity, price);
            products.put((long) barcode, newShopEntry);
            break;
        }
    }

    @Override
    public void addProduct(long barcode, int quantity) throws NoSuchProductException, ShopIsClosedException {
        if (this.isOpen() == false) {
            throw new ShopIsClosedException("Shop is closed, sorry!");
        }
        if (!hasProduct(barcode)) {
            throw new NoSuchProductException("There's no such product!");
        }
        for (int i = 0; i < products.keySet().size(); i++)
            if (products.get(barcode).getProduct().getBarcode() == barcode) {
                products.get(barcode).increaseQuantity(quantity);
            }
    }

    @Override
    public Product buyProduct(long barcode) throws NoSuchProductException, OutOfStockException, ShopIsClosedException {
        if (this.isOpen() == false) {
            throw new ShopIsClosedException("Shop is closed, sorry!");
        }
        if (!hasProduct(barcode)) {
            throw new NoSuchProductException("There's no such product");
        }
        if (products.get(barcode).getQuantity() == 0) {
            throw new OutOfStockException("Product is out of stock sorry!");
        }
        products.get(barcode).decreaseQuantity(1);
        return products.get(barcode).getProduct();
    }

    @Override
    public List<Product> buyProducts(long barcode, int quantity) throws NoSuchProductException, OutOfStockException, ShopIsClosedException {
        if (this.isOpen() == false) {
            throw new ShopIsClosedException("Shop is closed, sorry!");
        }
        if (!hasProduct(barcode)) {
            throw new NoSuchProductException("There's no such product");
        }
        if (products.get(barcode).getQuantity() == 0 || products.get(barcode).getQuantity() < quantity) {
            throw new OutOfStockException("Product is out of stock sorry!");
        }
        List<Product> boughtProducts = new ArrayList<>();
        products.get(barcode).decreaseQuantity(quantity);
        for (int i = 0; i < quantity; i++) {
            boughtProducts.add(products.get(barcode).getProduct());
        }
        return boughtProducts;
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
