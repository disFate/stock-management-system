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

        JPanel infoPanel = new JPanel(new GridLayout(6, 1));
        mainPanel.add(infoPanel, BorderLayout.CENTER);

        nameLabel = new JLabel("Name: " + currentUser.getName());
        infoPanel.add(nameLabel);

        emailLabel = new JLabel("Email: " + currentUser.getEmail());
        infoPanel.add(emailLabel);

        roleLabel = new JLabel("Role: " + currentUser.getRole().toString());
        infoPanel.add(roleLabel);

        approvedLabel = new JLabel("Approved: " + currentUser.getApproved().toString());
        infoPanel.add(approvedLabel);

        balanceLabel = new JLabel("Balance: " + currentUser.getBalance().toString());
        infoPanel.add(balanceLabel);

        realizedProfitLabel = new JLabel("Realized Profit: " + currentUser.getRealizedProfit().toString());
        infoPanel.add(realizedProfitLabel);

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
