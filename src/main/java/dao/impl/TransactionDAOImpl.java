package dao.impl;

/**
 * @Author: Tsuna
 * @Date: 2023-04-23-15:30
 * @Description:
 */

import DataSource.DatabaseConfig;
import DataSource.DatabaseConnectionPool;
import dao.ITransactionDAO;
import model.Transaction;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements ITransactionDAO {
    private DatabaseConfig databaseConfig = DatabaseConfig.getInstance();

    @Override
    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, stock_id, type, quantity, price) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setInt(1, transaction.getUserId());
            preparedStatement.setInt(2, transaction.getStockId());
            preparedStatement.setString(3, transaction.getType().toString());
            preparedStatement.setInt(4, transaction.getQuantity());
            preparedStatement.setDouble(5, transaction.getPrice());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Transaction> getTransactionsByUser(User user) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ?";
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, user.getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("stock_id"),
                        Transaction.Type.valueOf(resultSet.getString("type")),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price"),
                        resultSet.getTimestamp("transaction_date").toLocalDateTime()
                );
                transactions.add(transaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactions;
    }
}
