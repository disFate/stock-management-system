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
        this.currentUser = new User();
        // todo find a way to really restore the current user info
        currentUser.setId(2);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}

