package view;

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

public class addEditDeleteStocks extends JFrame{
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
                   //perform edit
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
                    //perform delete
                } else {
                    JOptionPane.showMessageDialog(null, "please select a stock");
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //implement add function
            }
        });





    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IStockDAO stockDAO = new StockDAOImpl();
            StockController stockController = new StockController(stockDAO);
            new addEditDeleteStocks(stockController).setVisible(true);
        });
    }
}