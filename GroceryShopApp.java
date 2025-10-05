<<<<<<< HEAD
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
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

    Object[] toRow() {
        return new Object[]{id, name, price, quantity, unit};
    }

    @Override
    public String toString() {
        return id + " | " + name + " | ₹" + price + " | " + quantity + " " + unit;
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

// ---------------------- MAIN APP ----------------------
public class GroceryShopApp {
    static List<Shop> shops = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GroceryShopApp::showSplashScreen);
    }

    // ---------------------- SPLASH SCREEN ----------------------
    static void showSplashScreen() {
        JFrame splash = new JFrame("Welcome");
        splash.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        splash.setSize(300, 150);
        splash.setLayout(new BorderLayout());

        JButton startBtn = new JButton("GroceryShopApp");
        startBtn.setFont(new Font("Arial", Font.BOLD, 16));
        splash.add(startBtn, BorderLayout.CENTER);

        startBtn.addActionListener(e -> {
            splash.dispose();
            showMainMenu();
        });

        splash.setLocationRelativeTo(null);
        splash.setVisible(true);
    }

    // ---------------------- MAIN MENU ----------------------
    static void showMainMenu() {
        JFrame menu = new JFrame("GroceryShop - Main Menu");
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setSize(400, 250);
        menu.setLayout(new GridLayout(3, 1, 10, 10));

        JButton adminBtn = new JButton("Admin Mode");
        JButton customerBtn = new JButton("Customer Mode");
        JButton exitBtn = new JButton("Exit");

        menu.add(adminBtn);
        menu.add(customerBtn);
        menu.add(exitBtn);

        adminBtn.addActionListener(e -> openAdminMode());
        customerBtn.addActionListener(e -> openCustomerMode());
        exitBtn.addActionListener(e -> menu.dispose());

        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
    }

    // ---------------------- ADMIN MODE ----------------------
    static void openAdminMode() {
        String[] options = {"Add Shop", "Add Product", "Update Product", "Delete Product", "View Shops", "Back"};
        while (true) {
            int choice = JOptionPane.showOptionDialog(
                    null, "Choose an option", "Admin Mode",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            if (choice == 0) addShopGUI();
            else if (choice == 1) addProductGUI();
            else if (choice == 2) updateProductGUI();
            else if (choice == 3) deleteProductGUI();
            else if (choice == 4) viewShopsGUI();
            else break;
        }
    }

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
            boolean exists = shops.stream().anyMatch(s -> s.shopName.equalsIgnoreCase(name) && s.location.equalsIgnoreCase(loc));
            if (exists) JOptionPane.showMessageDialog(null, "⚠ Shop already exists!");
            else shops.add(new Shop(name, loc));
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

                boolean exists = shop.products.stream().anyMatch(p -> p.id == id || p.name.equalsIgnoreCase(name));
                if (exists) JOptionPane.showMessageDialog(null, "⚠ Product already exists in this shop!");
                else shop.products.add(new Product(id, name, price, unit, qty));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "❌ Invalid input!");
            }
        }
    }

    static void updateProductGUI() {
        if (shops.isEmpty()) { JOptionPane.showMessageDialog(null, "⚠ No shops available."); return; }
        Shop shop = selectShopDialog();
        if (shop == null || shop.products.isEmpty()) { JOptionPane.showMessageDialog(null, "⚠ No products in this shop."); return; }
        Product product = selectProductDialog(shop, "Select Product to Update"); if (product == null) return;

        JTextField nameField = new JTextField(product.name);
        JTextField priceField = new JTextField(String.valueOf(product.price));
        JTextField qtyField = new JTextField(String.valueOf(product.quantity));
        JTextField unitField = new JTextField(product.unit);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Product Name:")); panel.add(nameField);
        panel.add(new JLabel("Price:")); panel.add(priceField);
        panel.add(new JLabel("Quantity:")); panel.add(qtyField);
        panel.add(new JLabel("Unit:")); panel.add(unitField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Update Product", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                product.name = nameField.getText().trim();
                product.price = Double.parseDouble(priceField.getText());
                product.quantity = Integer.parseInt(qtyField.getText());
                product.unit = unitField.getText().trim();
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(null, "❌ Invalid input!"); }
        }
    }

    static void deleteProductGUI() {
        if (shops.isEmpty()) { JOptionPane.showMessageDialog(null, "⚠ No shops available."); return; }
        Shop shop = selectShopDialog();
        if (shop == null || shop.products.isEmpty()) { JOptionPane.showMessageDialog(null, "⚠ No products in this shop."); return; }
        Product product = selectProductDialog(shop, "Select Product to Delete"); if (product == null) return;

        int confirm = JOptionPane.showConfirmDialog(null, "Delete " + product.name + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) shop.products.remove(product);
    }

    // ---------------------- VIEW SHOPS ----------------------
    static void viewShopsGUI() {
        if (shops.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠ No shops available.");
            return;
        }

        String[] shopNames = shops.stream().map(s -> s.shopName + " (" + s.location + ")").toArray(String[]::new);
        JList<String> shopList = new JList<>(shopNames);
        shopList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroll = new JScrollPane(shopList);
        listScroll.setPreferredSize(new Dimension(300, Math.min(shops.size() * 25, 200)));

        int result = JOptionPane.showConfirmDialog(null, listScroll, "Select a Shop", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION && shopList.getSelectedIndex() != -1) {
            Shop selectedShop = shops.get(shopList.getSelectedIndex());
            showShopProductsTable(selectedShop);
        }
    }

    static void showShopProductsTable(Shop shop) {
        if (shop.products.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠ No products available in this shop.");
            return;
        }

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Name","Price","Quantity","Unit"},0);
        for (Product p : shop.products) model.addRow(p.toRow());
        table.setModel(model);
        table.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(500, Math.min(200, shop.products.size() * 25 + 25)));

        JOptionPane.showMessageDialog(null, scroll, "Products in " + shop.shopName, JOptionPane.INFORMATION_MESSAGE);
    }

    // ---------------------- CUSTOMER MODE ----------------------
    static void openCustomerMode() {
        if (shops.isEmpty()) { JOptionPane.showMessageDialog(null, "⚠ No shops available."); return; }

        String loc = JOptionPane.showInputDialog("Enter location to search:");
        if (loc == null || loc.trim().isEmpty()) return;

        List<Shop> matched = new ArrayList<>();
        for (Shop s : shops) if (s.location.toLowerCase().contains(loc.toLowerCase())) matched.add(s);

        if (matched.isEmpty()) { JOptionPane.showMessageDialog(null, "❌ No shops found in this location!"); return; }

        Shop[] shopArray = matched.toArray(new Shop[0]);
        Shop selectedShop = (Shop) JOptionPane.showInputDialog(null, "Select Shop", "Shop Selection",
                JOptionPane.PLAIN_MESSAGE, null, shopArray, shopArray[0]);

        if (selectedShop != null) showCustomerProducts(selectedShop);
    }

    static void showCustomerProducts(Shop shop) {
        String[] options = {"View Products", "Search Product", "Back"};
        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "Customer Mode (" + shop.shopName + ")", "Customer Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (choice == 0) viewProductsTable(shop);
            else if (choice == 1) searchProduct(shop);
            else break;
        }
    }

    static void viewProductsTable(Shop shop) {
        if (shop.products.isEmpty()) { JOptionPane.showMessageDialog(null, "⚠ No products available in this shop."); return; }

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Name","Price","Quantity","Unit"},0);
        for (Product p : shop.products) model.addRow(p.toRow());
        table.setModel(model);
        table.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(500, Math.min(200, shop.products.size() * 25 + 25)));

        JOptionPane.showMessageDialog(null, scroll, "Products in " + shop.shopName, JOptionPane.INFORMATION_MESSAGE);
    }

    static void searchProduct(Shop shop) {
        String keyword = JOptionPane.showInputDialog("Enter product name or ID:");
        if (keyword == null || keyword.trim().isEmpty()) return;

        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for (Product p : shop.products) {
            if (p.name.equalsIgnoreCase(keyword) ||
                    p.name.toLowerCase().contains(keyword.toLowerCase()) ||
                    String.valueOf(p.id).equals(keyword)) {
                sb.append(p).append("\n");
                found = true;
            }
        }

        if (!found) sb.append("❌ No matching product found!");
        JTextArea textArea = new JTextArea(sb.toString(), 20, 50);
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Search Results", JOptionPane.INFORMATION_MESSAGE);
    }

    // ---------------------- HELPERS ----------------------
    static Shop selectShopDialog() {
        Shop[] shopArray = shops.toArray(new Shop[0]);
        return (Shop) JOptionPane.showInputDialog(
                null, "Select Shop", "Choose Shop",
                JOptionPane.PLAIN_MESSAGE, null, shopArray, shopArray[0]);
    }

    static Product selectProductDialog(Shop shop, String title) {
        Product[] prodArray = shop.products.toArray(new Product[0]);
        return (Product) JOptionPane.showInputDialog(
                null, "Select Product", title,
                JOptionPane.PLAIN_MESSAGE, null, prodArray, prodArray[0]);
    }
}
=======
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    Object[] toRow() {
        return new Object[]{id, name, price, quantity, unit};
    }

    @Override
    public String toString() {
        return id + " | " + name + " | ₹" + price + " | " + quantity + " " + unit;
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

public class GroceryShopApp {
    static List<Shop> shops = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GroceryShopApp::showSplashScreen);
    }

    static void showSplashScreen() {
        JFrame splash = new JFrame("Welcome");
        splash.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        splash.setSize(300, 150);
        splash.setLayout(new BorderLayout());

        JButton startBtn = new JButton("GroceryShopApp");
        startBtn.setFont(new Font("Arial", Font.BOLD, 16));
        splash.add(startBtn, BorderLayout.CENTER);

        startBtn.addActionListener(e -> {
            splash.dispose();
            showMainMenu();
        });

        splash.setLocationRelativeTo(null);
        splash.setVisible(true);
    }

    static void showMainMenu() {
        JFrame menu = new JFrame("GroceryShop - Main Menu");
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setSize(400, 250);
        menu.setLayout(new GridLayout(3, 1, 10, 10));

        JButton adminBtn = new JButton("Admin Mode");
        JButton customerBtn = new JButton("Customer Mode");
        JButton exitBtn = new JButton("Exit");

        menu.add(adminBtn);
        menu.add(customerBtn);
        menu.add(exitBtn);

        adminBtn.addActionListener(e -> openAdminMode());
        customerBtn.addActionListener(e -> openCustomerMode());
        exitBtn.addActionListener(e -> menu.dispose());

        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
    }

    static void openAdminMode() {
        String[] options = {"Add Shop", "Add Product", "Update Product", "Delete Product", "View Shops", "Back"};
        while (true) {
            int choice = JOptionPane.showOptionDialog(
                    null, "Choose an option", "Admin Mode",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            if (choice == JOptionPane.CLOSED_OPTION || choice == 5) break;
            switch (choice) {
                case 0 -> addShopGUI();
                case 1 -> addProductGUI();
                case 2 -> updateProductGUI();
                case 3 -> deleteProductGUI();
                case 4 -> viewShopsGUI();
            }
        }
    }

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
            if (name.isEmpty() || loc.isEmpty()) {
                JOptionPane.showMessageDialog(null, "⚠ Please enter valid details!");
                return;
            }
            boolean exists = shops.stream().anyMatch(s -> s.shopName.equalsIgnoreCase(name) && s.location.equalsIgnoreCase(loc));
            if (exists) JOptionPane.showMessageDialog(null, "⚠ Shop already exists!");
            else shops.add(new Shop(name, loc));
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
                String unit = unitField.getText().trim();
                int qty = Integer.parseInt(qtyField.getText());

                if (name.isEmpty() || unit.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "⚠ Please fill all fields!");
                    return;
                }

                boolean exists = shop.products.stream().anyMatch(p -> p.id == id || p.name.equalsIgnoreCase(name));
                if (exists) JOptionPane.showMessageDialog(null, "⚠ Product already exists in this shop!");
                else shop.products.add(new Product(id, name, price, unit, qty));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "❌ Invalid input!");
            }
        }
    }

    static void updateProductGUI() {
        if (shops.isEmpty()) { JOptionPane.showMessageDialog(null, "⚠ No shops available."); return; }
        Shop shop = selectShopDialog();
        if (shop == null || shop.products.isEmpty()) { JOptionPane.showMessageDialog(null, "⚠ No products in this shop."); return; }
        Product product = selectProductDialog(shop, "Select Product to Update");
        if (product == null) return;

        JTextField nameField = new JTextField(product.name);
        JTextField priceField = new JTextField(String.valueOf(product.price));
        JTextField qtyField = new JTextField(String.valueOf(product.quantity));
        JTextField unitField = new JTextField(product.unit);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Product Name:")); panel.add(nameField);
        panel.add(new JLabel("Price:")); panel.add(priceField);
        panel.add(new JLabel("Quantity:")); panel.add(qtyField);
        panel.add(new JLabel("Unit:")); panel.add(unitField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Update Product", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                product.name = nameField.getText().trim();
                product.price = Double.parseDouble(priceField.getText());
                product.quantity = Integer.parseInt(qtyField.getText());
                product.unit = unitField.getText().trim();
                JOptionPane.showMessageDialog(null, "✅ Product updated successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "❌ Invalid input!");
            }
        }
    }

    static void deleteProductGUI() {
        if (shops.isEmpty()) { JOptionPane.showMessageDialog(null, "⚠ No shops available."); return; }
        Shop shop = selectShopDialog();
        if (shop == null || shop.products.isEmpty()) { JOptionPane.showMessageDialog(null, "⚠ No products in this shop."); return; }
        Product product = selectProductDialog(shop, "Select Product to Delete");
        if (product == null) return;

        int confirm = JOptionPane.showConfirmDialog(null, "Delete " + product.name + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            shop.products.remove(product);
            JOptionPane.showMessageDialog(null, "🗑 Product deleted successfully!");
        }
    }

    static void viewShopsGUI() {
        if (shops.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠ No shops available.");
            return;
        }

        String[] shopNames = shops.stream().map(s -> s.shopName + " (" + s.location + ")").toArray(String[]::new);
        JList<String> shopList = new JList<>(shopNames);
        shopList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroll = new JScrollPane(shopList);
        listScroll.setPreferredSize(new Dimension(300, Math.min(shops.size() * 25, 200)));

        int result = JOptionPane.showConfirmDialog(null, listScroll, "Select a Shop", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION && shopList.getSelectedIndex() != -1) {
            Shop selectedShop = shops.get(shopList.getSelectedIndex());
            showShopProductsTable(selectedShop);
        }
    }

    static void showShopProductsTable(Shop shop) {
        if (shop.products.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠ No products available in this shop.");
            return;
        }

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Name","Price","Quantity","Unit"},0);
        for (Product p : shop.products) model.addRow(p.toRow());
        table.setModel(model);
        table.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(500, Math.min(200, shop.products.size() * 25 + 25)));

        JOptionPane.showMessageDialog(null, scroll, "Products in " + shop.shopName, JOptionPane.INFORMATION_MESSAGE);
    }

    static void openCustomerMode() {
        if (shops.isEmpty()) { JOptionPane.showMessageDialog(null, "⚠ No shops available."); return; }

        String loc = JOptionPane.showInputDialog("Enter location to search:");
        if (loc == null || loc.trim().isEmpty()) return;

        List<Shop> matched = new ArrayList<>();
        for (Shop s : shops) if (s.location.toLowerCase().contains(loc.toLowerCase())) matched.add(s);

        if (matched.isEmpty()) { JOptionPane.showMessageDialog(null, "❌ No shops found in this location!"); return; }

        Shop[] shopArray = matched.toArray(new Shop[0]);
        Shop selectedShop = (Shop) JOptionPane.showInputDialog(null, "Select Shop", "Shop Selection",
                JOptionPane.PLAIN_MESSAGE, null, shopArray, shopArray[0]);

        if (selectedShop != null) showCustomerProducts(selectedShop);
    }

    static void showCustomerProducts(Shop shop) {
        String[] options = {"View Products", "Search Product", "Back"};
        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "Customer Mode (" + shop.shopName + ")", "Customer Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (choice == JOptionPane.CLOSED_OPTION || choice == 2) break;
            if (choice == 0) viewProductsTable(shop);
            if (choice == 1) searchProduct(shop);
        }
    }

    static void viewProductsTable(Shop shop) {
        if (shop.products.isEmpty()) { JOptionPane.showMessageDialog(null, "⚠ No products available in this shop."); return; }

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Name","Price","Quantity","Unit"},0);
        for (Product p : shop.products) model.addRow(p.toRow());
        table.setModel(model);
        table.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(500, Math.min(200, shop.products.size() * 25 + 25)));

        JOptionPane.showMessageDialog(null, scroll, "Products in " + shop.shopName, JOptionPane.INFORMATION_MESSAGE);
    }

    static void searchProduct(Shop shop) {
        String keyword = JOptionPane.showInputDialog("Enter product name or ID:");
        if (keyword == null || keyword.trim().isEmpty()) return;

        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for (Product p : shop.products) {
            if (p.name.toLowerCase().contains(keyword.toLowerCase()) ||
                String.valueOf(p.id).equals(keyword.trim())) {
                sb.append(p).append("\n");
                found = true;
            }
        }

        if (!found) sb.append("❌ No matching product found!");
        JTextArea textArea = new JTextArea(sb.toString(), 20, 50);
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Search Results", JOptionPane.INFORMATION_MESSAGE);
    }

    static Shop selectShopDialog() {
        if (shops.isEmpty()) return null;
        Shop[] shopArray = shops.toArray(new Shop[0]);
        Shop selected = (Shop) JOptionPane.showInputDialog(null, "Select Shop", "Choose Shop",
                JOptionPane.PLAIN_MESSAGE, null, shopArray, shopArray[0]);
        return selected;
    }

    static Product selectProductDialog(Shop shop, String title) {
        if (shop.products.isEmpty()) return null;
        Product[] prodArray = shop.products.toArray(new Product[0]);
        Product selected = (Product) JOptionPane.showInputDialog(null, "Select Product", title,
                JOptionPane.PLAIN_MESSAGE, null, prodArray, prodArray[0]);
        return selected;
    }
}
>>>>>>> a25931dfea8c7f97a6356d52d512cf32f5a91a83
