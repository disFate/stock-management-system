package views;

import javax.swing.*;
import java.awt.*;

public class PortfolioView extends BaseView {
    private JTable portfolioTable;
    private JButton refreshButton;

    public PortfolioView() {
        setupWindow("Portfolio");

        portfolioTable = new JTable(); // Configure table model as needed
        JScrollPane scrollPane = new JScrollPane(portfolioTable);
        add(scrollPane, BorderLayout.CENTER);

        refreshButton = new JButton("Refresh");
        add(refreshButton, BorderLayout.SOUTH);

        //pack();
    }

    // Add other methods and event listeners as needed
}
