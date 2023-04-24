package session;

import model.User;

import java.math.BigDecimal;

/**
 * @Author: Tsuna
 * @Date: 2023-04-21-16:31
 * @Description:
 */
public class CurrentUser {
    private User currentUser;

    public CurrentUser() {
        this.currentUser = new User(2, "dong", "tsuna@bu.edu", "123", User.Role.CUSTOMER, true, BigDecimal.valueOf(100000l));
        // todo find a way to really restore the current user info
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}

