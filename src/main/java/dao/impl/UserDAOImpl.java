package dao.impl;

import DataSource.DatabaseConnectionPool;
import dao.IUserDAO;
import model.DTO.UserStockInfo;
import model.Entity.Transaction;
import model.Entity.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public void updateStockQuantity(int userId, int stockId, int transactionQuantity, Transaction.Type type) throws SQLException {
        String updateSql = "UPDATE user_stocks SET quantity = quantity - ? WHERE user_id = ? AND stock_id = ?";
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateSql)) {
            statement.setInt(1, transactionQuantity);
            statement.setInt(2, userId);
            statement.setInt(3, stockId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String checkSql = "SELECT quantity FROM user_stocks WHERE user_id = ? AND stock_id = ?";
        int updatedQuantity = 0;
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(checkSql)) {
            statement.setInt(1, userId);
            statement.setInt(2, stockId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                updatedQuantity = resultSet.getInt("quantity");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (updatedQuantity == 0) {
            String deleteSql = "DELETE FROM user_stocks WHERE user_id = ? AND stock_id = ?";
            try (Connection connection = DatabaseConnectionPool.getConnection();
                 PreparedStatement statement = connection.prepareStatement(deleteSql)) {
                statement.setInt(1, userId);
                statement.setInt(2, stockId);
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
    public void updateAverageCost(int userId, int stockId, int quantity, BigDecimal price) {
        try (Connection connection = DatabaseConnectionPool.getConnection()) {
            String query = "SELECT quantity, average_cost FROM user_stocks WHERE user_id = ? AND stock_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, stockId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int currentQuantity = resultSet.getInt("quantity") - quantity;
                BigDecimal currentAverageCost = resultSet.getBigDecimal("average_cost");

                BigDecimal newAverageCost = currentAverageCost.multiply(BigDecimal.valueOf(currentQuantity))
                        .add(price.multiply(BigDecimal.valueOf(quantity)))
                        .divide(BigDecimal.valueOf(currentQuantity + quantity), 2, RoundingMode.HALF_UP);

                String updateQuery = "UPDATE user_stocks SET average_cost = ? WHERE user_id = ? AND stock_id = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setBigDecimal(1, newAverageCost);
                updateStatement.setInt(2, userId);
                updateStatement.setInt(3, stockId);

                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    public List<User> getRegisteredUsers() {
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

    public List<UserStockInfo> getUserStockInfo(int userId) {
        List<UserStockInfo> userStockInfoList = new ArrayList<>();
        String query = "SELECT u.id as user_id, s.id as stock_id, s.symbol, s.name, us.average_cost, us.quantity, " +
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

                UserStockInfo userStockInfo = new UserStockInfo(uid, stockId, stockSymbol, stockName, averageCost, quantity, unrealizedProfit);
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
    public void updateRealizedProfit(int userId, int stockId, int quantity, BigDecimal price) {
        try (Connection connection = DatabaseConnectionPool.getConnection()) {
            String query = "SELECT quantity, average_cost, realized_profit FROM users INNER JOIN user_stocks ON users.id = user_stocks.user_id WHERE user_id = ? AND stock_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, stockId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                BigDecimal averageCost = resultSet.getBigDecimal("average_cost");
                BigDecimal realizedProfit = resultSet.getBigDecimal("realized_profit");

                BigDecimal profitFromSale = (price.subtract(averageCost)).multiply(BigDecimal.valueOf(quantity));
                BigDecimal newRealizedProfit = realizedProfit.add(profitFromSale);

                String updateQuery = "UPDATE users SET realized_profit = ? WHERE id = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setBigDecimal(1, newRealizedProfit);
                updateStatement.setInt(2, userId);

                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
