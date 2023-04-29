package view.managerPages;

import controller.UserController;
import dao.impl.StockDAOImpl;
import dao.impl.TransactionDAOImpl;
import dao.impl.UserDAOImpl;
import model.Entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserNotifyPage extends JFrame {
    List<User> registeredUsers;
    private JPanel mainPanel;
    private JTable userTable;
    private JButton backButton;
    private JButton notifyButton;


    private JTextField message;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;


    public UserNotifyPage(UserController userController) {
        setTitle("Users");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        setContentPane(mainPanel);
        mainPanel.setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Name", "Email", "Current Balance"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        List<User> registeredUsers = userController.getRegisteredUsers();

        for (User user : registeredUsers) {
            Object[] rowData = {user.getId(), user.getName(), user.getEmail(), user.getBalance()};
            tableModel.addRow(rowData);
        }

        scrollPane = new JScrollPane(userTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        backButton = new JButton("back");
        bottomPanel.add(backButton, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        notifyButton = new JButton("notify");


        JTextField message = new JTextField();
        message.setColumns(40);
        buttonPanel.add(message);
        buttonPanel.add(notifyButton);
        //TODO: add functionality to buttons

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserController userController = new UserController(new UserDAOImpl(), new TransactionDAOImpl(), new StockDAOImpl());
            new UserNotifyPage(userController).setVisible(true);
        });
    }


}
