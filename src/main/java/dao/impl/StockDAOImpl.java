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

            String query = "SELECT * FROM stocks";
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
                    "FROM stocks s " +
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
}