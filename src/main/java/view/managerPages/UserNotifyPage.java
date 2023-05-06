package view.managerPages;

import controller.UserController;
import dao.impl.MessageDAOImpl;
import dao.impl.StockDAOImpl;
import dao.impl.TransactionDAOImpl;
import dao.impl.UserDAOImpl;
import dao.impl.MessageDAOImpl;
import model.DTO.UserStockInfo;
import model.Entity.Message;
import model.Entity.User;
import controller.MessageController;
import session.CurrentUser;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class UserNotifyPage extends JFrame {
    List<User> registeredUsers;
    private JPanel mainPanel;
    private JTable userTable;
    private JButton backButton;
    private JButton notifyButton;
    List<UserStockInfo> userStockInfos;


    private JTextField message;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;


    public UserNotifyPage(UserController userController, MessageController messageController) {
        setTitle("Users");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        setContentPane(mainPanel);
        mainPanel.setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Name", "Email", "Current Balance"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);

        userTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);




        List<User> registeredUsers = userController.getRegisteredUsers();


        for (User user : registeredUsers) {
            Object[] rowData = {user.getId(), user.getName(), user.getEmail(), user.getBalance()};
            tableModel.addRow(rowData);
        }

        scrollPane = new JScrollPane(userTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        backButton = new JButton("back");
        bottomPanel.add(backButton, BorderLayout.WEST);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                ManagerFirstPage managerFirstPage = new ManagerFirstPage( new UserController(new UserDAOImpl(), new TransactionDAOImpl(), new StockDAOImpl()));
                managerFirstPage.setVisible(true);
                setVisible(false);
            }

        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        notifyButton = new JButton("notify");




        JButton selectAllButton = new JButton("Select All");
        selectAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userTable.selectAll();
            }
        });

        JButton showAll = new JButton("Show All Registered Users");
        showAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the list of derivative users from the user controller
                List<User> registeredUsers = userController.getRegisteredUsers();

                // Remove all rows from the table
                tableModel.setRowCount(0);

                // Add the derivative users to the table
                for (User user : registeredUsers) {
                    Object[] rowData = {user.getId(), user.getName(), user.getEmail(), user.getBalance()};
                    tableModel.addRow(rowData);
                }
            }
        });

        JButton viewButton = new JButton("view");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(mainPanel, "Please select a user to view.");
                } else if (userTable.getSelectedRowCount() > 1) {
                    JOptionPane.showMessageDialog(mainPanel, "Please select only one user to view.");
                } else {
                    int userID = (int)tableModel.getValueAt(selectedRow, 0);
                    User selectedUser = userController.getUserInfo(userID);
                    JFrame viewFrame = new JFrame();
                    viewFrame.setTitle("View User");
                    viewFrame.setSize(400, 300);
                    viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    viewFrame.setLocationRelativeTo(null);

                    JPanel viewPanel = new JPanel(new GridBagLayout());

                    GridBagConstraints c = new GridBagConstraints();
                    c.anchor = GridBagConstraints.LINE_END;
                    c.insets = new Insets(5, 5, 5, 5);

                    JLabel idLabel = new JLabel("ID:");
                    c.gridx = 0;
                    c.gridy = 0;
                    viewPanel.add(idLabel, c);

                    JTextField idField = new JTextField();
                    idField.setEditable(false);
                    idField.setText(String.valueOf(selectedUser.getId()));
                    c.gridx = 1;
                    c.gridy = 0;
                    viewPanel.add(idField, c);

                    JLabel nameLabel = new JLabel("Name:");
                    c.gridx = 0;
                    c.gridy = 1;
                    viewPanel.add(nameLabel, c);

                    JTextField nameField = new JTextField();
                    nameField.setEditable(false);
                    nameField.setText(selectedUser.getName());
                    c.gridx = 1;
                    c.gridy = 1;
                    viewPanel.add(nameField, c);

                    JLabel emailLabel = new JLabel("Email:");
                    c.gridx = 0;
                    c.gridy = 2;
                    viewPanel.add(emailLabel, c);

                    JTextField emailField = new JTextField();
                    emailField.setEditable(false);
                    emailField.setText(selectedUser.getEmail());
                    c.gridx = 1;
                    c.gridy = 2;
                    viewPanel.add(emailField, c);

                    JLabel balanceLabel = new JLabel("Balance:");
                    c.gridx = 0;
                    c.gridy = 3;
                    viewPanel.add(balanceLabel, c);

                    JTextField balanceField = new JTextField();
                    balanceField.setEditable(false);
                    balanceField.setText(selectedUser.getBalance().toString());
                    c.gridx = 1;
                    c.gridy = 3;
                    viewPanel.add(balanceField, c);

                    JLabel profitLabel = new JLabel("Realized Profit:");
                    c.gridx = 0;
                    c.gridy = 4;
                    viewPanel.add(profitLabel, c);

                    JTextField profitField = new JTextField();
                    profitField.setEditable(false);
                    profitField.setText(selectedUser.getRealizedProfit().toString());
                    c.gridx = 1;
                    c.gridy = 4;
                    viewPanel.add(profitField, c);

                    JButton blockButton = new JButton("Block User");
                    c.gridx = 0;
                    c.gridy = 5;
                    c.gridwidth = 2;
                    c.anchor = GridBagConstraints.CENTER;
                    viewPanel.add(blockButton, c);

                    JButton viewUnrealizedProfitsButton = new JButton("View Unrealized Profits");
                    c.gridx = 0;
                    c.gridy = 6;
                    c.gridwidth = 2;
                    c.anchor = GridBagConstraints.CENTER;
                    viewPanel.add(viewUnrealizedProfitsButton, c);

                    viewUnrealizedProfitsButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int selectedRow = userTable.getSelectedRow();
                            if (selectedRow == -1) {
                                JOptionPane.showMessageDialog(mainPanel, "Please select a user to view.");
                            } else {
                                int userID = (int)tableModel.getValueAt(selectedRow, 0);
                                List<UserStockInfo> userStockInfos = userController.getUnrealizedProfit(userID);
                                if (userStockInfos.isEmpty()) {
                                    JOptionPane.showMessageDialog(mainPanel, "This user does not have any purchased stocks");
                                } else {
                                    JFrame profitsFrame = new JFrame();
                                    profitsFrame.setTitle("Unrealized Profits");
                                    profitsFrame.setSize(600, 400);
                                    profitsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                    profitsFrame.setLocationRelativeTo(null);

                                    JPanel profitsPanel = new JPanel(new BorderLayout());

                                    JTable profitsTable = new JTable(new DefaultTableModel(
                                            new Object[][] {},
                                            new Object[] {"Symbol", "Name", "Avg. Cost", "Quantity", "Price", "Unrealized Profit"}
                                    ));

                                    for (UserStockInfo userStockInfo : userStockInfos) {
                                        Object[] rowData = {
                                                userStockInfo.getStockSymbol(),
                                                userStockInfo.getStockName(),
                                                userStockInfo.getAverageCost(),
                                                userStockInfo.getQuantity(),
                                                userStockInfo.getPrice(),
                                                userStockInfo.getUnrealizedProfit()
                                        };
                                        ((DefaultTableModel)profitsTable.getModel()).addRow(rowData);
                                    }

                                    profitsPanel.add(new JScrollPane(profitsTable), BorderLayout.CENTER);

                                    profitsFrame.getContentPane().add(profitsPanel);

                                    profitsFrame.setVisible(true);
                                }
                            }
                        }
                    });


                    blockButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int selectedUserId = selectedUser.getId();
                            userController.updateUserDenied(userID);
                            tableModel.removeRow(selectedRow);
                            viewFrame.dispose();
                        }
                    });

                    viewFrame.getContentPane().add(viewPanel);

                    viewFrame.setVisible(true);
                }
            }
        });



        JButton showDerivativeButton = new JButton("Show Qualified Derivative Traders");
        showDerivativeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the list of derivative users from the user controller
                List<User> derivativeUsers = userController.getDerivativeUsers();

                // Remove all rows from the table
                tableModel.setRowCount(0);

                // Add the derivative users to the table
                for (User user : derivativeUsers) {
                    Object[] rowData = {user.getId(), user.getName(), user.getEmail(), user.getBalance()};
                    tableModel.addRow(rowData);
                }
            }
        });
        topPanel.add(showAll);
        topPanel.add(showDerivativeButton);
        buttonPanel.add(selectAllButton);
        buttonPanel.add(notifyButton);
        buttonPanel.add(viewButton);


        notifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = userTable.getSelectedRows();
                if (selectedRows.length == 0) {
                    JOptionPane.showMessageDialog(mainPanel, "Please select at least one user to notify.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int row : selectedRows) {
                        sb.append(tableModel.getValueAt(row, 1));
                        sb.append(", ");
                    }
                    sb.delete(sb.length() - 2, sb.length()); // remove the trailing comma and space
                    String recipients = sb.toString();

                    JFrame messageFrame = new JFrame();
                    messageFrame.setTitle("New Message");
                    messageFrame.setSize(400, 300);
                    messageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    messageFrame.setLocationRelativeTo(null);

                    JPanel messagePanel = new JPanel(new BorderLayout());
                    messageFrame.setContentPane(messagePanel);

                    JLabel recipientLabel = new JLabel("Send message to: " + recipients);
                    messagePanel.add(recipientLabel, BorderLayout.NORTH);

                    JTextArea messageTextArea = new JTextArea();
                    JScrollPane messageScrollPane = new JScrollPane(messageTextArea);
                    messagePanel.add(messageScrollPane, BorderLayout.CENTER);

                    JButton sendButton = new JButton("Send");
                    sendButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String message = messageTextArea.getText();
                            for (int row : selectedRows) {
                                int userID = (int)tableModel.getValueAt(row, 0);
                                messageController.createMessage(userID, message);
                            }
                            messageFrame.dispose();
                        }
                    });
                    messagePanel.add(sendButton, BorderLayout.SOUTH);

                    messageFrame.setVisible(true);
                }
            }
        });


    }

    public void loadData(UserController userController) {

        userStockInfos = userController.getUnrealizedProfit(CurrentUser.getCurrentUser().getId());
        for (UserStockInfo userStockInfo : userStockInfos) {
            Object[] rowData = {
                    userStockInfo.getStockSymbol(),
                    userStockInfo.getStockName(),
                    userStockInfo.getAverageCost(),
                    userStockInfo.getQuantity(),
                    userStockInfo.getPrice(),
                    userStockInfo.getUnrealizedProfit()
            };

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserController userController = new UserController(new UserDAOImpl(), new TransactionDAOImpl(), new StockDAOImpl());

            try {
                MessageController messageController = new MessageController(new MessageDAOImpl());
                new UserNotifyPage(userController, messageController).setVisible(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        });
    }


}