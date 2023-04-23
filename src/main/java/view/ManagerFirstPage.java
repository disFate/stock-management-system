package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ManagerFirstPage extends JFrame {

    private JButton stocksButton;
    private JButton usersButton;
    private JButton notificationButton;

    private JButton backButton;

    public ManagerFirstPage() {
        setTitle("Manager Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel with a BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

        // Create a panel for the center buttons with a GridLayout
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBackground(Color.WHITE);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Create the stocks button and add it to the center panel
        stocksButton = new JButton("Stock Management");
        stocksButton.setPreferredSize(new Dimension(200, 100));
        stocksButton.setFont(new Font("Arial", Font.BOLD, 20));
        centerPanel.add(stocksButton);

        // Create the users button and add it to the center panel
        usersButton = new JButton("Users Management");
        usersButton.setPreferredSize(new Dimension(200, 100));
        usersButton.setFont(new Font("Arial", Font.BOLD, 20));
        centerPanel.add(usersButton);

        // Create a panel for the notification button with a FlowLayout
        JPanel notificationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        notificationPanel.setBackground(Color.WHITE);
        mainPanel.add(notificationPanel, BorderLayout.NORTH);

        // Create the notification button and add it to the notification panel
        notificationButton = new JButton("Notifications");
        notificationButton.setPreferredSize(new Dimension(150, 40));
        notificationButton.setFont(new Font("Arial", Font.BOLD, 14));
        notificationPanel.add(notificationButton);

        // Set the main panel as the content pane
        setContentPane(mainPanel);

        // Center the frame on the screen
        setLocationRelativeTo(null);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        backButton = new JButton("back");
        bottomPanel.add(backButton, BorderLayout.WEST);
    }

    public static void main(String[] args) {
        ManagerFirstPage managerFirstPage = new ManagerFirstPage();
        managerFirstPage.setVisible(true);
    }
}