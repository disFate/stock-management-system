package dao.impl;

import DataSource.DatabaseConnectionPool;
import dao.IUserDAO;
import model.DTO.UserStockInfo;
import model.Entity.User;

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

    @Override
    public void updateStockQuantity(int userId, int stockId, int newQuantity) throws SQLException {
        String updateSql;
        if (newQuantity == 0) {
            updateSql = "DELETE FROM user_stocks WHERE user_id = ? AND stock_id = ?";
            try (Connection connection = DatabaseConnectionPool.getConnection();
                 PreparedStatement statement = connection.prepareStatement(updateSql)) {
                statement.setInt(1, userId);
                statement.setInt(2, stockId);
                statement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            updateSql = "INSERT INTO user_stocks (quantity, user_id, stock_id) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantity = ?";

            try (Connection connection = DatabaseConnectionPool.getConnection();
                 PreparedStatement statement = connection.prepareStatement(updateSql)) {
                statement.setInt(1, newQuantity);
                statement.setInt(2, userId);
                statement.setInt(3, stockId);
                statement.setInt(4, newQuantity);
                statement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateBalance(int userId, BigDecimal balance) {
        String sql = "UPDATE users SET balance = ? where id = ?";
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(sql);) {
            updateStatement.setBigDecimal(1, balance);
            updateStatement.setInt(2, userId);
            updateStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAverageCost(int userId, int stockId, BigDecimal newAverageCost) {
        String query = "UPDATE user_stocks SET average_cost = ? WHERE user_id = ? AND stock_id = ?";
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setBigDecimal(1, newAverageCost);
            statement.setInt(2, userId);
            statement.setInt(3, stockId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getRegisteredUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE approved = 'approved' AND role = 'customer'";
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

    public UserStockInfo getUserStockInfo(int userId, int stockId) {
        UserStockInfo userStockInfo = null;
        String query = "SELECT u.id as user_id, s.id as stock_id, s.symbol, s.name, us.average_cost, us.quantity, s.price, " +
                "((s.price - us.average_cost) * us.quantity) as unrealized_profit " +
                "FROM user_stocks us " +
                "JOIN users u ON u.id = us.user_id " +
                "JOIN stocks s ON s.id = us.stock_id " +
                "WHERE us.user_id = ? AND us.stock_id = ?";

        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            statement.setInt(2, stockId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String stockSymbol = resultSet.getString("symbol");
                String stockName = resultSet.getString("name");
                BigDecimal averageCost = resultSet.getBigDecimal("average_cost");
                int quantity = resultSet.getInt("quantity");
                BigDecimal unrealizedProfit = resultSet.getBigDecimal("unrealized_profit");
                BigDecimal price = resultSet.getBigDecimal("price");

                userStockInfo = new UserStockInfo(userId, stockId, stockSymbol, stockName, averageCost, quantity, unrealizedProfit, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userStockInfo;
    }

    public List<UserStockInfo> getUserStockInfoByUserId(int userId) {
        List<UserStockInfo> userStockInfoList = new ArrayList<>();
        String query = "SELECT u.id as user_id, s.id as stock_id, s.symbol, s.name, us.average_cost, us.quantity, s.price, " +
                "((s.price - us.average_cost) * us.quantity) as unrealized_profit " +
                "FROM user_stocks us " +
                "JOIN users u ON u.id = us.user_id " +
                "JOIN stocks s ON s.id = us.stock_id " +
                "WHERE us.user_id = ?";

        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int uid = resultSet.getInt("user_id");
                int stockId = resultSet.getInt("stock_id");
                String stockSymbol = resultSet.getString("symbol");
                String stockName = resultSet.getString("name");
                BigDecimal averageCost = resultSet.getBigDecimal("average_cost");
                int quantity = resultSet.getInt("quantity");
                BigDecimal unrealizedProfit = resultSet.getBigDecimal("unrealized_profit");
                BigDecimal price = resultSet.getBigDecimal("price");

                UserStockInfo userStockInfo = new UserStockInfo(uid, stockId, stockSymbol, stockName, averageCost, quantity, unrealizedProfit, price);
                userStockInfoList.add(userStockInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userStockInfoList;
    }

    public User getUserById(int id) {
        User user = null;
        String query = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                User.Role role = User.Role.valueOf(resultSet.getString("role").toUpperCase());
                User.Approved approved = User.Approved.valueOf(resultSet.getString("approved").toUpperCase());
                BigDecimal balance = resultSet.getBigDecimal("balance");
                BigDecimal realizedProfit = resultSet.getBigDecimal("realized_profit");

                user = new User(id, name, email, password, role, approved, balance, realizedProfit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public void updateRealizedProfit(int userId, BigDecimal newRealizedProfit) {
        try (Connection connection = DatabaseConnectionPool.getConnection()) {
            String query = "UPDATE users SET realized_profit = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setBigDecimal(1, newRealizedProfit);
            statement.setInt(2, userId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserApproved(int userId) {
        String query = "UPDATE users SET approved = 'approved' WHERE id = ?";
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUserPending(int userId) {
        String query = "UPDATE users SET approved = 'pending' WHERE id = ?";
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserDenied(int userId) {
        String query = "UPDATE users SET approved = 'declined' WHERE id = ?";
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCountPending() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM users WHERE approved = 'pending'";
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public List<User> getDerivativeUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE realized_profit > 10000";
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

    public List<User> getPendingUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE approved = 'pending' AND role = 'customer'";
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

    public void startTransaction(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commitTransaction(Connection connection) throws SQLException {
        connection.commit();
    }

    public void rollbackTransaction(Connection connection) throws SQLException {
        connection.rollback();
    }

    public void addUser(User user) {
        String sql = "INSERT INTO users (name, email, password, role, approved, balance, realized_profit) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole().toString().toLowerCase());
            statement.setString(5, user.isApproved().toString().toLowerCase());
            statement.setBigDecimal(6, user.getBalance());
            statement.setBigDecimal(7, BigDecimal.ZERO );

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}