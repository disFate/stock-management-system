package session;

import model.User;

/**
 * @Author: Tsuna
 * @Date: 2023-04-21-16:31
 * @Description:
 */
public class CurrentUser {
    private User currentUser;

    public CurrentUser() {
        this.currentUser = new User(2, "dong","123");
        // todo find a way to really restore the current user info
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}

