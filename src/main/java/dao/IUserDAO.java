package dao;

import model.Transaction;
import model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author: Tsuna
 * @Date: 2023-04-23-16:44
 * @Description:
 */
public interface IUserDAO {

    void updateStock(int userId, int stockId, int transactionQuantity, Transaction.Type type) throws SQLException;

    void updateBalance(int userId, User user);


    List<User>getRegisteredUsers();
}
