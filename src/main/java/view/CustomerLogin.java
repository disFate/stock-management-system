package view;
import javax.swing.*;
import java.awt.*;

import controller.UserController;
import dao.IUserDAO;
import model.Entity.User;
import controller.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import session.*;

public class CustomerLogin extends JPanel {
    private String userId = "";
    private String password = "";
    private UserController userController;
    private JPanel mainPanel;

    public CustomerLogin(JPanel mainPanel, UserController userController) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("User ID:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField userIdField = new JTextField(15);
        add(userIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPasswordField passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            userId = userIdField.getText();
            char[] passwordCharArray = passwordField.getPassword();
            password = new String(passwordCharArray);

            User user = userController.getUserInfo(Integer.parseInt(userId));
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Invalid ID");
                return;
            }
            if (!user.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(this, "Invalid password");
                return;
            }
            if (!user.getRole().equals(User.Role.CUSTOMER)) {
                JOptionPane.showMessageDialog(this, "Not a customer account.");
                return;
            }

            // Set current user
            CurrentUser.setCurrentUser(user);

            // Create a UserProfile panel with the logged-in user and add it to the mainPanel
            UserProfile userProfile = new UserProfile(mainPanel,user);
            mainPanel.add(userProfile, "UserProfile");

            // Switch to the UserProfile screen
            CardLayout layout = (CardLayout) mainPanel.getLayout();
            layout.show(mainPanel, "UserProfile");
        });
        add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> {
            Component[] components = mainPanel.getComponents();
            for (Component component : components) {
                if (component instanceof SignUp) {
                    CardLayout layout = (CardLayout) mainPanel.getLayout();
                    layout.show(mainPanel, "SignUp");
                    return;
                }
            }

            // Create a new SignUp panel and add it to the mainPanel
            SignUp signUpPanel = new SignUp(mainPanel, userController);
            mainPanel.add(signUpPanel, "SignUp");

            // Switch to the SignUp screen
            CardLayout layout = (CardLayout) mainPanel.getLayout();
            layout.show(mainPanel, "SignUp");
        });
        add(signUpButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            userId = "";
            password = "";
            userIdField.setText("");
            passwordField.setText("");

            CardLayout layout = (CardLayout) mainPanel.getLayout();
            layout.show(mainPanel, "Welcome");
        });
        add(backButton, gbc);
    }
}
