package dao.impl;
import dao.DatabaseConfig;
import dao.IStockDAO;
import model.Stock;

import java.sql.*;
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

        try (Connection connection = DriverManager.getConnection(
                databaseConfig.getDbUrl(),
                databaseConfig.getDbUsername(),
                databaseConfig.getDbPassword())) {

            String query = "SELECT * FROM stock";
            PreparedStatement statement = connection.prepareStatement(query);
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

        try (Connection connection = DriverManager.getConnection(
                databaseConfig.getDbUrl(),
                databaseConfig.getDbUsername(),
                databaseConfig.getDbPassword())) {

            String query = "SELECT s.id, s.symbol, s.name, s.price, us.quantity " +
                    "FROM stock s " +
                    "INNER JOIN user_stocks us ON s.id = us.stock_id " +
                    "WHERE us.user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
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
    public void buyStock(int userId, int stockId, int quantity) throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                databaseConfig.getDbUrl(),
                databaseConfig.getDbUsername(),
                databaseConfig.getDbPassword())) {

            String insertOrUpdateQuery = "INSERT INTO user_stocks (user_id, stock_id, quantity) " +
                    "VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE quantity = quantity + ?";
            PreparedStatement statement = connection.prepareStatement(insertOrUpdateQuery);
            statement.setInt(1, userId);
            statement.setInt(2, stockId);
            statement.setInt(3, quantity);
            statement.setInt(4, quantity);
            statement.executeUpdate();
        }
    }

    @Override
    public void sellStock(int userId, String symbol, int quantity) throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                databaseConfig.getDbUrl(),
                databaseConfig.getDbUsername(),
                databaseConfig.getDbPassword())) {

            // 首先，根据符号获取股票ID
            String selectStockIdQuery = "SELECT id FROM stock WHERE symbol = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectStockIdQuery);
            selectStatement.setString(1, symbol);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                int stockId = resultSet.getInt("id");

                // 使用获取到的股票ID进行更新
                String updateQuery = "UPDATE user_stocks SET quantity = quantity - ? " +
                        "WHERE user_id = ? AND stock_id = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setInt(1, quantity);
                updateStatement.setInt(2, userId);
                updateStatement.setInt(3, stockId);
                updateStatement.executeUpdate();
            } else {
                throw new SQLException("未找到对应的股票符号：" + symbol);
            }
        }
    }

}

