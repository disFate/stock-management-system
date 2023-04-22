package view;

/**
 * @Author: Tsuna
 * @Date: 2023-04-21-15:28
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

public class UserStockPage extends JFrame {

    private JPanel mainPanel;
    private JTable userStockTable;
    private JButton backButton;
    private JButton sellButton;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;

    public UserStockPage(StockController stockController) {
        setTitle("user stocks");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        // todo Add ActionListener to backButton

        // Add ActionListener to sellButton
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userStockTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Perform sell operation with the selected stock
                    String symbol = (String) userStockTable.getValueAt(selectedRow, 0);
                    int quantity = 1;// 获取出售数量，可以通过弹出窗口或其他方式让用户输入
                    stockController.sellStock(symbol, quantity);
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
            new UserStockPage(stockController).setVisible(true);
        });
    }
}
