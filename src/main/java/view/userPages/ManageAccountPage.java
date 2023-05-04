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
import java.math.BigDecimal;

public class ManageAccountPage extends JFrame {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel rightPanel;
    private JPanel leftPanel;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel roleLabel;
    private JLabel approvedLabel;
    private JLabel balanceLabel;
    private JLabel realizedProfitLabel;
    private JButton backButton;
    private JButton registerButton;
    private JButton withDrawButton;
    private JButton depositButton;
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


        bottomPanel = new JPanel(new BorderLayout());
        leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        backButton = new JButton("Back");
        leftPanel.add(backButton, BorderLayout.WEST);
        backButton.addActionListener(e -> {
            userMenuPage.setVisible(true);
            this.setVisible(false);
        });

        depositButton = new JButton("Deposit");
        rightPanel.add(depositButton);
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDepositDialog(true, userController);
            }
        });

        withDrawButton = new JButton("Withdraw");
        rightPanel.add(withDrawButton);
        withDrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDepositDialog(false, userController);
            }
        });

        if (!currentUser.getApproved().equals(User.Approved.APPROVED)) {
            registerButton = new JButton("Upgrade");
            rightPanel.add(registerButton, BorderLayout.EAST);
            registerButton.addActionListener(e -> {
                userController.updateUserPending(currentUser.getId());
                currentUser.setApproved(User.Approved.PENDING);
            });
        }
    }

    private void showDepositDialog(boolean isDeposit, UserController userController) {
        JTextField amountField = new JTextField(10);
        JTextField cardNumberField = new JTextField(16);
        JTextField cvvField = new JTextField(3);
        JTextField expireDateField = new JTextField(5);

        JPanel depositPanel = new JPanel(new GridLayout(5, 2));
        depositPanel.add(new JLabel("Amount:"));
        depositPanel.add(amountField);
        depositPanel.add(new JLabel("Card Number:"));
        depositPanel.add(cardNumberField);
        depositPanel.add(new JLabel("CVV:"));
        depositPanel.add(cvvField);
        depositPanel.add(new JLabel("Expire Date (MM/YY):"));
        depositPanel.add(expireDateField);

        int result = JOptionPane.showConfirmDialog(null, depositPanel, isDeposit ? "Deposit" : "Withdraw",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Process the deposit or withdrawal
            // Get the input values
            String amount = amountField.getText();
            String cardNumber = cardNumberField.getText();
            String cvv = cvvField.getText();
            String expireDate = expireDateField.getText();

            // Perform validation and process the transaction
            if (isDeposit) {
                userController.deposit(CurrentUser.getCurrentUser().getId(), new BigDecimal(amount));
            } else {
                userController.withDraw(CurrentUser.getCurrentUser().getId(), new BigDecimal(amount));
            }
            loadData(userController);
        }
    }

    public void loadData(UserController userController) {
        currentUser = userController.getUserInfo(CurrentUser.getCurrentUser().getId());

        nameLabel.setText("Name: " + currentUser.getName());
        emailLabel.setText("Email: " + currentUser.getEmail());
        roleLabel.setText("Role: " + currentUser.getRole().toString());
        approvedLabel.setText("Approved: " + currentUser.getApproved().toString());
        balanceLabel.setText("Balance: " + currentUser.getBalance().toString());
        realizedProfitLabel.setText("Realized Profit: " + currentUser.getRealizedProfit().toString());

        if (currentUser.getApproved().equals(User.Approved.APPROVED) && registerButton != null) {
            registerButton.setVisible(false);
        }
    }
}
