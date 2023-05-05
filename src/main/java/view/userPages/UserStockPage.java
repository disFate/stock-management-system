package view.userPages;

import common.Response;
import controller.StockController;
import controller.UserController;
import model.DTO.UserStockInfo;
import session.CurrentUser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class UserStockPage extends JFrame {

    List<UserStockInfo> userStockInfos;
    private JPanel mainPanel;
    private JTable userStockTable;
    private JButton backButton;
    private JButton refreshButton;
    private JButton sellButton;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;

    public UserStockPage(StockController stockController, UserController userController, UserMenuPage userMenuPage) {
        setTitle("user stocks");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        setContentPane(mainPanel);
        mainPanel.setLayout(new BorderLayout());

        String[] columnNames = {"symbol", "company", "averageCost", "quantity", "price", "unrealizedProfit"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userStockTable = new JTable(tableModel);
        userStockTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //loadData(userController);

        scrollPane = new JScrollPane(userStockTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        backButton = new JButton("back");
        leftPanel.add(backButton);
        refreshButton = new JButton("refresh");
        rightPanel.add(refreshButton);
        sellButton = new JButton("sell");
        rightPanel.add(sellButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userMenuPage.setVisible(true);
                setVisible(false);
            }
        });

        // Add ActionListener to sellButton
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userStockTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Perform sell operation with the selected stock
                    UserStockInfo userStockInfo = userStockInfos.get(selectedRow);
                    String input = JOptionPane.showInputDialog(null, "Enter the quantity you want to sell:", "Sell Stock", JOptionPane.QUESTION_MESSAGE);
                    //todo a pop up window to select quantity
                    int quantity = Integer.parseInt(input);

                    try {
                        Response res = userController.sellStock(userStockInfo.getStockId(), quantity);
                        if (res.isSuccess()) {
                            UserStockInfo updatedUserStockInfo = userController.getUserStockInfo(userStockInfo.getUserId(), userStockInfo.getStockId());
                            tableModel.removeRow(selectedRow);
                            if (updatedUserStockInfo != null) {
                                updateRow(selectedRow, updatedUserStockInfo);
                            }
                            tableModel.fireTableRowsUpdated(selectedRow, selectedRow);
                        }
                        JOptionPane.showMessageDialog(null, res.getMessage());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "please select a stock");
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData(userController);
            }
        });
    }

    public void updateRow(int selectedRow, UserStockInfo updatedUserStockInfo) {
        Object[] updatedRowData = {
                updatedUserStockInfo.getStockSymbol(),
                updatedUserStockInfo.getStockName(),
                updatedUserStockInfo.getAverageCost(),
                updatedUserStockInfo.getQuantity(),
                updatedUserStockInfo.getPrice(),
                updatedUserStockInfo.getUnrealizedProfit()
        };
        tableModel.insertRow(selectedRow, updatedRowData);
    }

    public void loadData(UserController userController) {
        tableModel.setRowCount(0);
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
            tableModel.addRow(rowData);
        }
    }

}
