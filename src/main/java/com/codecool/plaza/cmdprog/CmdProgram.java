package com.codecool.plaza.cmdprog;

import com.codecool.plaza.api.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CmdProgram {
    private List<Product> cart;
    private String[] args;
    PlazaImpl plaza;
    ShopImpl shop;


    public CmdProgram(String[] args) {
        this.args = args;
        cart = new ArrayList<>();
    }

    public void run() throws Exception {
        handleStart();
        handleShopInteractListing();
    }

    public Plaza handleCreatePlaza() {
        Scanner sc = new Scanner(System.in);
        System.out.println("First of all create your plaza with a given name!");
        String name = sc.nextLine();
        plaza = new PlazaImpl(name);
        return plaza;
    }

    public void handleStart() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("There are no plaza created yet! \nType 'yes' to continue or 'no' to exit");
            String answer = sc.nextLine();
            if (answer.toLowerCase().equals("no")) {
                System.exit(0);
            } else if (answer.toLowerCase().equals("yes")) {
                handleCreatePlaza();
                System.out.println("Welcome to the" + " " + plaza.getName() + " " + "plaza! Press");
                break;
            } else {
                System.out.println("Wrong command, try yes or no!");
            }
        }
    }

    public void handleShopInteractListing() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("1) to list all shops.\n" +
            "2) to add a new shop.\n" +
            "3) to remove an existing shop.\n" +
            "4) enter a shop by name.\n" +
            "5) to open the plaza.\n" +
            "6) to close the plaza.\n" +
            "7) to check if the plaza is open or not.\n" +
            "0) leave plaza. ");

        try {
            while (true) {
                int choice = sc.nextInt();
                if (choice == 0) {
                    handleListingCart();
                    handleSumOfPrices();
                    System.exit(0);
                } else if (choice == 1) {
                    try {
                        handleShopListing();
                    } catch (PlazaIsClosedException e) {
                        System.out.println("Plaza is closed!");
                    }
                } else if (choice == 2) {
                    try {
                        Shop shop = handleShopAdding();
                        plaza.addShop(shop);
                    } catch (PlazaIsClosedException ex) {
                        System.out.println("Plaza is closed!");
                    }
                } else if (choice == 3) {
                    try {
                        handleShopRemoving();
                    } catch (NoSuchShopException ex) {
                        System.out.println("No such product!");
                    } catch (PlazaIsClosedException ex) {
                        System.out.println("Plaza is closed!");
                    }
                } else if (choice == 4) {
                    try {
                        handleShopMenu();
                    } catch (NullPointerException e) {
                        System.out.println("No shop was added to plaza yet!");
                    } catch (PlazaIsClosedException e) {
                        System.out.println("Plaza is closed!");
                    } catch (NoSuchShopException e) {
                        System.out.println("No such shop!");
                    }
                } else if (choice == 5) {
                    plaza.open();
                    System.out.println("Plaza is open now!");
                } else if (choice == 6) {
                    plaza.close();
                    System.out.println("Plaza has been closed!");
                } else if (choice == 7) {
                    System.out.println(plaza.isOpen());
                }
            }
        } catch (InputMismatchException ex) {
            System.out.println("Invalid input, try numbers 0-7");
            handleShopInteractListing();
        }
    }


    public void handleShopListing() throws PlazaIsClosedException {
        if (plaza.getShops().size() == 0) {
            System.out.println("There's no any shop in the plaza!");
        }
        for (int i = 0; i < plaza.getShops().size(); i++) {
            System.out.println(plaza.getShops().get(i).getName() + "  (Owner: " + plaza.getShops().get(i).getOwner() + ")");
        }
    }

    public Shop handleShopAdding() throws PlazaIsClosedException {
        if (plaza.isOpen() == false) {
            throw new PlazaIsClosedException("Plaza is closed!");
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Type the name of the new shop");
        String name = sc.nextLine();
        System.out.println("Who is the owner of this shop?");
        String owner = sc.nextLine();
        ShopImpl shop = new ShopImpl(name, owner);
        System.out.println("Shop has been added to the plaza!");
        return shop;
    }

    public void handleShopRemoving() throws PlazaIsClosedException, NoSuchShopException {
        if (plaza.isOpen() == false) {
            throw new PlazaIsClosedException("Plaza is closed!");
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Type in the shop's name to remove!");
        String shopName = sc.nextLine();
        plaza.removeShop(plaza.findShopByName(shopName));
        System.out.println("Shop has been removed!");
    }

    public void handleFindProductByName() throws ShopIsClosedException {
        if (shop.isOpen() == false) {
            throw new ShopIsClosedException("Shop is closed!");
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Type in the product's you're looking for!");
        String name = sc.nextLine();
        Product product = shop.findByName(name);
        if (product == null) {
            System.out.println("There is no such product");
        } else if (product != null) {
            System.out.println("Product found, details: " + product);
        }
    }

    public Product getNewProduct() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("What of the type of your product? (Food / Clothing)");
        String productType = sc.nextLine();
        if (productType.toLowerCase().equals("food")) {
            System.out.println("What's the name of the product?");
            String name = sc.nextLine();
            System.out.println("What's the barcode of your product?");
            long barcode = sc.nextLong();
            System.out.println("What's the manufacturer of the product?");
            sc.nextLine();
            String manufacturer = sc.nextLine();
            System.out.println("How much calories does it contain?");
            int calories = sc.nextInt();
            System.out.println("What's the best before date of your product? (yyyy/MM/dd)");
            sc.nextLine();
            String dateAsString = sc.nextLine();
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
            Date date = format.parse(dateAsString);
            FoodProduct food = new FoodProduct(name, barcode, manufacturer, calories, date);
            return food;
        } else if (productType.toLowerCase().equals("clothing")) {
            System.out.println("What's the name of the product?");
            String name = sc.nextLine();
            System.out.println("What's the barcode of your product?");
            long barcode = sc.nextLong();
            System.out.println("What's the manufacturer of the product?");
            sc.nextLine();
            String manufacturer = sc.nextLine();
            System.out.println("What's the material of your product?");
            String material = sc.nextLine();
            System.out.println("What's the type of your product?");
            String type = sc.nextLine();
            ClothingProduct product = new ClothingProduct(name, barcode, manufacturer, material, type);
            return product;
        } else {
            System.out.println("Invalid input, try food or clothing!");
            return null;
        }
    }

    public void handleCreation() throws ParseException, ProductAlreadyExistsException, ShopIsClosedException {
        if (shop.isOpen() == false) {
            throw new ShopIsClosedException("Shop is closed");
        }
        Scanner sc = new Scanner(System.in);
        Product product = getNewProduct();
        System.out.println("Type in the quantity to add!");
        int quantity = sc.nextInt();
        System.out.println("Type in the price of your product!");
        float price = sc.nextFloat();
        shop.addNewProduct(product, quantity, price);
        System.out.println("Product has been added to shop");
        System.out.println(product);
    }

    public void handleAddExistingProduct() throws NoSuchProductException, ShopIsClosedException {
        if (shop.isOpen() == false) {
            throw new ShopIsClosedException("Shop is closed!");
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Type in the products barcode to add!");
        long barCode = sc.nextLong();
        System.out.println("Type in the quantity to add!");
        int quantity = sc.nextInt();
        shop.addProduct(barCode, quantity);
        System.out.println("Product has been added to shop!");
    }

    public void handleGetOwner() throws ShopIsClosedException {
        if (shop.isOpen() == false) {
            throw new ShopIsClosedException("Shop is closed");
        }
        System.out.println(shop.getOwner());
    }

    public void handleBuyProductByBarcode() throws NoSuchProductException, ShopIsClosedException, OutOfStockException {
        if (shop.isOpen() == false) {
            throw new ShopIsClosedException("Shop is closed!");
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Type in the product's barcode to buy it!");
        long barCode = sc.nextLong();
        Product product = shop.buyProduct(barCode);
        cart.add(product);
        System.out.println("Product has been added to your cart!");
    }

    public void handleBuyProducts() throws NoSuchProductException, ShopIsClosedException, OutOfStockException {
        if (shop.isOpen() == false) {
            throw new ShopIsClosedException("Shop is closed!");
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Type in the product's barcode to buy it!");
        long barCode = sc.nextLong();
        System.out.println("Type in the product's quantity to buy it/them!");
        int quantity = sc.nextInt();
        List<Product> products = shop.buyProducts(barCode, quantity);
        for (int i = 0; i < products.size(); i++) {
            cart.add(products.get(i));
        }
        System.out.println("Products have been added to your cart!");
    }

    public void handleSumOfPrices() {
        float sumOfPrices = 0;
        for (int i = 0; i < cart.size(); i++) {
            for (long key : shop.getProducts().keySet()) {
                if (cart.get(i).getName() == shop.getProducts().get(key).getProduct().getName()) {
                    sumOfPrices += shop.getProducts().get(key).getPrice();
                }
            }
        }
        System.out.println("Total sum of prices: " + sumOfPrices);
    }

    public void handleListingCart() {
        System.out.println("Your bought products are: ");
        for (int i = 0; i < cart.size(); i++) {
            System.out.println(cart.get(i).getName());
        }
    }

    public void handleListProducts() throws ShopIsClosedException {
        if (shop.isOpen() == false) {
            throw new ShopIsClosedException("Shop is closed!");
        }
        if (shop.getProducts().size() == 0) {
            System.out.println("There's no added product yet!");
        }
        System.out.println(shop.getProducts());
    }

    public void handleShopMenu() throws Exception {
        if (plaza.isOpen() == false) {
            throw new PlazaIsClosedException("Plaza is closed!");
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Type in the name of the shop to enter!");
        String shopName = sc.nextLine();
        try {
            plaza.findShopByName(shopName);
            shop = (ShopImpl) plaza.findShopByName(shopName);
        } catch (NoSuchShopException e) {
            e.printStackTrace();
        } catch (PlazaIsClosedException e) {
            e.printStackTrace();
        }
        System.out.println("Welcome in the " + plaza.findShopByName(shopName).getName() + " shop!\n Make your choice below");
        System.out.println("1) to list available products.\n" +
            "2) to find products by name.\n" +
            "3) to display the shop's owner.\n" +
            "4) to open the shop.\n" +
            "5) to close the shop.\n" +
            "6) to add new product to the shop.\n" +
            "7) to add existing products to the shop.\n" +
            "8) to buy a product by barcode.\n" +
            "9) to buy products by barcode.\n" +
            "10) to list content of cart.\n" +
            "0) back to plaza. ");

        try {
            while (true) {
                int choice = sc.nextInt();
                if (choice == 0) {
                    handleShopInteractListing();
                } else if (choice == 1) {
                    try{
                        handleListProducts();
                    } catch (ShopIsClosedException ex) {
                        System.out.println("Shop is closed!");
                    }
                } else if (choice == 2) {
                    try {
                    handleFindProductByName();
                    } catch (ShopIsClosedException ex) {
                        System.out.println("Shop is closed!");
                    }
                } else if (choice == 3) {
                    try {
                        handleGetOwner();
                    } catch (ShopIsClosedException ex) {
                        System.out.println("Shop is closed!");
                    }
                } else if (choice == 4) {
                    shop.open();
                    System.out.println("Shop is open now!");
                } else if (choice == 5) {
                    shop.close();
                    System.out.println("Shop has been closed!");
                } else if (choice == 6) {
                    try {
                    handleCreation();
                    } catch (ShopIsClosedException ex) {
                        System.out.println("Shop is closed!");
                    }
                } else if (choice == 7) {
                    try {
                        handleAddExistingProduct();
                    } catch (NoSuchProductException ex) {
                        System.out.println("There's no such product");
                    } catch (ShopIsClosedException ex) {
                        System.out.println("Shop is closed!");
                    }
                } else if (choice == 8) {
                    try {
                    handleBuyProductByBarcode();
                    } catch (NoSuchProductException ex) {
                        System.out.println("No such product!");
                    } catch (OutOfStockException ex) {
                        System.out.println("Product is out of stock!");
                    } catch (ShopIsClosedException ex) {
                        System.out.println("Shop is closed!");
                    }
                } else if (choice == 9) {
                    try {
                    handleBuyProducts();
                    } catch (NoSuchProductException ex) {
                        System.out.println("No such product!");
                    } catch (OutOfStockException ex) {
                        System.out.println("Product is out of stock!");
                    } catch (ShopIsClosedException ex) {
                        System.out.println("Shop is closed!");
                    }
                } else if (choice == 10) {
                    handleListingCart();
                    handleSumOfPrices();
                }
            }
        } catch (InputMismatchException ex) {
            System.out.println("Not valid input, try number 0-10");
        }
    }
}
