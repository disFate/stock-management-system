package controller;

/**
 * @Author: Tsuna
 * @Date: 2023-04-21-15:50
 * @Description:
 */
import dao.IStockDAO;
import model.Stock;
import model.User;
import session.CurrentUser;

import java.sql.SQLException;
import java.util.List;

public class StockController {
    private IStockDAO stockDAO;

    public StockController(IStockDAO stockDAO) {
        this.stockDAO = stockDAO;
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

    public void buyStock(int stockId, int quantity) {
        User currentUser = new CurrentUser().getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("No user is currently logged in");
        }
        try {
            stockDAO.buyStock(currentUser.getId(), stockId, quantity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sellStock(String symbol, int quantity) {
        User currentUser = new CurrentUser().getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("No user is currently logged in");
        }
        try {
            stockDAO.sellStock(currentUser.getId(), symbol, quantity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
