package dao.impl;

import DataSource.DatabaseConfig;
import DataSource.DatabaseConnectionPool;
import dao.IStockDAO;
import model.Entity.Stock;
import model.Entity.Transaction;

import java.math.BigDecimal;
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
        String query = "SELECT * FROM stocks WHERE isDeleted = false";
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String symbol = resultSet.getString("symbol");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
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
    public Stock getStockById(int id) {
        Stock stock = null;
        String query = "SELECT * FROM stocks WHERE isDeleted = false and id = ?";

        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String symbol = resultSet.getString("symbol");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
                int amount = resultSet.getInt("amount");

                stock = new Stock(id, symbol, name, price, amount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stock;
    }

    @Override
    public List<Stock> getUserStocks(int userId) {
        List<Stock> stocks = new ArrayList<>();
        String query = "SELECT s.id, s.symbol, s.name, s.price, us.quantity " +
                "FROM stocks s " +
                "INNER JOIN user_stocks us ON s.id = us.stock_id " +
                "WHERE us.user_id = ? AND s.isDeleted = false";
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String symbol = resultSet.getString("symbol");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
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

    public void addStock(String symbol, String company_name, double price, int amount) {
        String query = "INSERT INTO stocks (symbol, name, price, amount) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, symbol);
            statement.setString(2, company_name);
            statement.setDouble(3, price);
            statement.setInt(4, amount);
            statement.executeUpdate();

            // Retrieve the auto-generated ID of the newly inserted stock
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int stockID = rs.getInt(1);
                System.out.println("Successfully added stock with ID: " + stockID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStock(String companyName) {
        String query = "UPDATE stocks SET isDeleted=true WHERE name=?";
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setString(1, companyName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editStock(String oldName, String symbol, String company_name, double price, int amount) {
        String query = "UPDATE stocks SET symbol = ?, name = ?, price = ?, amount = ? WHERE name = ?";
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, symbol);
            statement.setString(2, company_name);
            statement.setDouble(3, price);
            statement.setInt(4, amount);
            statement.setString(5, oldName);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Successfully updated stock with name: " + oldName);
            } else {
                System.out.println("No stock found with name: " + oldName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startTransaction(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commitTransaction(Connection connection) throws SQLException {
        connection.commit();
    }

    public void rollbackTransaction(Connection connection) throws SQLException {
        connection.rollback();
    }

}