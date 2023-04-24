package dao;

/**
 * @Author: Tsuna
 * @Date: 2023-04-21-15:37
 * @Description:
 */

import model.Stock;

import java.util.List;

public interface IStockDAO {
    List<Stock> getAllStocks();

    List<Stock> getUserStocks(int userId);
}
