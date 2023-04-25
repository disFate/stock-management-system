package controller;

/**
 * @Author: Tsuna
 * @Date: 2023-04-21-15:50
 * @Description:
 */

import dao.IStockDAO;
import dao.ITransactionDAO;
import model.Stock;
import model.User;
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
        User currentUser = new CurrentUser().getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("No user is currently logged in");
        }
        return stockDAO.getUserStocks(currentUser.getId());
    }

    public void addStock (int stockID, String symbol, String company, double price, int amount){
        stockDAO.addStock( stockID,  symbol,  company,  price,  amount);
    }

    public void deleteStock(int stockID){
        stockDAO.deleteStock(stockID);
    }

}
