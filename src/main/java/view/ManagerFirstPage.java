package view;

import controller.StockController;
import controller.UserController;
import dao.IStockDAO;
import dao.impl.StockDAOImpl;
import model.Stock;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ManagerFirstPage extends JFrame {


    private JButton stocks;
    private JButton users;


    public ManagerFirstPage(){
        setTitle("Manager Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




    }

}
