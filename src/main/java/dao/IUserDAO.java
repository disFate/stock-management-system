package dao;

import model.Stock;
import model.User;

import java.sql.SQLException;

/**
 * @Author: Tsuna
 * @Date: 2023-04-23-16:44
 * @Description:
 */
public interface IUserDAO {

    void buyStock(int userId, Stock stock, int quantity) throws SQLException;

    void sellStock(int userId, Stock stock, int quantity) throws SQLException;

    void updateUser(int userId, User user);
}
