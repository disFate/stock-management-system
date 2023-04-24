package dao.impl;

import dao.DatabaseConfig;
import dao.IUserDAO;
import model.Stock;
import model.User;

import java.sql.*;

/**
 * @Author: Tsuna
 * @Date: 2023-04-23-16:43
 * @Description:
 */
public class UserDAOImpl implements IUserDAO {
    DatabaseConfig databaseConfig = DatabaseConfig.getInstance();

    @Override
    public void buyStock(int userId, Stock stock, int quantity) throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                databaseConfig.getDbUrl(),
                databaseConfig.getDbUsername(),
                databaseConfig.getDbPassword())) {

            String insertOrUpdateQuery = "INSERT INTO user_stocks (user_id, stock_id, quantity) " +
                    "VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE quantity = quantity + ?";
            PreparedStatement statement = connection.prepareStatement(insertOrUpdateQuery);
            statement.setInt(1, userId);
            statement.setInt(2, stock.getId());
            statement.setInt(3, quantity);
            statement.setInt(4, quantity);
            statement.executeUpdate();
        }
    }

    @Override
    public void sellStock(int userId, Stock stock, int buyQuantity) throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                databaseConfig.getDbUrl(),
                databaseConfig.getDbUsername(),
                databaseConfig.getDbPassword())) {

            // 首先，根据符号获取股票ID
            String selectStockIdQuery = "SELECT id FROM stocks WHERE symbol = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectStockIdQuery);
            selectStatement.setString(1, stock.getSymbol());
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                int stockId = resultSet.getInt("id");

                // 使用获取到的股票ID进行更新
                String updateQuery = "UPDATE user_stocks SET quantity = quantity - ? " +
                        "WHERE user_id = ? AND stock_id = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setInt(1, buyQuantity);
                updateStatement.setInt(2, userId);
                updateStatement.setInt(3, stockId);
                updateStatement.executeUpdate();
            } else {
                throw new SQLException("can't find the stock：" + stock.getSymbol());
            }
        }
    }

    @Override
    public void updateUser(int userId, User user) {

    }

}
