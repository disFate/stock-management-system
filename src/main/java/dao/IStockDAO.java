package dao;

/**
 * @Author: Tsuna
 * @Date: 2023-04-21-15:37
 * @Description:
 */

import model.Entity.Stock;
import model.Entity.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IStockDAO {
    List<Stock> getAllStocks();

    Stock getStockById(int id);

    List<Stock> getUserStocks(int userId);

    void updateAmount(int stockId, int transactionAmount, Transaction.Type transactionType);

    void addStock(String symbol, String Company, double Price, int amount);

    void deleteStock(String Company);

    void editStock(String oldName, String symbol, String Company, double Price, int amount);

    public void startTransaction(Connection connection) throws SQLException;

    public void commitTransaction(Connection connection) throws SQLException;

    public void rollbackTransaction(Connection connection) throws SQLException;
}
