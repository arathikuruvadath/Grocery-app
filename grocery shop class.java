import java.util.ArrayList;

public class GroceryShop {
    private ArrayList<Product> products = new ArrayList<>();

    // Add product
    public void addProduct(Product product) {
        products.add(product);
        System.out.println("✅ Product added successfully!");
    }

    // Update product by ID
    public void updateProduct(int id, String name, double price, int quantity) {
        for (Product p : products) {
            if (p.getId() == id) {
                p.setName(name);
                p.setPrice(price);
                p.setQuantity(quantity);
                System.out.println("✅ Product updated!");
                return;
            }
        }
        System.out.println("❌ Product not found!");
    }

    // Delete product by ID
    public void deleteProduct(int id) {
        products.removeIf(p -> p.getId() == id);
        System.out.println("✅ Product deleted (if it existed).");
    }

    // List all products
    public void listProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (Product p : products) {
                System.out.println(p);
            }
        }
    }

    // Search product by name
    public void searchProduct(String name) {
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                System.out.println("🔎 Found: " + p);
                return;
            }
        }
        System.out.println("❌ Product not found.");
    }
}