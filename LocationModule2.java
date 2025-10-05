import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LocationModule2 extends JPanel {

    static ArrayList<Shop> shops = new ArrayList<>();

    private DefaultListModel<String> shopListModel;
    private JList<String> shopList;

    public LocationModule2() {
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Location Module - Shops & Products", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        add(header, BorderLayout.NORTH);

        // Shop list
        shopListModel = new DefaultListModel<>();
        shopList = new JList<>(shopListModel);
        add(new JScrollPane(shopList), BorderLayout.CENTER);

        // Buttons panel
        JPanel buttons = new JPanel();
        JButton addShopBtn = new JButton("Add Shop");
        JButton addProductBtn = new JButton("Add Product to Shop");
        JButton viewProductsBtn = new JButton("View Products");
        buttons.add(addShopBtn);
        buttons.add(addProductBtn);
        buttons.add(viewProductsBtn);
        add(buttons, BorderLayout.SOUTH);

        // Button actions
        addShopBtn.addActionListener(e -> addShop());
        addProductBtn.addActionListener(e -> addProductToShop());
        viewProductsBtn.addActionListener(e -> viewProducts());

        refreshShopList();
    }

    private void refreshShopList() {
        shopListModel.clear();
        int i = 1;
        for (Shop s : shops) {
            shopListModel.addElement(i++ + ". " + s.shopName + " (" + s.location + ")");
        }
    }

    private void addShop() {
        String name = JOptionPane.showInputDialog(this, "Enter Shop Name:");
        String loc = JOptionPane.showInputDialog(this, "Enter Location:");
        if (name != null && loc != null) {
            shops.add(new Shop(name, loc));
            refreshShopList();
            JOptionPane.showMessageDialog(this, "✅ Shop added successfully!");
        }
    }

    private void addProductToShop() {
        int index = shopList.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Select a shop first!");
            return;
        }
        Shop shop = shops.get(index);

        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Product ID:"));
            String name = JOptionPane.showInputDialog(this, "Enter Product Name:");
            double price = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Price:"));
            String unit = JOptionPane.showInputDialog(this, "Enter Unit (Kg, L, Pack etc.):");
            int qty = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Quantity:"));

            shop.products.add(new Product(id, name, price, unit, qty));
            JOptionPane.showMessageDialog(this, "✅ Product added to " + shop.shopName);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    private void viewProducts() {
        int index = shopList.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Select a shop first!");
            return;
        }
        Shop shop = shops.get(index);
        if (shop.products.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠ No products in this shop.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Product p : shop.products) {
            sb.append(p.id).append(" | ").append(p.name)
              .append(" | ₹").append(p.price)
              .append(" | Stock: ").append(p.quantity)
              .append(" ").append(p.unit).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Products in " + shop.shopName, JOptionPane.INFORMATION_MESSAGE);
    }
}