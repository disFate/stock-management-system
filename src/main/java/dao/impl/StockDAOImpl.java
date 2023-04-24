package dao.impl;

import DataSource.DatabaseConfig;
import DataSource.DatabaseConnectionPool;
import dao.IStockDAO;
import model.Stock;
import model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Tsuna
 * @Date: 2023-04-21-15:38
 * @Description:
 */
public class StockDAOImpl implements IStockDAO {
    private DatabaseConfig databaseConfig;

    public StockDAOImpl() {
        databaseConfig = DatabaseConfig.getInstance();
    }

    @Override
    public List<Stock> getAllStocks() {
        List<Stock> stocks = new ArrayList<>();
        String query = "SELECT * FROM stocks";
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String symbol = resultSet.getString("symbol");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int amount = resultSet.getInt("amount");

                Stock stock = new Stock(id, symbol, name, price, amount);
                stocks.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stocks;
    }

    @Override
    public List<Stock> getUserStocks(int userId) {
        List<Stock> stocks = new ArrayList<>();
        String query = "SELECT s.id, s.symbol, s.name, s.price, us.quantity " +
                "FROM stocks s " +
                "INNER JOIN user_stocks us ON s.id = us.stock_id " +
                "WHERE us.user_id = ?";
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String symbol = resultSet.getString("symbol");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");

                Stock stock = new Stock(id, symbol, name, price, quantity);
                stocks.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stocks;
    }

    @Override
    public void updateAmount(int stockId, int transactionAmount, Transaction.Type transactionType) {
        String sql;
        if (transactionType.equals(Transaction.Type.BUY)) {
            sql = "UPDATE stocks SET amount = amount - ? where id = ?";
        } else {
            sql = "UPDATE stocks SET amount = amount + ? where id = ?";
        }

        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, transactionAmount);
            statement.setInt(2, stockId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}