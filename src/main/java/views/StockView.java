package views;

/**
 * @Author: Tsuna
 * @Date: 2023-04-13-19:33
 * @Description:
 */
import javax.swing.*;
import java.awt.*;

public class StockView extends BaseView {
    private JTable stockTable;
    private JButton refreshButton;

    public StockView() {
        setupWindow("Stocks");

        stockTable = new JTable(); // Configure table model as needed
        JScrollPane scrollPane = new JScrollPane(stockTable);
        add(scrollPane, BorderLayout.CENTER);

        refreshButton = new JButton("Refresh");
        add(refreshButton, BorderLayout.SOUTH);

        pack();
    }

    // Add other methods and event listeners as needed
}
