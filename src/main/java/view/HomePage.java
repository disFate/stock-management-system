package view;
import javax.swing.*;
import java.awt.*;

import controller.UserController;
import dao.IUserDAO;
import dao.impl.StockDAOImpl;
import dao.impl.TransactionDAOImpl;
import dao.impl.UserDAOImpl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        UserController userController = new UserController(new UserDAOImpl(), new TransactionDAOImpl(), new StockDAOImpl());

        JFrame frame = new JFrame("Home Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel(new CardLayout());

        // Welcome Panel
        JPanel welcomePanel = new WelcomePanel(mainPanel);
        mainPanel.add(welcomePanel, "Welcome");

        // Customer Login Panel
        JPanel customerLoginPanel = new CustomerLogin(mainPanel, userController);
        mainPanel.add(customerLoginPanel, "Customer Login");

        // Manager Login Panel
        JPanel managerLoginPanel = new ManagerLogin(mainPanel, userController);
        mainPanel.add(managerLoginPanel, "Manager Login");

        // Manager Login Panel
        JPanel SignUpPanel = new SignUp(mainPanel, userController);
        mainPanel.add(SignUpPanel, "SignUp");

        frame.getContentPane().add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}



