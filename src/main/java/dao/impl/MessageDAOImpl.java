package dao.impl;

import DataSource.DatabaseConnectionPool;
import dao.IMessageDAO;
import model.Entity.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAOImpl implements IMessageDAO {
    private Connection connection;

    public MessageDAOImpl() throws SQLException {
        this.connection = DatabaseConnectionPool.getConnection();
    }

    @Override
    public boolean createMessage(Message message) {
        String sql = "INSERT INTO message (user_id, message, message_date) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, message.getUserId());
            pstmt.setString(2, message.getMessage());
            pstmt.setTimestamp(3, new java.sql.Timestamp(message.getMessageDate().getTime()));
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateMessageAsRead(int messageId) {
        String sql = "UPDATE message SET isRead = true WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, messageId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Message> getUnreadMessagesByUserId(int userId) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM message WHERE user_id = ? AND isRead = false";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                messages.add(createMessageFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public List<Message> getAllMessagesByUserId(int userId) {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM message WHERE user_id = ? ORDER BY message_date DESC";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                messages.add(createMessageFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    private Message createMessageFromResultSet(ResultSet rs) throws SQLException {
        Message message = new Message();
        message.setId(rs.getInt("id"));
        message.setUserId(rs.getInt("user_id"));
        message.setMessage(rs.getString("message"));
        message.setRead(rs.getBoolean("isRead"));
        message.setMessageDate(rs.getTimestamp("message_date"));
        return message;
    }
}
