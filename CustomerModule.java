import java.util.*;

class Product {
    int id;
    String name;
    double price;
    int quantity;

    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}

public class CustomerModule {
    static Scanner sc = new Scanner(System.in);

    static ArrayList<Product> products = new ArrayList<>(Arrays.asList(
        new Product(101, "Rice", 55.5, 10),
        new Product(102, "Sugar", 45.0, 20),
        new Product(103, "Milk", 30.0, 15)
    ));

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Customer Module ===");
            System.out.println("1. View Products");
            System.out.println("2. Search Product");
            System.out.println("3. View Product Details");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            
            int choice;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                sc.nextLine(); // clear buffer
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> viewProducts();
                case 2 -> searchProduct();
                case 3 -> productDetails();
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void viewProducts() {
        System.out.println("\n--- Available Products ---");
        for (Product p : products) {
            System.out.println(p.id + " | " + p.name + " | ₹" + p.price + " | Stock: " + p.quantity);
        }
    }

    static void searchProduct() {
        sc.nextLine(); // consume newline
        System.out.print("Enter product name keyword: ");
        String keyword = sc.nextLine().toLowerCase();
        boolean found = false;
        for (Product p : products) {
            if (p.name.toLowerCase().contains(keyword)) {
                System.out.println(p.id + " | " + p.name + " | ₹" + p.price + " | Stock: " + p.quantity);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matching product found!");
        }
    }

    static void productDetails() {
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();
        for (Product p : products) {
            if (p.id == id) {
                System.out.println("\n--- Product Details ---");
                System.out.println("ID: " + p.id);
                System.out.println("Name: " + p.name);
                System.out.println("Price: ₹" + p.price);
                System.out.println("Quantity in Stock: " + p.quantity);
                return;
            }
        }
        System.out.println("Product not found!");
    }
}
