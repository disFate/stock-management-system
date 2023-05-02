package view.userPages;

/**
 * @Author: Tsuna
 * @Date: 2023-04-21-15:26
 * @Description:
 */

import common.Response;
import controller.StockController;
import controller.UserController;
import model.Entity.Stock;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class StockDisplayPage extends JFrame {

    List<Stock> stocks;
    private JPanel mainPanel;
    private JTable stockTable;
    private JButton backButton;
    private JButton refreshButton;
    private JButton buyButton;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;

    {

    }

    public StockDisplayPage(StockController stockController, UserController userController, UserMenuPage userMenuPage) {
        setTitle("market stocks");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        setContentPane(mainPanel);
        mainPanel.setLayout(new BorderLayout());

        String[] columnNames = {"symbol", "company", "price", "amount"};
        tableModel = new DefaultTableModel(columnNames, 0);
        stockTable = new JTable(tableModel);
        stockTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //loadData(stockController);

        scrollPane = new JScrollPane(stockTable);
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
        buyButton = new JButton("buy");
        rightPanel.add(buyButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userMenuPage.setVisible(true);
                setVisible(false);
            }
        });
        // Add ActionListener to buyButton
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = stockTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Perform buy operation with the selected stock
                    //todo pop up window to select quantity
                    int quantity = 1;
                    Stock stock = stocks.stream().filter(s -> s.getSymbol().equals(stockTable.
                            getValueAt(selectedRow, 0))).findFirst().orElse(null);
                    try {
                        Response res = userController.buyStock(stock.getId(), quantity);
                        if (res.isSuccess() == true) {
                            stock.setAmount(stock.getAmount() - quantity);
                            tableModel.setValueAt(stock.getAmount(), selectedRow, 3);
                            tableModel.fireTableRowsUpdated(selectedRow, selectedRow);
//                            Thread thread = new Thread(() -> {
//                                userMenuPage.notifyUpdate(1, stockController);
//                            });
//                            thread.start();
                        } else {
                            System.out.println(res.getMessage());
                        }
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
                loadData(stockController);
            }
        });
    }

    public void loadData(StockController stockController) {
        tableModel.setRowCount(0);
        stocks = stockController.getAllStocks();
        for (Stock stock : stocks) {
            tableModel.addRow(new Object[]{stock.getSymbol(), stock.getName(), stock.getPrice(), stock.getAmount()});
        }
    }
}

