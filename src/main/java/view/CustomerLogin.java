package view;

import controller.MessageController;
import controller.StockController;
import controller.UserController;
import dao.impl.MessageDAOImpl;
import dao.impl.StockDAOImpl;
import dao.impl.TransactionDAOImpl;
import model.Entity.User;
import session.CurrentUser;
import view.userPages.UserMenuPage;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class CustomerLogin extends JPanel {
    private String userId = "";
    private String password = "";
    private UserController userController;
    private JPanel mainPanel;

    public CustomerLogin(JPanel mainPanel, UserController userController, Boolean isManager) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Email:"), gbc);

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

            //User user = userController.getUserInfo(Integer.parseInt(userId));
            User user = userController.getUserInfo(userId);
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Invalid Email");
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
            CustomerLogin customerLoginPage = this;
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    UserMenuPage userMenuPage = null;
                    try {
                        userMenuPage = new UserMenuPage(new StockController(new StockDAOImpl(), new TransactionDAOImpl()), userController, new MessageController(new MessageDAOImpl()));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    userMenuPage.setVisible(true);
                    // Close the CustomerLogin page
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(CustomerLogin.this);
                    frame.dispose();
                }
            });

            // Add the UserMenuPage to the mainPanel
            //mainPanel.add(userMenuPage, "UserMenuPage");

            // Show the UserMenuPage
            //CardLayout layout = (CardLayout) mainPanel.getLayout();
            //layout.show(mainPanel, "UserMenuPage");

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
                    // Create a new SignUp panel and add it to the mainPanel
                    SignUp signUpPanel = new SignUp(mainPanel, userController, false);
                    mainPanel.add(signUpPanel, "Customer Sign Up");

                    // Switch to the SignUp screen
                    CardLayout layout = (CardLayout) mainPanel.getLayout();
                    layout.show(mainPanel, "Customer Sign Up");
                    //CardLayout layout = (CardLayout) mainPanel.getLayout();
                    //layout.show(mainPanel, "SignUp");
                    return;
                }
            }


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
