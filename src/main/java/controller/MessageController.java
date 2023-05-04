package controller;

import dao.IMessageDAO;
import model.Entity.Message;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class MessageController {
    private IMessageDAO messageDAO;

    public MessageController(IMessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public boolean createMessage(int userId, String messageContent) {
        Message message = new Message();
        message.setUserId(userId);
        message.setMessage(messageContent);
        message.setRead(false);
        message.setMessageDate((Timestamp) new Date());
        return messageDAO.createMessage(message);
    }

    public boolean markMessageAsRead(int messageId) {
        return messageDAO.updateMessageAsRead(messageId);
    }

    public List<Message> getUnreadMessagesByUserId(int userId) {
        return messageDAO.getUnreadMessagesByUserId(userId);
    }

    public List<Message> getAllMessagesByUserId(int userId) {
        return messageDAO.getAllMessagesByUserId(userId);
    }
}
