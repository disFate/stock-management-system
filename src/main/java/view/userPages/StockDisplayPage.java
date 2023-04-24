package view.userPages;

/**
 * @Author: Tsuna
 * @Date: 2023-04-21-15:26
 * @Description:
 */

import controller.StockController;
import controller.UserController;
import model.Stock;

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
    private JButton buyButton;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;

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

        stocks = stockController.getAllStocks();
        for (Stock stock : stocks) {
            Object[] rowData = {stock.getSymbol(), stock.getName(), stock.getPrice(), stock.getAmount()};
            tableModel.addRow(rowData);
        }

        scrollPane = new JScrollPane(stockTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        backButton = new JButton("back");
        bottomPanel.add(backButton, BorderLayout.WEST);

        buyButton = new JButton("buy");
        bottomPanel.add(buyButton, BorderLayout.EAST);

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
                        if (userController.buyStock(stock, quantity).isSuccess() == true) {
                            tableModel.setValueAt(stock.getAmount(), selectedRow, 3);
                            tableModel.fireTableRowsUpdated(selectedRow, selectedRow);
                            Thread thread = new Thread(() -> {
                                userMenuPage.notifyUpdate(1, stockController);
                            });
                            thread.start();
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "please xselect a stock");
                }
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

