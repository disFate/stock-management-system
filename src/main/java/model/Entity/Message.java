package model.Entity;

/**
 * @Author: Tsuna
 * @Date: 2023-05-03-14:39
 * @Description:
 */

import java.sql.Timestamp;

public class Message {
    private int id;
    private int userId;
    private String message;
    private boolean isRead;
    private Timestamp messageDate;

    public Message() {
    }

    public Message(int id, int userId, String message, boolean isRead, Timestamp messageDate) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.isRead = isRead;
        this.messageDate = messageDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public Timestamp getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Timestamp messageDate) {
        this.messageDate = messageDate;
    }
}