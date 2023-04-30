package view.managerPages;

import controller.StockController;
import dao.IStockDAO;
import dao.ITransactionDAO;
import dao.impl.StockDAOImpl;
import dao.impl.TransactionDAOImpl;
import model.Entity.Stock;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

public class addEditDeleteStocks extends JFrame {
    private JPanel mainPanel;
    private JTable stockTable;
    private JButton backButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;

    public addEditDeleteStocks(StockController stockController) {

        setTitle("All Stocks");
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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        addButton = new JButton("add");
        editButton = new JButton("edit");
        deleteButton = new JButton("delete");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = stockTable.getSelectedRow();
                if (selectedRow != -1) {
                    // get the current values of the selected row
                    String oldName = (String) tableModel.getValueAt(selectedRow, 1);
                    String symbol = (String) tableModel.getValueAt(selectedRow, 0);

                    BigDecimal bigDprice = (BigDecimal) tableModel.getValueAt(selectedRow, 2);
                    double price = bigDprice.doubleValue();
                    int amount = (int) tableModel.getValueAt(selectedRow, 3);

                    // create the edit stock panel
                    JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
                    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    JLabel symbolLabel = new JLabel("Symbol:");
                    JTextField symbolField = new JTextField(symbol);
                    JLabel companyLabel = new JLabel("Company:");
                    JTextField companyField = new JTextField(oldName);
                    JLabel priceLabel = new JLabel("Price:");
                    JTextField priceField = new JTextField(Double.toString(price));
                    JLabel amountLabel = new JLabel("Amount:");
                    JTextField amountField = new JTextField(Integer.toString(amount));

                    panel.add(symbolLabel);
                    panel.add(symbolField);
                    panel.add(companyLabel);
                    panel.add(companyField);
                    panel.add(priceLabel);
                    panel.add(priceField);
                    panel.add(amountLabel);
                    panel.add(amountField);

                    int result = JOptionPane.showConfirmDialog(null, panel, "Edit Stock",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        // get the values entered by the user
                        String newSymbol = symbolField.getText();
                        String newCompany = companyField.getText();
                        double newPrice = Double.parseDouble(priceField.getText());
                        int newAmount = Integer.parseInt(amountField.getText());

                        // call the editStock method in stockController
                        stockController.editStock(oldName, newSymbol, newCompany, newPrice, newAmount);

                        // update the tableModel with the new stock data
                        Object[] rowData = {newSymbol, newCompany, newPrice, newAmount};
                        tableModel.setValueAt(newSymbol, selectedRow, 0);
                        tableModel.setValueAt(newCompany, selectedRow, 1);
                        tableModel.setValueAt(newPrice, selectedRow, 2);
                        tableModel.setValueAt(newAmount, selectedRow, 3);
                        tableModel.fireTableDataChanged(); // force the JTable to redraw itself
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "please select a stock");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = stockTable.getSelectedRow();
                if (selectedRow != -1) {
                    stockController.deleteStock((String) stockTable.getModel().getValueAt(selectedRow, 1));
                    tableModel.removeRow(selectedRow); // remove the row from the table model
                    tableModel.fireTableDataChanged(); // notify the table that its data has changed

                } else {
                    JOptionPane.showMessageDialog(null, "please select a stock");
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
                panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel symbolLabel = new JLabel("Symbol:");
                JTextField symbolField = new JTextField();
                JLabel companyLabel = new JLabel("Company:");
                JTextField companyField = new JTextField();
                JLabel priceLabel = new JLabel("Price:");
                JTextField priceField = new JTextField();
                JLabel amountLabel = new JLabel("Amount:");
                JTextField amountField = new JTextField();

                panel.add(symbolLabel);
                panel.add(symbolField);
                panel.add(companyLabel);
                panel.add(companyField);
                panel.add(priceLabel);
                panel.add(priceField);
                panel.add(amountLabel);
                panel.add(amountField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Add Stock",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    // get the values entered by the user

                    String symbol = symbolField.getText();
                    String company = companyField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int amount = Integer.parseInt(amountField.getText());
                    stockController.addStock(symbol, company, price, amount);
                    Object[] rowData = {symbol, company, price, amount};
                    tableModel.addRow(rowData); // update the tableModel with the new stock data
                    tableModel.fireTableDataChanged(); // force the JTable to redraw itself
                }
            }
        });


    }

    public static void main(String[] args) {
        System.out.println("Running page");
        SwingUtilities.invokeLater(() -> {
            IStockDAO stockDAO = new StockDAOImpl();
            ITransactionDAO transactionDAO = new TransactionDAOImpl();
            StockController stockController = new StockController(stockDAO, transactionDAO);
            new addEditDeleteStocks(stockController).setVisible(true);
        });
    }
}