package view.managerPages;

import controller.UserController;
import dao.IStockDAO;
import dao.impl.StockDAOImpl;
import dao.impl.TransactionDAOImpl;
import dao.impl.UserDAOImpl;
import model.Entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserRequestsPage extends JFrame {
    private JPanel mainPanel;
    private JTable userTable;
    private JButton backButton;
    private JButton approveButton;
    private JButton denyButton;

    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;


    public UserRequestsPage(UserController userController) {
        setTitle("User Requests");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        setContentPane(mainPanel);
        mainPanel.setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Name", "Email", "Current Balance"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        List<User> pendingUsers = userController.getPendingUsers();

        for (User user : pendingUsers) {
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

        approveButton = new JButton("approve");

        denyButton = new JButton("deny");

        buttonPanel.add(approveButton);
        buttonPanel.add(denyButton);


        approveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow != -1) {
                    int userID = (int) tableModel.getValueAt(selectedRow, 0);
                    userController.updateUserApproved(userID);
                } else {
                    JOptionPane.showMessageDialog(null, "please select a user");
                }
            }
        });

        denyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow != -1) {
                    int userID = (int) tableModel.getValueAt(selectedRow, 0);
                    userController.updateUserDenied(userID);
                } else {
                    JOptionPane.showMessageDialog(null, "please select a user");
                }
            }
        });


    }

    public static void main(String[] args) {
        System.out.println("RUNNING PAGE");
        SwingUtilities.invokeLater(() -> {
            IStockDAO stockDAO = new StockDAOImpl();
            UserController userController = new UserController(new UserDAOImpl(), new TransactionDAOImpl(), new StockDAOImpl());
            new UserRequestsPage(userController).setVisible(true);
        });
    }


}
