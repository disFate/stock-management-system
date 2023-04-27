package dao;

/**
 * @Author: Tsuna
 * @Date: 2023-04-21-15:37
 * @Description:
 */

import model.Stock;
import model.Transaction;

import java.util.List;

public interface IStockDAO {
    List<Stock> getAllStocks();

    List<Stock> getUserStocks(int userId);

    void updateAmount(int stockId, int transactionAmount, Transaction.Type transactionType);

    void addStock( String symbol, String Company, double Price, int amount);

    void deleteStock(String Company);

    void editStock(String oldName, String symbol, String Company, double Price, int amount);
}
