import javax.swing.*;

public class MainGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Shop Management System");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        JPanel adminPanel = new JPanel();
        JPanel customerPanel = new JPanel();

        tabs.addTab("Admin", adminPanel);
        tabs.addTab("Customer", customerPanel);

        frame.add(tabs);
        frame.setVisible(true);
    }
}