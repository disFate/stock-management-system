package view;

import controller.StockController;
import dao.impl.StockDAOImpl;
import model.User;
import session.CurrentUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserMenuPage extends JFrame {
    private StockDisplayPage stockPage;
    private UserStockPage userStockPage;

    public UserMenuPage(StockController stockController) {
        stockPage = new StockDisplayPage(stockController, this);
        userStockPage = new UserStockPage(stockController, this);

        setTitle("Customer Stock Trading System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(800, 600);
        setResizable(false);

        GridBagConstraints gbc = new GridBagConstraints();

        User currentUser = new CurrentUser().getCurrentUser();
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getUsername());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 40, 0); // Add a bottom margin of 40 pixels
        add(welcomeLabel, gbc);


        JButton buyButton = new JButton("Buy Stocks");
        buyButton.setPreferredSize(new Dimension(200, 50));
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stockPage.setVisible(true);
                setVisible(false);
            }
        });

        JButton sellButton = new JButton("Sell Stocks");
        sellButton.setPreferredSize(new Dimension(200, 50));
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userStockPage.setVisible(true);
                setVisible(false);
            }
        });

        JButton manageAccountButton = new JButton("Manage Account");
        manageAccountButton.setPreferredSize(new Dimension(200, 50));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(buyButton, gbc);

        gbc.gridy = 2;
        add(sellButton, gbc);

        gbc.gridy = 3;
        add(manageAccountButton, gbc);

        getContentPane().setBackground(new Color(235, 235, 235));


        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                UserMenuPage userMenuPage = new UserMenuPage(new StockController(new StockDAOImpl()));
                userMenuPage.setVisible(true);
            }
        });
    }
}
