package session;

import model.Entity.User;

import java.math.BigDecimal;

/**
 * @Author: Tsuna
 * @Date: 2023-04-21-16:31
 * @Description:
 */
public class CurrentUser {
    private static User currentUser;

    static {
        currentUser = new User(2, "dong", "tsuna@bu.edu", "123", User.Role.CUSTOMER, User.Approved.APPROVED, BigDecimal.valueOf(100000l), BigDecimal.valueOf(0));
    }

    public CurrentUser() {
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}

