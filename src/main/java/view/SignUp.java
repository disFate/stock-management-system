package view;

import controller.UserController;
import model.Entity.User;
import session.CurrentUser;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import model.Entity.User.Role;

public class SignUp extends JPanel {
    private JTextField userIdField;
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;

    public SignUp(JPanel mainPanel, UserController userController, Boolean isManager) {

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);

        //gbc.gridx = 0;
        //gbc.gridy = 0;
        //add(new JLabel("User ID:"), gbc);

        //gbc.gridx = 1;
        //gbc.gridy = 0;
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        //userIdField = new JTextField(15);
        //add(userIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        nameField = new JTextField(15);
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        emailField = new JTextField(15);
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> {
            signUp(mainPanel, userController, isManager);
        });
        add(signUpButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            nameField.setText("");
            emailField.setText("");
            passwordField.setText("");
            CardLayout layout = (CardLayout) mainPanel.getLayout();
            layout.show(mainPanel, "Customer Login");
        });
        add(backButton, gbc);
    }

    private void signUp(JPanel mainPanel, UserController userController, Boolean isManager) {
        //String userId = userIdField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        // Check if user already exists
        if(isManager && userController.managerCheck()){
            JOptionPane.showMessageDialog(this, "Manager already exists");
            return;
        }
        if (userController.getUserInfo(email) != null) {
            JOptionPane.showMessageDialog(this, "User already exists");
            return;
        }

        // Create new user
        Role r = null;
        if(isManager){
            r = Role.MANAGER;
        }else{
            r = Role.CUSTOMER;
        }
        User user = new User(-1, name, email, password, r, User.Approved.REGISTERED, BigDecimal.ZERO, BigDecimal.ZERO);

        // Add user to database
        userController.addUser(user);

        // Set current user
        CurrentUser.setCurrentUser(user);

        // Create a UserProfile panel with the logged-in user and add it to the mainPanel
        //UserProfile userProfile = new UserProfile(mainPanel,user);
        //mainPanel.add(userProfile, "UserProfile");

        // Switch to the UserProfile screen
        //CardLayout layout = (CardLayout) mainPanel.getLayout();
        //layout.show(mainPanel, "Customer Login");

        CardLayout layout = (CardLayout) mainPanel.getLayout();
        layout.show(mainPanel, "Home Page");
    }
}
