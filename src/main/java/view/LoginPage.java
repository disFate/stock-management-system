package view;
import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Home Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel(new CardLayout());

        // Welcome Panel
        JPanel welcomePanel = createWelcomePanel(mainPanel);
        mainPanel.add(welcomePanel, "Welcome");

        // Customer Login Panel
        JPanel customerLoginPanel = createCustomerLoginPanel(mainPanel);
        mainPanel.add(customerLoginPanel, "Customer Login");

        // Manager Login Panel
        JPanel managerLoginPanel = createManagerLoginPanel(mainPanel);
        mainPanel.add(managerLoginPanel, "Manager Login");

        frame.getContentPane().add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel createWelcomePanel(JPanel mainPanel) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel welcomeLabel = new JLabel("Welcome to the Portfolio Stock System!");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(welcomeLabel, gbc);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints buttonsGbc = new GridBagConstraints();

        JButton managerButton = new JButton("Manager");
        managerButton.addActionListener(e -> {
            CardLayout layout = (CardLayout) mainPanel.getLayout();
            layout.show(mainPanel, "Manager Login");
        });
        buttonsGbc.gridx = 0;
        buttonsGbc.gridy = 0;
        buttonsGbc.weightx = 0.5;
        buttonsGbc.weighty = 1;
        buttonsGbc.insets = new Insets(0, 0, 0, 10);
        buttonsGbc.anchor = GridBagConstraints.EAST;
        buttonsPanel.add(managerButton, buttonsGbc);

        JButton customerButton = new JButton("Customer");
        customerButton.addActionListener(e -> {
            CardLayout layout = (CardLayout) mainPanel.getLayout();
            layout.show(mainPanel, "Customer Login");
        });
        buttonsGbc.gridx = 1;
        buttonsGbc.gridy = 0;
        buttonsGbc.weightx = 0.5;
        buttonsGbc.weighty = 1;
        buttonsGbc.insets = new Insets(0, 10, 0, 0);
        buttonsGbc.anchor = GridBagConstraints.WEST;
        buttonsPanel.add(customerButton, buttonsGbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonsPanel, gbc);

        return panel;
    }


    private static JPanel createCustomerLoginPanel(JPanel mainPanel) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("User ID:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField userIdField = new JTextField(15);
        panel.add(userIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPasswordField passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String userId = userIdField.getText();
            char[] passwordCharArray = passwordField.getPassword();
            String password = new String(passwordCharArray);

            System.out.println("User ID: " + userId);
            System.out.println("Password: " + password);
        });
        panel.add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            CardLayout layout = (CardLayout) mainPanel.getLayout();
            layout.show(mainPanel, "Welcome");
        });
        panel.add(backButton, gbc);


        return panel;
    }


    private static JPanel createManagerLoginPanel(JPanel mainPanel) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Manager ID:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JPasswordField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> System.out.println("Manager login button clicked"));
        panel.add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            CardLayout layout = (CardLayout) mainPanel.getLayout();
            layout.show(mainPanel, "Welcome");
        });
        panel.add(backButton, gbc);

        return panel;
    }
}