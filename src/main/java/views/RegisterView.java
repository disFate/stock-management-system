//package views;
//
///**
// * @Author: Tsuna
// * @Date: 2023-04-13-17:05
// * @Description:
// */
//import javax.swing.*;
//import java.awt.*;
//
//public class RegisterView extends BaseView {
//    private JTextField nameField;
//    private JTextField emailField;
//    private JPasswordField passwordField;
//    private JButton registerButton;
//
//    public RegisterView() {
//        setupWindow("Register");
//
//        JPanel formPanel = createFormPanel(4, 2, 5, 5);
//        formPanel.add(new JLabel("Name:"));
//        nameField = new JTextField();
//        formPanel.add(nameField);
//        formPanel.add(new JLabel("Email:"));
//        emailField = new JTextField();
//        formPanel.add(emailField);
//        formPanel.add(new JLabel("Password:"));
//        passwordField = new JPasswordField();
//        formPanel.add(passwordField);
//
//        registerButton = new JButton("Register");
//        formPanel.add(registerButton);
//
//        add(formPanel, BorderLayout.CENTER);
//        //pack();
//    }
//
//    // Add other methods and event listeners as needed
//    public static void main(String[] args) {
//        RegisterView view = new RegisterView();
//    }
//}
