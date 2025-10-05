import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

// ---------------------- PRODUCT CLASS ----------------------
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

    @Override
    public String toString() {
        return id + " | " + name + " | " + price + " per " + unit + " | Qty: " + quantity;
    }
}

// ---------------------- SHOP CLASS ----------------------
class Shop {
    String shopName;
    String location;
    List<Product> products = new ArrayList<>();

    Shop(String shopName, String location) {
        this.shopName = shopName;
        this.location = location;
    }

    @Override
    public String toString() {
        return shopName + " (" + location + ")";
    }
}

// ---------------------- MAIN SYSTEM ----------------------
public class GroceryShopSystem {

    static List<Shop> shops = new ArrayList<>();

    public static void main(String[] args) {
        String[] modes = {"Admin", "Customer", "Exit"};
        while (true) {
            int mode = JOptionPane.showOptionDialog(null, "Select Mode:", "Grocery Shop System",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, modes, modes[0]);

            switch (mode) {
                case 0 -> showAdminMenu();
                case 1 -> showCustomerMenu();
                case 2, JOptionPane.CLOSED_OPTION -> System.exit(0);
            }
        }
    }

    // ---------------------- ADMIN MENU ----------------------
    static void showAdminMenu() {
        String[] options = {"Add Shop", "Add Product", "View Products", "Back"};
        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "Admin Menu", "Admin Mode",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (choice) {
                case 0 -> addShopGUI();
                case 1 -> addProductGUI();
                case 2 -> viewProductsGUI();
                case 3, JOptionPane.CLOSED_OPTION -> { return; }
            }
        }
    }

    // ---------------------- ADD SHOP ----------------------
    static void addShopGUI() {
        JTextField nameField = new JTextField(10);
        JTextField locField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Shop Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Location:"));
        panel.add(locField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Shop", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String loc = locField.getText().trim();

            boolean exists = shops.stream().anyMatch(
                    s -> s.shopName.equalsIgnoreCase(name) && s.location.equalsIgnoreCase(loc));

            if (exists) {
                JOptionPane.showMessageDialog(null, "⚠ Shop already exists at this location!");
                return;
            }

            shops.add(new Shop(name, loc));
            JOptionPane.showMessageDialog(null, "✅ Shop added successfully!");
        }
    }

    // ---------------------- ADD PRODUCT ----------------------
    static void addProductGUI() {
        if (shops.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠ No shops available. Add a shop first.");
            return;
        }

        Shop shop = selectShopDialog();
        if (shop == null) return;

        JTextField idField = new JTextField(5);
        JTextField nameField = new JTextField(10);
        JTextField priceField = new JTextField(7);
        JTextField unitField = new JTextField(5);
        JTextField qtyField = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Product ID:")); panel.add(idField);
        panel.add(new JLabel("Product Name:")); panel.add(nameField);
        panel.add(new JLabel("Price:")); panel.add(priceField);
        panel.add(new JLabel("Unit:")); panel.add(unitField);
        panel.add(new JLabel("Quantity:")); panel.add(qtyField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Product to " + shop.shopName, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText().trim();
                double price = Double.parseDouble(priceField.getText());
                String unit = unitField.getText();
                int qty = Integer.parseInt(qtyField.getText());

                boolean exists = shop.products.stream().anyMatch(
                        p -> p.id == id || p.name.equalsIgnoreCase(name));

                if (exists) {
                    JOptionPane.showMessageDialog(null, "⚠ Product already exists in this shop!");
                    return;
                }

                shop.products.add(new Product(id, name, price, unit, qty));
                JOptionPane.showMessageDialog(null, "✅ Product added!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "❌ Invalid input!");
            }
        }
    }

    // ---------------------- VIEW PRODUCTS ----------------------
    static void viewProductsGUI() {
        if (shops.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠ No shops available.");
            return;
        }

        Shop shop = selectShopDialog();
        if (shop == null) return;

        if (shop.products.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠ No products in this shop.");
            return;
        }

        StringBuilder sb = new StringBuilder("Products in " + shop.shopName + ":\n");
        for (Product p : shop.products) sb.append(p).append("\n");

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // ---------------------- CUSTOMER MENU ----------------------
    static void showCustomerMenu() {
        String[] options = {"Browse Shops", "Search Product", "Buy Product", "Back"};
        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "Customer Menu", "Customer Mode",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0 -> browseShopsGUI();
                case 1 -> searchProductGUI();
                case 2 -> buyProductGUI();
                case 3, JOptionPane.CLOSED_OPTION -> { return; }
            }
        }
    }

    // ---------------------- CUSTOMER: BROWSE SHOPS ----------------------
    static void browseShopsGUI() {
        if (shops.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠ No shops available.");
            return;
        }

        StringBuilder sb = new StringBuilder("Available Shops:\n");
        for (Shop s : shops) sb.append(s).append("\n");

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // ---------------------- CUSTOMER: SEARCH PRODUCT ----------------------
    static void searchProductGUI() {
        if (shops.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠ No shops available.");
            return;
        }

        String query = JOptionPane.showInputDialog("Enter product name to search:");
        if (query == null || query.isBlank()) return;

        StringBuilder sb = new StringBuilder("Search Results:\n");
        for (Shop s : shops) {
            for (Product p : s.products) {
                if (p.name.equalsIgnoreCase(query)) {
                    sb.append(p).append(" @ ").append(s.shopName).append(" (").append(s.location).append(")\n");
                }
            }
        }
        JOptionPane.showMessageDialog(null, sb.length() > 16 ? sb.toString() : "❌ No product found!");
    }

    // ---------------------- CUSTOMER: BUY PRODUCT ----------------------
    static void buyProductGUI() {
        if (shops.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠ No shops available.");
            return;
        }

        Shop shop = selectShopDialog();
        if (shop == null || shop.products.isEmpty()) return;

        String input = JOptionPane.showInputDialog("Enter Product ID to buy:");
        if (input == null) return;

        try {
            int pid = Integer.parseInt(input);
            Product product = shop.products.stream().filter(p -> p.id == pid).findFirst().orElse(null);

            if (product == null) {
                JOptionPane.showMessageDialog(null, "❌ Product not found!");
                return;
            }

            String qtyInput = JOptionPane.showInputDialog("Enter quantity:");
            if (qtyInput == null) return;
            int qty = Integer.parseInt(qtyInput);

            if (qty <= 0 || qty > product.quantity) {
                JOptionPane.showMessageDialog(null, "⚠ Invalid quantity!");
                return;
            }

            product.quantity -= qty;
            JOptionPane.showMessageDialog(null, "✅ Purchased " + qty + " " + product.unit + " of " + product.name);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "❌ Invalid input!");
        }
    }

    // ---------------------- SELECT SHOP ----------------------
    static Shop selectShopDialog() {
        String[] shopArray = shops.stream().map(Shop::toString).toArray(String[]::new);
        int idx = JOptionPane.showOptionDialog(null, "Select Shop:", "Choose Shop",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, shopArray, shopArray[0]);
        return (idx >= 0 && idx < shops.size()) ? shops.get(idx) : null;
    }
}
