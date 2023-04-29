package controller;

/**
 * @Author: Tsuna
 * @Date: 2023-04-21-15:50
 * @Description:
 */

import dao.IStockDAO;
import dao.ITransactionDAO;
import model.Entity.Stock;
import model.Entity.User;
import session.CurrentUser;

import java.util.List;

public class StockController {
    private IStockDAO stockDAO;
    private ITransactionDAO transactionDAO;

    public StockController(IStockDAO stockDAO, ITransactionDAO transactionDAO) {
        this.stockDAO = stockDAO;
        this.transactionDAO = transactionDAO;
    }

    public List<Stock> getAllStocks() {
        return stockDAO.getAllStocks();
    }

    public List<Stock> getUserStocks() {
        User currentUser = CurrentUser.getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("No user is currently logged in");
        }
        return stockDAO.getUserStocks(currentUser.getId());
    }

    public void addStock(String symbol, String company, double price, int amount) {
        stockDAO.addStock(symbol, company, price, amount);
    }

    public void deleteStock(String company) {
        stockDAO.deleteStock(company);
    }

    public void editStock(String oldName, String Symbol, String name, double price, int amount) {
        stockDAO.editStock(oldName, Symbol, name, price, amount);
    }
}
