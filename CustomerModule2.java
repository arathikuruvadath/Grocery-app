import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomerModule2 extends JPanel {

    static ArrayList<Product> products = new ArrayList<>(Arrays.asList(
            new Product(101, "Rice", 55.5, 10),
            new Product(102, "Sugar", 45.0, 20),
            new Product(103, "Milk", 30.0, 15)
    ));

    private JList<String> productList;
    private DefaultListModel<String> productModel;

    public CustomerModule2() {
        setLayout(new BorderLayout());

        // Top label
        JLabel header = new JLabel("Customer Module - Available Products", JLabel.CENTER);
        add(header, BorderLayout.NORTH);

        // Product list
        productModel = new DefaultListModel<>();
        for (Product p : products) {
            productModel.addElement(p.id + " | " + p.name + " | ₹" + p.price + " | Stock: " + p.quantity);
        }
        productList = new JList<>(productModel);
        add(new JScrollPane(productList), BorderLayout.CENTER);

        // Buttons panel
        JPanel buttons = new JPanel();
        JButton viewButton = new JButton("View Details");
        JButton searchButton = new JButton("Search Product");
        buttons.add(viewButton);
        buttons.add(searchButton);
        add(buttons, BorderLayout.SOUTH);

        // Button actions
        viewButton.addActionListener(e -> viewProductDetails());
        searchButton.addActionListener(e -> searchProduct());
    }

    private void viewProductDetails() {
        String selected = productList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a product!");
            return;
        }

        int id = Integer.parseInt(selected.split("\\|")[0].trim());
        for (Product p : products) {
            if (p.id == id) {
                JOptionPane.showMessageDialog(this,
                        "ID: " + p.id +
                                "\nName: " + p.name +
                                "\nPrice: ₹" + p.price +
                                "\nQuantity: " + p.quantity,
                        "Product Details",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
    }

    private void searchProduct() {
        String keyword = JOptionPane.showInputDialog(this, "Enter product name keyword:");
        if (keyword == null || keyword.isEmpty()) return;

        DefaultListModel<String> searchResults = new DefaultListModel<>();
        for (Product p : products) {
            if (p.name.toLowerCase().contains(keyword.toLowerCase())) {
                searchResults.addElement(p.id + " | " + p.name + " | ₹" + p.price + " | Stock: " + p.quantity);
            }
        }

        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No matching product found!");
        } else {
            productList.setModel(searchResults);
        }
    }
}