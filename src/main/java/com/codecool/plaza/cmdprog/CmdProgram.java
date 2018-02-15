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
        System.out.println("There are no plaza created yet! \nType 'yes' to continue or 'no' to exit");
        String answer = sc.nextLine();
        if (answer.toLowerCase().equals("no")) {
            System.exit(0);
        } else if (answer.toLowerCase().equals("yes")) {
            handleCreatePlaza();
            System.out.println("Welcome to the" + " " + plaza.getName() + " " + "plaza! Press");
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

        while (true) {
            int choice = sc.nextInt();
            if (choice == 0) {
                handleListingCart();
                handleSumOfPrices();
                break;
            } else if (choice == 1) {
                try {
                    handleShopListing();
                } catch (PlazaIsClosedException e) {
                    System.out.println(e);
                }
            } else if (choice == 2) {
                try {
                    plaza.addShop(handleShopAdding());
                } catch (ShopAlreadyExistsException e) {
                    System.out.println("This shop already exists!");
                } catch (PlazaIsClosedException e) {
                    System.out.println("Plaza is closed!");
                }
            } else if (choice == 3) {
                handleShopRemoving();
            } else if (choice == 4) {
                try {
                    handleShopMenu();
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
    }

    public void handleShopListing() throws PlazaIsClosedException {
        for (int i = 0; i < plaza.getShops().size(); i++) {
            System.out.println(plaza.getShops().get(i).getName() + "  (Owner: " + plaza.getShops().get(i).getOwner() + ")");
        }
    }

    public Shop handleShopAdding() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Type the name of the new shop");
        String name = sc.nextLine();
        System.out.println("Who is the owner of this shop?");
        String owner = sc.nextLine();
        ShopImpl shop = new ShopImpl(name, owner);
        System.out.println("Shop has been added to the plaza!");
        return shop;
    }

    public void handleShopRemoving() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Type in the shop's name to remove!");
        String shopName = sc.nextLine();
        try {
            plaza.removeShop(plaza.findShopByName(shopName));
            System.out.println("Shop has been removed!");
        } catch (NoSuchShopException e) {
            e.printStackTrace();
        } catch (PlazaIsClosedException e) {
            e.printStackTrace();
        }
    }

    public void handleFindProductByName() throws ShopIsClosedException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Type in the product's you're looking for!");
        String name = sc.nextLine();
        Product product = shop.findByName(name);
        System.out.println("Product found, details: " + product);
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
            ClothingProduct product = new ClothingProduct(name,barcode,manufacturer,material,type);
            return product;
        }
        return null;
    }

    public void handleCreation() throws ParseException, ProductAlreadyExistsException, ShopIsClosedException {
        Scanner sc = new Scanner(System.in);
        Product product = getNewProduct();
        System.out.println("Type in the quantity to add!");
        int quantity = sc.nextInt();
        System.out.println("Type in the price of your product!");
        float price = sc.nextFloat();
        shop.addNewProduct(product,quantity,price);
        System.out.println("Product has been added to shop");
        System.out.println(product);
    }

    public void handleAddExistingProduct() throws NoSuchProductException, ShopIsClosedException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Type in the products barcode to add!");
        long barCode = sc.nextLong();
        System.out.println("Type in the quantity to add!");
        int quantity = sc.nextInt();
        shop.addProduct(barCode,quantity);
        System.out.println("Product has been added to shop!");
    }

    public void handleBuyProductByBarcode() throws NoSuchProductException, ShopIsClosedException, OutOfStockException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Type in the product's barcode to buy it!");
        long barCode = sc.nextLong();
        Product product = shop.buyProduct(barCode);
        cart.add(product);
        System.out.println("Product has been added to your cart!");
    }

    public void handleBuyProducts() throws NoSuchProductException, ShopIsClosedException, OutOfStockException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Type in the product's barcode to buy it!");
        long barCode = sc.nextLong();
        System.out.println("Type in the product's quantity to buy it/them!");
        int quantity = sc.nextInt();
        List<Product> products = shop.buyProducts(barCode,quantity);
        for (int i = 0; i < products.size(); i++) {
            cart.add(products.get(i));
        }
        System.out.println("Products have been added to your cart!");
    }

    public void handleSumOfPrices() {
        float sumOfPrices = 0;
        //List keys = new ArrayList(shop.getProducts().keySet());
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

    public void handleShopMenu() throws Exception {
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

        while (true) {
            int choice = sc.nextInt();
            if (choice == 0) {
                handleShopInteractListing();
            } else if (choice == 1) {
                System.out.println(shop.getProducts());
            } else if (choice == 2) {
                handleFindProductByName();
            } else if (choice == 3) {
                System.out.println(shop.getOwner());
            } else if (choice == 4) {
                shop.open();
                System.out.println("Shop is open now!");
            } else if (choice == 5) {
                shop.close();
                System.out.println("Shop has been closed!");
            } else if (choice == 6) {
                handleCreation();
            } else if (choice == 7) {
                handleAddExistingProduct();
            } else if (choice == 8) {
                handleBuyProductByBarcode();
            } else if (choice == 9) {
                handleBuyProducts();
            } else if (choice == 10) {
                handleListingCart();
                handleSumOfPrices();
            }
        }
    }
}
