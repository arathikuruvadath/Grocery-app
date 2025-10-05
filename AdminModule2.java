import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AdminModule2 extends JPanel {

    static ArrayList<Product> products = new ArrayList<>();

    private DefaultListModel<String> productListModel;
    private JList<String> productList;

    public AdminModule2() {
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Admin Module - Manage Products", JLabel.CENTER);
        add(header, BorderLayout.NORTH);

        // Product list
        productListModel = new DefaultListModel<>();
        productList = new JList<>(productListModel);
        add(new JScrollPane(productList), BorderLayout.CENTER);

        // Buttons panel
        JPanel buttons = new JPanel();
        JButton addBtn = new JButton("Add Product");
        JButton updateBtn = new JButton("Update Product");
        JButton deleteBtn = new JButton("Delete Product");
        buttons.add(addBtn);
        buttons.add(updateBtn);
        buttons.add(deleteBtn);
        add(buttons, BorderLayout.SOUTH);

        // Button actions
        addBtn.addActionListener(e -> addProduct());
        updateBtn.addActionListener(e -> updateProduct());
        deleteBtn.addActionListener(e -> deleteProduct());

        refreshProductList();
    }

    private void refreshProductList() {
        productListModel.clear();
        for (Product p : products) {
            productListModel.addElement(p.toString());
        }
    }

    private void addProduct() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Product ID:"));
            String name = JOptionPane.showInputDialog(this, "Enter Product Name:");
            double price = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Product Price:"));
            int qty = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Product Quantity:"));
            products.add(new Product(id, name, price, qty));
            refreshProductList();
            JOptionPane.showMessageDialog(this, "✅ Product added!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    private void updateProduct() {
        String selected = productList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a product to update!");
            return;
        }
        int id = Integer.parseInt(selected.split("\\|")[0].trim());

        for (Product p : products) {
            if (p.id == id) {
                try {
                    String name = JOptionPane.showInputDialog(this, "Enter new name:", p.name);
                    double price = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter new price:", p.price));
                    int qty = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter new quantity:", p.quantity));

                    p.name = name;
                    p.price = price;
                    p.quantity = qty;
                    refreshProductList();
                    JOptionPane.showMessageDialog(this, "✅ Product updated!");
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input!");
                    return;
                }
            }
        }
    }

    private void deleteProduct() {
        String selected = productList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a product to delete!");
            return;
        }
        int id = Integer.parseInt(selected.split("\\|")[0].trim());
        products.removeIf(p -> p.id == id);
        refreshProductList();
        JOptionPane.showMessageDialog(this, "✅ Product deleted!");
    }
}