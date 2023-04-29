package dao;

import model.DTO.UserStockInfo;
import model.Entity.Transaction;
import model.Entity.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: Tsuna
 * @Date: 2023-04-23-16:44
 * @Description:
 */
public interface IUserDAO {

    void updateStockQuantity(int userId, int stockId, int transactionQuantity, Transaction.Type type) throws SQLException;

    void updateBalance(int userId, BigDecimal balance);

    void updateAverageCost(int userId, int stockId, int quantity, BigDecimal price);

    List<User> getRegisteredUsers();

    List<UserStockInfo> getUserStockInfo(int userId);

    User getUserById(int userId);

    void updateRealizedProfit(int userId, int stockId, int quantity, BigDecimal price);
}
