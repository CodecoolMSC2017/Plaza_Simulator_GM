package com.codecool.plaza.cmdprog;

import com.codecool.plaza.api.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class CmdProgram {
    private List<Product> cart;
    private String[] args;
    PlazaImpl plaza;
    ShopImpl shop;
    Scanner sc = new Scanner(System.in);

    public CmdProgram(String[] args) {
        this.args = args;
    }

    public void run() throws ParseException, ProductAlreadyExistsException, ShopIsClosedException {
        handleStart();
        handleShopInteractListing();
    }

    public Plaza handleCreatePlaza() {
        System.out.println("First of all create your plaza with a given name!");
        String name = sc.nextLine();
        plaza = new PlazaImpl(name);
        return plaza;
    }

    public void handleStart() {
        System.out.println("There are no plaza created yet! \nType 'yes' to continue or 'no' to exit");
        String answer = sc.nextLine();
        if (answer.toLowerCase().equals("no")) {
            System.exit(0);
        } else if (answer.toLowerCase().equals("yes")) {
            handleCreatePlaza();
            System.out.println("Welcome to the" + " " + plaza.getName() + " " + "plaza! Press");
        }
    }

    public void handleShopInteractListing() throws ParseException, ProductAlreadyExistsException, ShopIsClosedException {
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
        System.out.println("Type the name of the new shop");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.println("Who is the owner of this shop?");
        String owner = sc.nextLine();
        ShopImpl shop = new ShopImpl(name, owner);
        System.out.println("Shop has been added to the plaza!");
        return shop;
    }

    public void handleShopRemoving() {
        System.out.println("Type in the shop's name to remove!");
        sc.nextLine();
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
        System.out.println("Type in the product's you're looking for!");
        String name = sc.nextLine();
        Product product = shop.findByName(name);
        System.out.println("Product found, details: " + product);
    }

    public Product getNewProduct() throws ParseException {
        System.out.println("What of the type of your product? (Food / Clothing)");
        sc.nextLine();
        String productType = sc.nextLine();
        if (productType.equals("Food")) {
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
            String dateAsString = sc.nextLine();
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
            Date date = format.parse(dateAsString);
            FoodProduct food = new FoodProduct(name, barcode, manufacturer, calories, date);
            return food;
        } else if (productType.equals("clothing")) {
            System.out.println("What's the name of the product?");
            sc.nextLine();
            String name = sc.nextLine();
            System.out.println("What's the barcode of your product?");
            long barcode = sc.nextLong();
            System.out.println("What's the manufacturer of the product?");
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
        Product product = getNewProduct();
        sc.nextLine();
        System.out.println("Type in the quantity to add!");
        int quantity = sc.nextInt();
        System.out.println("Type in the price of your product!");
        float price = sc.nextFloat();
        shop.addNewProduct(product,quantity,price);
        System.out.println("Product has been added to shop");
        System.out.println(product);
    }

    public void handleShopMenu() throws PlazaIsClosedException, NoSuchShopException, ShopIsClosedException, ParseException, ProductAlreadyExistsException {
        System.out.println("Type in the name of the shop to enter!");
        sc.nextLine();
        String shopName = sc.nextLine();
        try {
            plaza.findShopByName(shopName);
            shop = (ShopImpl) plaza.findShopByName(shopName);
        } catch (NoSuchShopException e) {
            e.printStackTrace();
        } catch (PlazaIsClosedException e) {
            e.printStackTrace();
        }
        System.out.println("Welcome in the" + plaza.findShopByName(shopName).getName() + "shop!\n Make your choice below");
        System.out.println("1) to list available products.\n" +
            "2) to find products by name.\n" +
            "3) to display the shop's owner.\n" +
            "4) to open the shop.\n" +
            "5) to close the shop.\n" +
            "6) to add new product to the shop.\n" +
            "7) to add existing products to the shop.\n" +
            "8) to buy a product by barcode.\n" +
            "9) to check if the plaza is open or not.\n" +
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
            } else if (choice == 5) {
                shop.close();
            } else if (choice == 6) {
                handleCreation();

            }
        }
    }
}
