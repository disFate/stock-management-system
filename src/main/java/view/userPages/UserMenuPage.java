package view.userPages;

import controller.MessageController;
import controller.StockController;
import controller.UserController;
import dao.impl.MessageDAOImpl;
import dao.impl.StockDAOImpl;
import dao.impl.TransactionDAOImpl;
import dao.impl.UserDAOImpl;
import model.Entity.Message;
import model.Entity.User;
import session.CurrentUser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class UserMenuPage extends JFrame {
    private StockDisplayPage stockPage;//0
    private UserStockPage userStockPage;//1
    private ManageAccountPage manageAccountPage;

    public UserMenuPage(StockController stockController, UserController userController, MessageController messageController) {
        stockPage = new StockDisplayPage(stockController, userController, this);
        userStockPage = new UserStockPage(stockController, userController, this);
        manageAccountPage = new ManageAccountPage(userController, this);

        setTitle("Customer Stock Trading System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(800, 600);
        setResizable(false);

        GridBagConstraints gbc = new GridBagConstraints();

        User currentUser = CurrentUser.getCurrentUser();
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getName());
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
                stockPage.loadData(stockController);
                setVisible(false);
            }
        });

        JButton sellButton = new JButton("Sell Stocks");
        sellButton.setPreferredSize(new Dimension(200, 50));
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!CurrentUser.getCurrentUser().getApproved().equals(User.Approved.APPROVED)) {
                    JOptionPane.showMessageDialog(null, "only trading account can buy and sell stocks", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                userStockPage.setVisible(true);
                userStockPage.loadData(userController);
                setVisible(false);
            }
        });

        JButton manageAccountButton = new JButton("Manage Account");
        manageAccountButton.setPreferredSize(new Dimension(200, 50));
        manageAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manageAccountPage.setVisible(true);
                manageAccountPage.loadData(userController);
                setVisible(false);
            }
        });

        JButton messagesButton = new JButton("Messages");
        messagesButton.setPreferredSize(new Dimension(200, 50));
        messagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMessagesDialog(messageController, messagesButton);
            }
        });
        Thread thread = new Thread();
        updateMessageButton(messageController, messagesButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(buyButton, gbc);

        gbc.gridy = 3;
        add(sellButton, gbc);

        gbc.gridy = 4;
        add(manageAccountButton, gbc);
        gbc.gridy = 1;
        add(messagesButton, gbc);

        getContentPane().setBackground(new Color(235, 235, 235));
        setLocationRelativeTo(null);
    }

    private void showMessagesDialog(MessageController messageController, JButton messagesButton) {
        JDialog messagesDialog = new JDialog(this, "User Messages", true);
        messagesDialog.setSize(800, 600);
        messagesDialog.setLocationRelativeTo(this);

        String[] columnNames = {"Message", "Date", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable messagesTable = new JTable(tableModel);
        messagesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Load messages from the UserController
        List<Message> messages = messageController.getAllMessagesByUserId(CurrentUser.getCurrentUser().getId());
        for (Message message : messages) {
            tableModel.addRow(new Object[]{message.getMessage(), message.getMessageDate(), message.isRead() == true ? "read" : "unread"});
        }

        JScrollPane scrollPane = new JScrollPane(messagesTable);
        messagesDialog.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messagesDialog.dispose();
            }
        });

        JButton readButton = new JButton("Mark as Read");

        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = messagesTable.getSelectedRow();
                if (selectedRow != -1) {
                    Message selectedMessage = messages.get(selectedRow);
                    if (!selectedMessage.isRead()) {
                        messageController.markMessageAsRead(selectedMessage.getId());
                        tableModel.setValueAt("read", selectedRow, 2);
                        updateMessageButton(messageController, messagesButton); // Update message count on the message button
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a message to mark as read.");
                }
            }
        });
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(readButton);
        bottomPanel.add(closeButton);
        messagesDialog.add(bottomPanel, BorderLayout.SOUTH);
        messagesDialog.setVisible(true);
    }

    public void updateMessageButton(MessageController messageController, JButton messageButton) {
        int unreadMessagesCount = messageController.getUnreadMessagesByUserId(CurrentUser.getCurrentUser().getId()).size();
        if (unreadMessagesCount > 0) {
            messageButton.setText("Messages (" + unreadMessagesCount + ")");
        } else {
            messageButton.setText("Messages");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UserMenuPage userMenuPage = null;
                try {
                    userMenuPage = new UserMenuPage(new StockController(new StockDAOImpl(), new TransactionDAOImpl()), new UserController(new UserDAOImpl(), new TransactionDAOImpl(), new StockDAOImpl()), new MessageController(new MessageDAOImpl()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                userMenuPage.setVisible(true);
                UserController userController = new UserController(new UserDAOImpl(), new TransactionDAOImpl(), new StockDAOImpl());
            }
        });
    }

}
