package dao;

import model.Entity.Message;

import java.util.List;

/**
 * @Author: Tsuna
 * @Date: 2023-05-03-14:41
 * @Description:
 */
public interface IMessageDAO {
    boolean createMessage(Message message);

    boolean updateMessageAsRead(int messageId);

    List<Message> getUnreadMessagesByUserId(int userId);

    List<Message> getAllMessagesByUserId(int userId);
}
