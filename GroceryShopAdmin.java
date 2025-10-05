import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


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

public class GroceryShopAdmin {
    static List<Shop> shops = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GroceryShopAdmin::showAdminMenu);
    }

    
    static void showAdminMenu() {
        String[] options = {"Add Shop", "Add Product", "View Products", "Exit"};
        while (true) {
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Admin Menu",
                    "Admin Mode",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (choice) {
                case 0 -> addShopGUI();
                case 1 -> addProductGUI();
                case 2 -> viewProductsGUI();
                case 3, JOptionPane.CLOSED_OPTION -> System.exit(0);
                default -> {}
            }
        }
    }

    static void addShopGUI() {
        JTextField nameField = new JTextField(10);
        JTextField locField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Shop Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Location:"));
        panel.add(locField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Shop", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String loc = locField.getText().trim();

            if (name.isEmpty() || loc.isEmpty()) {
                JOptionPane.showMessageDialog(null, "⚠ Please fill in all fields!");
                return;
            }

            boolean exists = shops.stream().anyMatch(
                    s -> s.shopName.equalsIgnoreCase(name) && s.location.equalsIgnoreCase(loc)
            );
            if (exists) {
                JOptionPane.showMessageDialog(null, "⚠ Shop already exists at this location!");
                return;
            }

            shops.add(new Shop(name, loc));
            JOptionPane.showMessageDialog(null, "✅ Shop added successfully!");
        }
    }

    
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

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(new JLabel("Product ID:"));
        panel.add(idField);
        panel.add(new JLabel("Product Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Unit:"));
        panel.add(unitField);
        panel.add(new JLabel("Quantity:"));
        panel.add(qtyField);

        int result = JOptionPane.showConfirmDialog(
                null, panel, "Add Product to " + shop.shopName, JOptionPane.OK_CANCEL_OPTION
        );
        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                String unit = unitField.getText().trim();
                int qty = Integer.parseInt(qtyField.getText().trim());

                if (name.isEmpty() || unit.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "⚠ Please fill in all fields!");
                    return;
                }

                boolean exists = shop.products.stream().anyMatch(
                        p -> p.id == id || p.name.equalsIgnoreCase(name)
                );
                if (exists) {
                    JOptionPane.showMessageDialog(null, "⚠ Product already exists in this shop!");
                    return;
                }

                shop.products.add(new Product(id, name, price, unit, qty));
                JOptionPane.showMessageDialog(null, "✅ Product added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "❌ Invalid input! Check numeric fields.");
            }
        }
    }

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

        StringBuilder sb = new StringBuilder("Products in " + shop.shopName + ":\n\n");
        for (Product p : shop.products) sb.append(p).append("\n");

        JTextArea textArea = new JTextArea(sb.toString(), 10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(null, scrollPane, "View Products", JOptionPane.INFORMATION_MESSAGE);
    }

    static Shop selectShopDialog() {
        String[] shopArray = shops.stream().map(Shop::toString).toArray(String[]::new);
        String selected = (String) JOptionPane.showInputDialog(
                null, "Select Shop:", "Choose Shop",
                JOptionPane.QUESTION_MESSAGE, null, shopArray, shopArray[0]
        );
        if (selected == null) return null;
        return shops.stream().filter(s -> s.toString().equals(selected)).findFirst().orElse(null);
    }
}