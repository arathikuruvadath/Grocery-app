import javax.swing.*;

public class MainGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Grocery Management System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        // Admin tab
        tabs.addTab("Admin", new AdminModule2());

        // Customer tab
        tabs.addTab("Customer", new CustomerModule2());

        // Location tab
        tabs.addTab("Location", new LocationModule2());

        frame.add(tabs);
        frame.setVisible(true);
    }
}