package view;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {

    public WelcomePanel(JPanel mainPanel) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel welcomeLabel = new JLabel("Welcome to the Portfolio Stock System!");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(welcomeLabel, gbc);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints buttonsGbc = new GridBagConstraints();

        JButton managerButton = new JButton("Manager");
        managerButton.addActionListener(e -> {
            CardLayout layout = (CardLayout) mainPanel.getLayout();
            layout.show(mainPanel, "Manager Login");
        });
        buttonsGbc.gridx = 0;
        buttonsGbc.gridy = 0;
        buttonsGbc.weightx = 0.5;
        buttonsGbc.weighty = 1;
        buttonsGbc.insets = new Insets(0, 0, 0, 10);
        buttonsGbc.anchor = GridBagConstraints.EAST;
        buttonsPanel.add(managerButton, buttonsGbc);

        JButton customerButton = new JButton("Customer");
        customerButton.addActionListener(e -> {
            CardLayout layout = (CardLayout) mainPanel.getLayout();
            layout.show(mainPanel, "Customer Login");
        });
        buttonsGbc.gridx = 1;
        buttonsGbc.gridy = 0;
        buttonsGbc.weightx = 0.5;
        buttonsGbc.weighty = 1;
        buttonsGbc.insets = new Insets(0, 10, 0, 0);
        buttonsGbc.anchor = GridBagConstraints.WEST;
        buttonsPanel.add(customerButton, buttonsGbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonsPanel, gbc);
    }
}
