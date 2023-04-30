package view.managerPages;

import controller.UserController;
import dao.impl.StockDAOImpl;
import dao.impl.TransactionDAOImpl;
import dao.impl.UserDAOImpl;
import model.Entity.User;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

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

        userTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        userTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                // Ignore extra messages.
                if (event.getValueIsAdjusting()) return;

                ListSelectionModel lsm = (ListSelectionModel)event.getSource();
                int[] selectedRows = userTable.getSelectedRows();
                int numRows = tableModel.getRowCount();

                for (int i = 0; i < numRows; i++) {
                    if (!contains(selectedRows, i)) {
                        lsm.removeSelectionInterval(i, i);
                    }
                }
            }

            private boolean contains(int[] array, int value) {
                for (int i : array) {
                    if (i == value) {
                        return true;
                    }
                }
                return false;
            }

        });



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


//        JTextField message = new JTextField();
//        message.setColumns(40);
//        buttonPanel.add(message);
        buttonPanel.add(notifyButton);
        notifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = userTable.getSelectedRows();
                if (selectedRows.length == 0) {
                    JOptionPane.showMessageDialog(mainPanel, "Please select at least one user to notify.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int row : selectedRows) {
                        sb.append(tableModel.getValueAt(row, 1));
                        sb.append(", ");
                    }
                    sb.delete(sb.length() - 2, sb.length()); // remove the trailing comma and space
                    String recipients = sb.toString();

                    JFrame messageFrame = new JFrame();
                    messageFrame.setTitle("New Message");
                    messageFrame.setSize(400, 300);
                    messageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    messageFrame.setLocationRelativeTo(null);

                    JPanel messagePanel = new JPanel(new BorderLayout());
                    messageFrame.setContentPane(messagePanel);

                    JLabel recipientLabel = new JLabel("Send message to: " + recipients);
                    messagePanel.add(recipientLabel, BorderLayout.NORTH);

                    JTextArea messageTextArea = new JTextArea();
                    JScrollPane messageScrollPane = new JScrollPane(messageTextArea);
                    messagePanel.add(messageScrollPane, BorderLayout.CENTER);

                    JButton sendButton = new JButton("Send");
                    sendButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String message = messageTextArea.getText();
                            //TODO: send the message to the selected users
                            messageFrame.dispose();
                        }
                    });
                    messagePanel.add(sendButton, BorderLayout.SOUTH);

                    messageFrame.setVisible(true);
                }
            }
        });


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserController userController = new UserController(new UserDAOImpl(), new TransactionDAOImpl(), new StockDAOImpl());
            new UserNotifyPage(userController).setVisible(true);
        });
    }


}
