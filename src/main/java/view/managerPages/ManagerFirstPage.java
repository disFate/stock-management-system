package view.managerPages;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.*;

import controller.MessageController;
import controller.StockController;
import controller.UserController;
import dao.impl.MessageDAOImpl;
import dao.impl.StockDAOImpl;
import dao.impl.TransactionDAOImpl;
import dao.impl.UserDAOImpl;

public class ManagerFirstPage extends JFrame {

    private JButton stocksButton;
    private JButton usersButton;
    private JButton notificationButton;

    private JButton backButton;

    public ManagerFirstPage(UserController userController) {
        setTitle("Manager Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setResizable(true);

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 40, 0); // Add a bottom margin of 40 pixels
        add(welcomeLabel, gbc);


        JButton notificationButton = new JButton("User Requests (" + userController.getPendingCount() + ")");
        notificationButton.setPreferredSize(new Dimension(200, 50));

        UserRequestsPage requestPage = new UserRequestsPage(new UserController(new UserDAOImpl(), new TransactionDAOImpl(), new StockDAOImpl()));

        addEditDeleteStocks stocksPage = new addEditDeleteStocks(new StockController(new StockDAOImpl(), new TransactionDAOImpl()));




        notificationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requestPage.setVisible(true);
                setVisible(false);
            }
        });

        JButton stocksButton = new JButton("Stocks Management");
        stocksButton.setPreferredSize(new Dimension(200, 50));
        stocksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stocksPage.setVisible(true);
                setVisible(false);
            }
        });

        JButton usersButton = new JButton("User Management");
        usersButton.setPreferredSize(new Dimension(200, 50));

        usersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MessageController messageController = new MessageController(new MessageDAOImpl());
                    UserNotifyPage userPage = new UserNotifyPage(new UserController(new UserDAOImpl(), new TransactionDAOImpl(), new StockDAOImpl()), new MessageController(new MessageDAOImpl()));
                    userPage.setVisible(true);
                } catch (SQLException i) {
                    throw new RuntimeException(i);
                }
                setVisible(false);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(notificationButton, gbc);

        gbc.gridy = 2;
        add(stocksButton, gbc);

        gbc.gridy = 3;
        add(usersButton, gbc);

        getContentPane().setBackground(new Color(235, 235, 235));


        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        System.out.println("Running page");
        ManagerFirstPage managerFirstPage = new ManagerFirstPage( new UserController(new UserDAOImpl(), new TransactionDAOImpl(), new StockDAOImpl()));
        managerFirstPage.setVisible(true);
    }
}