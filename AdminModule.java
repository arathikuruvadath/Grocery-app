import java.util.*;

class Product {
    int id;
    String name;
    double price;
    int quantity;

    Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | ₹" + price + " | Qty: " + quantity;
    }
}

public class AdminModule {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Product> products = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Admin Module ===");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> viewProducts();
                case 3 -> updateProduct();
                case 4 -> deleteProduct();
                case 0 -> { System.out.println("Exiting..."); return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void addProduct() {
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Price: ");
        double price = sc.nextDouble();
        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();
        products.add(new Product(id, name, price, qty));
        System.out.println("✅ Product added successfully!");
    }

    static void viewProducts() {
        System.out.println("\n--- Product List ---");
        if (products.isEmpty()) {
            System.out.println("No products available!");
            return;
        }
        for (Product p : products) {
            System.out.println(p);
        }
    }

    static void updateProduct() {
        System.out.print("Enter Product ID to Update: ");
        int id = sc.nextInt();
        for (Product p : products) {
            if (p.id == id) {
                sc.nextLine();
                System.out.print("Enter new name: ");
                p.name = sc.nextLine();
                System.out.print("Enter new price: ");
                p.price = sc.nextDouble();
                System.out.print("Enter new quantity: ");
                p.quantity = sc.nextInt();
                System.out.println(" Product updated!");
                return;
            }
        }
        System.out.println(" Product not found!");
    }

    static void deleteProduct() {
        System.out.print("Enter Product ID to Delete: ");
        int id = sc.nextInt();
        boolean removed = products.removeIf(p -> p.id == id);
        if (removed) System.out.println("Product deleted!");
        else System.out.println(" Product not found!");
    }
}
