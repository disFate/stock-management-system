package view;
import javax.swing.*;
import java.awt.*;

import controller.UserController;
import dao.IUserDAO;
import model.Entity.User;
import controller.*;
import view.managerPages.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import session.*;
public class ManagerLogin extends JPanel {
    private String managerId = "";
    private String password = "";
    private UserController userController;
    private JPanel mainPanel;
    public ManagerLogin(JPanel mainPanel, UserController userController) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Manager Email:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField managerIdField = new JTextField(15);
        add(managerIdField, gbc);

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
            managerId = managerIdField.getText();
            char[] passwordCharArray = passwordField.getPassword();
            password = new String(passwordCharArray);

            User user = userController.getUserInfo(managerId);
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Invalid Email");
                return;
            }
            if (!user.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(this, "Invalid password");
                return;
            }
            if (!user.getRole().equals(User.Role.MANAGER)) {
                JOptionPane.showMessageDialog(this, "Not a manager account.");
                return;
            }

            // Set current user
            CurrentUser.setCurrentUser(user);

            ManagerFirstPage managerFirstPage = new ManagerFirstPage(userController);
            managerFirstPage.setVisible(true);

            // Close the ManagerLogin page
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ManagerLogin.this);
            frame.dispose();

            managerIdField.setText("");
            passwordField.setText("");

        });
        add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            managerId = "";
            password = "";
            managerIdField.setText("");
            passwordField.setText("");

            CardLayout layout = (CardLayout) mainPanel.getLayout();
            layout.show(mainPanel, "Welcome");
        });
        add(backButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> {
            Component[] components = mainPanel.getComponents();
            for (Component component : components) {
                if (component instanceof SignUp) {
                    // Create a new SignUp panel and add it to the mainPanel
                    SignUp signUpPanel = new SignUp(mainPanel, userController, true);
                    mainPanel.add(signUpPanel, "Manager Sign Up");

                    // Switch to the SignUp screen
                    CardLayout layout = (CardLayout) mainPanel.getLayout();
                    layout.show(mainPanel, "Manager Sign Up");
                    //CardLayout layout = (CardLayout) mainPanel.getLayout();
                    //layout.show(mainPanel, "SignUp");
                    return;
                }
            }
        });
        add(signUpButton, gbc);
    }
}
