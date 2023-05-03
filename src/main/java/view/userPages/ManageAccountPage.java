package view.userPages;

/**
 * @Author: Tsuna
 * @Date: 2023-05-01-16:34
 * @Description:
 */

import controller.UserController;
import model.Entity.User;
import session.CurrentUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageAccountPage extends JFrame {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel roleLabel;
    private JLabel approvedLabel;
    private JLabel balanceLabel;
    private JLabel realizedProfitLabel;
    private JButton backButton;
    private JButton registerButton;
    private JButton profitButton;
    private User currentUser;

    public ManageAccountPage(UserController userController, UserMenuPage userMenuPage) {
        setTitle("Manage Account");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        currentUser = userController.getUserInfo(CurrentUser.getCurrentUser().getId());

        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        Font labelFont = new Font("Arial", Font.PLAIN, 16);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        nameLabel = new JLabel("Name: " + currentUser.getName());
        nameLabel.setFont(labelFont);
        infoPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        emailLabel = new JLabel("Email: " + currentUser.getEmail());
        emailLabel.setFont(labelFont);
        infoPanel.add(emailLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        roleLabel = new JLabel("Role: " + currentUser.getRole().toString());
        roleLabel.setFont(labelFont);
        infoPanel.add(roleLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        approvedLabel = new JLabel("Approved: " + currentUser.getApproved().toString());
        approvedLabel.setFont(labelFont);
        infoPanel.add(approvedLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        balanceLabel = new JLabel("Balance: " + currentUser.getBalance().toString());
        balanceLabel.setFont(labelFont);
        infoPanel.add(balanceLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        realizedProfitLabel = new JLabel("Realized Profit: " + currentUser.getRealizedProfit().toString());
        realizedProfitLabel.setFont(labelFont);
        infoPanel.add(realizedProfitLabel, gbc);


        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        backButton = new JButton("Back");
        leftPanel.add(backButton, BorderLayout.WEST);
        backButton.addActionListener(e -> {
            userMenuPage.setVisible(true);
            this.setVisible(false);
        });

        if (!currentUser.getApproved().equals(User.Approved.APPROVED)) {
            registerButton = new JButton("Upgrade");
            rightPanel.add(registerButton, BorderLayout.EAST);
            registerButton.addActionListener(e -> {
                userController.updateUserPending(currentUser.getId());
            });
        }

        profitButton = new JButton("Profit");
        rightPanel.add(profitButton);
        profitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void loadData(UserController userController) {
        currentUser = userController.getUserInfo(CurrentUser.getCurrentUser().getId());

        nameLabel.setText("Name: " + currentUser.getName());
        emailLabel.setText("Email: " + currentUser.getEmail());
        roleLabel.setText("Role: " + currentUser.getRole().toString());
        approvedLabel.setText("Approved: " + currentUser.getApproved().toString());
        balanceLabel.setText("Balance: " + currentUser.getBalance().toString());
        realizedProfitLabel.setText("Realized Profit: " + currentUser.getRealizedProfit().toString());
    }
}
