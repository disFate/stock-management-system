package view.managerPages;

import controller.UserController;
import dao.IStockDAO;
import dao.impl.StockDAOImpl;
import dao.impl.TransactionDAOImpl;
import dao.impl.UserDAOImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        String[] columnNames = {"Username", "First Name", "Last Name", "Current Balance"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // TODO: add users from table

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
                    //perform approve
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
                    //perform deny
                } else {
                    JOptionPane.showMessageDialog(null, "please select a user");
                }
            }
        });


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IStockDAO stockDAO = new StockDAOImpl();
            UserController userController = new UserController(new UserDAOImpl(), new TransactionDAOImpl());
            new UserRequestsPage(userController).setVisible(true);
        });
    }


}
