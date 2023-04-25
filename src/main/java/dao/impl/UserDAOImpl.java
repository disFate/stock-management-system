package dao.impl;

import DataSource.DatabaseConfig;
import DataSource.DatabaseConnectionPool;
import dao.IUserDAO;
import model.Stock;
import model.Transaction;
import model.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: Tsuna
 * @Date: 2023-04-23-16:43
 * @Description:
 */
public class UserDAOImpl implements IUserDAO {
    DatabaseConfig databaseConfig = DatabaseConfig.getInstance();

    public List<User> getRegisteredUsers(){
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE approved = TRUE";
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                BigDecimal balance = resultSet.getBigDecimal("balance");

                User newUser = new User(id, name, email, balance);
                users.add(newUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }


    @Override
    public void updateStock(int userId, int stockId, int transactionQuantity, Transaction.Type type) throws SQLException {
        String sql;
        if (type.equals(Transaction.Type.BUY)) {
            sql = "INSERT INTO user_stocks (user_id, stock_id, quantity) " +
                    "VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE quantity = quantity + ?";
            try (Connection connection = DatabaseConnectionPool.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, userId);
                statement.setInt(2, stockId);
                statement.setInt(3, transactionQuantity);
                statement.setInt(4, transactionQuantity);
                statement.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            sql = "UPDATE user_stocks SET quantity = quantity - ? where user_id = ?" +
                    " and stock_id = ?";
            try (Connection connection = DatabaseConnectionPool.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, transactionQuantity);
                statement.setInt(2, userId);
                statement.setInt(3, stockId);
                statement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
//
//    @Override
//    public void sellStock(int userId, Stock stock, int buyQuantity) throws SQLException {
//        String sql = "UPDATE user_stocks SET quantity = quantity - ? " +
//                "WHERE user_id = ? AND stock_id = ?";
//        try (Connection connection = DatabaseConnectionPool.getConnection();
//             PreparedStatement updateStatement = connection.prepareStatement(sql);) {
//            int stockId = stock.getId();
//            updateStatement.setInt(1, buyQuantity);
//            updateStatement.setInt(2, userId);
//            updateStatement.setInt(3, stockId);
//            updateStatement.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void updateBalance(int userId, User user) {
        String sql = "UPDATE users SET balance = ? where id = ?";
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(sql);) {
            updateStatement.setBigDecimal(1, user.getBalance());
            updateStatement.setInt(2, userId);
            updateStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
