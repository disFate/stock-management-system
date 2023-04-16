//package views;
//
///**
// * @Author: Tsuna
// * @Date: 2023-04-13-19:33
// * @Description:
// */
//import javax.swing.*;
//import java.awt.*;
//
//public class UserView extends BaseView {
//    private JLabel nameLabel;
//    private JLabel emailLabel;
//    private JLabel balanceLabel;
//
//    public UserView() {
//        setupWindow("User");
//
//        JPanel formPanel = createFormPanel(3, 2, 5, 5);
//        formPanel.add(new JLabel("Name:"));
//        nameLabel = new JLabel();
//        formPanel.add(nameLabel);
//        formPanel.add(new JLabel("Email:"));
//        emailLabel = new JLabel();
//        formPanel.add(emailLabel);
//        formPanel.add(new JLabel("Balance:"));
//        balanceLabel = new JLabel();
//        formPanel.add(balanceLabel);
//
//        add(formPanel, BorderLayout.CENTER);
//        pack();
//    }
//    // Add other methods and event listeners as needed
//}
