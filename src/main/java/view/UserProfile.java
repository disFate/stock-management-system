package view;

import model.Entity.User;
import javax.swing.*;
import java.awt.*;

public class UserProfile extends JPanel {
    private User user;

    public UserProfile(JPanel mainPanel, User user) {
        this.user = user;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("User ID:"), gbc);
        gbc.gridx = 1;
        add(new JLabel(String.valueOf(user.getId())), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        add(new JLabel(user.getName()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        add(new JLabel(user.getEmail()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        add(new JLabel(user.getRole().toString()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Account Approval:"), gbc);
        gbc.gridx = 1;
        add(new JLabel(user.isApproved().toString()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Account Balance:"), gbc);
        gbc.gridx = 1;
        add(new JLabel(user.getBalance().toString()), gbc);

        // Add a back button or other navigation options as needed
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            CardLayout layout = (CardLayout) mainPanel.getLayout();
            layout.show(mainPanel, "CustomerLogin");

            // Remove the current user profile panel from the main panel
            mainPanel.remove(this);
        });
        add(backButton, gbc);
    }
}
