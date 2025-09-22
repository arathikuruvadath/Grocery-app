import java.util.*;


class Product {
    int id;
    String name;
    double price;
    String unit;
    int quantity;

    Product(int id, String name, double price, String unit, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.quantity = quantity;
    }
}
class Shop {
    String shopName;
    String location;
    ArrayList<Product> products = new ArrayList<>();

    Shop(String shopName, String location) {
        this.shopName = shopName;
        this.location = location;
    }
}

public class LocationModule {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Shop> shops = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Location Module ===");
            System.out.println("1. Admin Mode");
            System.out.println("2. Customer Mode");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> adminMode();
                case 2 -> customerMode();
                case 0 -> { System.out.println("Exiting..."); return; }
                default -> System.out.println("❌ Invalid choice!");
            }
        }
    }


    static void adminMode() {
        while (true) {
            System.out.println("\n=== Admin Mode ===");
            System.out.println("1. Add Shop & Location");
            System.out.println("2. Add Product to Shop");
            System.out.println("3. View Shops");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addShop();
                case 2 -> addProductToShop();
                case 3 -> viewShops();
                case 0 -> { return; }
                default -> System.out.println("❌ Invalid choice!");
            }
        }
    }

    static void addShop() {
        sc.nextLine();
        System.out.print("Enter Shop Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Location: ");
        String loc = sc.nextLine();

        shops.add(new Shop(name, loc));
        System.out.println("✅ Shop added successfully!");
    }

    static void addProductToShop() {
        if (shops.isEmpty()) {
            System.out.println("⚠ No shops available. Add a shop first.");
            return;
        }
        viewShops();
        System.out.print("Select shop number: ");
        int choice = sc.nextInt();
        if (choice < 1 || choice > shops.size()) {
            System.out.println("❌ Invalid shop selection!");
            return;
        }
        Shop shop = shops.get(choice - 1);

        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Price: ");
        double price = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter Unit (Kg, L, Pack, etc.): ");
        String unit = sc.nextLine();
        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();

        shop.products.add(new Product(id, name, price, unit, qty));
        System.out.println("✅ Product added to " + shop.shopName);
    }

    static void viewShops() {
        System.out.println("\n--- Shops & Locations ---");
        int i = 1;
        for (Shop s : shops) {
            System.out.println(i++ + ". " + s.shopName + " (" + s.location + ")");
        }
    }

    
    static void customerMode() {
        if (shops.isEmpty()) {
            System.out.println("⚠ No shops available. Please wait for admin to add.");
            return;
        }
        sc.nextLine();
        System.out.print("Enter location to search: ");
        String loc = sc.nextLine().toLowerCase();

        ArrayList<Shop> matched = new ArrayList<>();
        for (Shop s : shops) {
            if (s.location.toLowerCase().contains(loc)) {
                matched.add(s);
            }
        }

        if (matched.isEmpty()) {
            System.out.println("❌ No shops found in this location!");
            return;
        }

        System.out.println("\n--- Shops in " + loc + " ---");
        for (int i = 0; i < matched.size(); i++) {
            System.out.println((i + 1) + ". " + matched.get(i).shopName + " (" + matched.get(i).location + ")");
        }
        System.out.print("Select shop number: ");
        int choice = sc.nextInt();
        if (choice < 1 || choice > matched.size()) {
            System.out.println("❌ Invalid choice!");
            return;
        }

        Shop selectedShop = matched.get(choice - 1);
        showCustomerShopMenu(selectedShop);
    }

    static void showCustomerShopMenu(Shop shop) {
        while (true) {
            System.out.println("\n=== Customer Mode (" + shop.shopName + ") ===");
            System.out.println("1. View Products");
            System.out.println("2. Search Product");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> viewProducts(shop);
                case 2 -> searchProduct(shop);
                case 0 -> { return; }
                default -> System.out.println("❌ Invalid choice!");
            }
        }
    }

    static void viewProducts(Shop shop) {
        System.out.println("\n--- Products in " + shop.shopName + " ---");
        if (shop.products.isEmpty()) {
            System.out.println("⚠ No products available in this shop.");
            return;
        }
        for (Product p : shop.products) {
            System.out.println(p.id + " | " + p.name + " | ₹" + p.price + " | Stock: " + p.quantity + " " + p.unit);
        }
    }

    static void searchProduct(Shop shop) {
        sc.nextLine();
        System.out.print("Enter product name keyword: ");
        String keyword = sc.nextLine().toLowerCase();
        boolean found = false;
        for (Product p : shop.products) {
            if (p.name.toLowerCase().contains(keyword)) {
                System.out.println(p.id + " | " + p.name + " | ₹" + p.price + " | Stock: " + p.quantity + " " + p.unit);
                found = true;
            }
        }
        if (!found) System.out.println(" No matching product found!");
    }
}
