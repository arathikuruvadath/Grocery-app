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

    public String toString() {
        return id + " | " + name + " | $" + price + " | Qty: " + quantity;
    }
}

public class cmexample {
    public static void main(String[] args) {
        ArrayList<Product> products = new ArrayList<>();

        // --- Preloaded products (from shop) ---
        products.add(new Product(1, "Apple", 0.5, 100));
        products.add(new Product(2, "Banana", 0.3, 200));
        products.add(new Product(3, "Orange", 0.8, 150));

        // Display products
        System.out.println("Available Products:");
        for (Product p : products) {
            System.out.println(p);
        }

        // Simulate search by name
        String searchKey = "Apple";
        System.out.println("\n Searching for product: " + searchKey);
        boolean found = false;
        for (Product p : products) {
            if (p.name.equalsIgnoreCase(searchKey)) {
                System.out.println("Found: " + p);
                found = true;
            }
        }
        if (!found) {
            System.out.println(" No product found with name " + searchKey);
        }

        // View product details
        int viewId = 3;
        System.out.println("\n Viewing details of Product ID: " + viewId);
        for (Product p : products) {
            if (p.id == viewId) {
                System.out.println("Product Details → " + p);
            }
        }
    }
}
