package view;

/**
 * @Author: Tsuna
 * @Date: 2023-04-21-15:26
 * @Description:
 */
import controller.StockController;
import dao.IStockDAO;
import dao.impl.StockDAOImpl;
import model.Stock;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StockDisplayPage extends JFrame {

    private JPanel mainPanel;
    private JTable stockTable;
    private JButton backButton;
    private JButton buyButton;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;

    public StockDisplayPage(StockController stockController) {
        setTitle("market stocks");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        setContentPane(mainPanel);
        mainPanel.setLayout(new BorderLayout());

        String[] columnNames = {"symbol", "company", "price", "amount"};
        tableModel = new DefaultTableModel(columnNames, 0);
        stockTable = new JTable(tableModel);
        stockTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        List<Stock> stocks = stockController.getAllStocks();
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

        // todo Add ActionListener to backButton

        // Add ActionListener to buyButton
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = stockTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Perform buy operation with the selected stock
                    int stockId = (int) stockTable.getValueAt(selectedRow, 0);
                    int quantity = 1;// 获取购买数量，可以通过弹出窗口或其他方式让用户输入
                    stockController.buyStock(stockId, quantity);
                } else {
                    JOptionPane.showMessageDialog(null, "please select a stock");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IStockDAO stockDAO = new StockDAOImpl();
            StockController stockController = new StockController(stockDAO);
            new StockDisplayPage(stockController).setVisible(true);
        });
    }
}

