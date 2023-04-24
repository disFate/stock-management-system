package view.userPages;

/**
 * @Author: Tsuna
 * @Date: 2023-04-21-15:28
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

public class UserStockPage extends JFrame {

    private JPanel mainPanel;
    private JTable userStockTable;
    private JButton backButton;
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

        String[] columnNames = {"symbol", "company", "price", "quantity"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userStockTable = new JTable(tableModel);
        userStockTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        List<Stock> userStocks = stockController.getUserStocks();
        for (Stock stock : userStocks) {
            Object[] rowData = {stock.getSymbol(), stock.getName(), stock.getPrice(), stock.getAmount()};
            tableModel.addRow(rowData);
        }

        scrollPane = new JScrollPane(userStockTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        backButton = new JButton("back");
        bottomPanel.add(backButton, BorderLayout.WEST);

        sellButton = new JButton("sell");
        bottomPanel.add(sellButton, BorderLayout.EAST);

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
                    Stock stock = userStocks.stream().filter(s -> s.getSymbol().equals(userStockTable.
                            getValueAt(selectedRow, 0))).findFirst().orElse(null);
                    //todo a pop up window to select quantity
                    int quantity = 1;
                    try {
                        userController.sellStock(stock, quantity);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    loadData(stockController);
                } else {
                    JOptionPane.showMessageDialog(null, "please select a stock");
                }
            }
        });
    }

    public void loadData(StockController stockController) {
        // 清除表格中的现有数据
        tableModel.setRowCount(0);

        // 从数据库或其他数据源中加载新数据
        List<Stock> userStocks = stockController.getUserStocks();

        // 将新数据添加到表格模型中
        for (Stock stock : userStocks) {
            Object[] rowData = {stock.getSymbol(), stock.getName(), stock.getPrice(), stock.getAmount()};
            tableModel.addRow(rowData);
        }
    }
}
