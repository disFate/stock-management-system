package session;

import controller.UserController;
import dao.impl.StockDAOImpl;
import dao.impl.TransactionDAOImpl;
import dao.impl.UserDAOImpl;
import model.Entity.User;

/**
 * @Author: Tsuna
 * @Date: 2023-04-21-16:31
 * @Description:
 */
public class CurrentUser {
    private static User currentUser;

    static {
        // todo create currentUser when login. Sometimes user should be updated when they update or withdraw, we can remind user to log in again
        currentUser = new UserController(new UserDAOImpl(), new TransactionDAOImpl(), new StockDAOImpl()).getUserInfo(2);
    }

    public CurrentUser() {
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}

