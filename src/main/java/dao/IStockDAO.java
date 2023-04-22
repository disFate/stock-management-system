package dao;

/**
 * @Author: Tsuna
 * @Date: 2023-04-21-15:37
 * @Description:
 */
import model.Stock;

import java.sql.SQLException;
import java.util.List;

public interface IStockDAO {
    List<Stock> getAllStocks();
    List<Stock> getUserStocks(int userId);

    void buyStock(int userId, int stockId, int quantity) throws SQLException;
    void sellStock(int userId, String symbol, int quantity) throws SQLException;
}
