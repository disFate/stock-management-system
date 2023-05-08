package dao;

import model.DTO.UserStockInfo;
import model.Entity.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: Tsuna
 * @Date: 2023-04-23-16:44
 * @Description:
 */
public interface IUserDAO {

    void updateStockQuantity(int userId, int stockId, int transactionQuantity) throws SQLException;

    void updateBalance(int userId, BigDecimal balance);

    void updateAverageCost(int userId, int stockId, BigDecimal newAverageCost);

    List<User> getRegisteredUsers();
    List<User> getDerivativeUsers();

    UserStockInfo getUserStockInfo(int userId, int stockId);

    List<UserStockInfo> getUserStockInfoByUserId(int userId);

    User getUserById(int userId);

    User getUserByEmail(String email);

    void updateRealizedProfit(int userId, BigDecimal newRealizedProfit);

    void updateUserApproved(int userId);

    void updateUserPending(int userId);

    void updateUserDenied(int userId);

    int getCountPending();

    List<User> getPendingUsers();

    public void startTransaction(Connection connection) throws SQLException;

    public void commitTransaction(Connection connection) throws SQLException;

    public void rollbackTransaction(Connection connection) throws SQLException;

    void addUser(User user);
}
