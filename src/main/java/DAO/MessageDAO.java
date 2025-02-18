package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    /**
     * Updates the text of a message by its ID in the database.
     * @param message_id The unique ID of the message to be updated.
     * @param message_text The new text content of the message.
     * @return True if the update was successful, false otherwise.
     */
    public Boolean updateMessageById(int message_id, String message_text) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, message_text);
            pstmt.setInt(2, message_id);
            return pstmt.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Deletes a message from the database using its ID.
     * @param message_id The unique ID of the message to be deleted.
     */
    public void deleteMessageById(int message_id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM Message WHERE message_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, message_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Retrieves a message from the database by its ID.
     * @param message_id The unique ID of the message to be retrieved.
     * @return The Message object if found, otherwise null.
     */
    public Message getMessageById(int message_id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, message_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves all messages posted by a specific user.
     * @param account_id The unique ID of the account whose messages are to be retrieved.
     * @return A list of Message objects posted by the user.
     */
    public List<Message> getAllMessagesSentByParticularUser(int account_id) {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Message WHERE posted_by = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, account_id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
     * Retrieves all messages stored in the database.
     * @return A list of all Message objects in the database.
     */
    public List<Message> getAllMessages() {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Message";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /** 
     * Inserts a new message into the database.
     * @param message The Message object to be inserted.
     * @return The inserted Message object with the generated ID, or null if insertion fails.
     */
    public Message insertMessage(Message message) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, message.getPosted_by());
            pstmt.setString(2, message.getMessage_text());
            pstmt.setLong(3, message.getTime_posted_epoch());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                message.setMessage_id(rs.getInt(1));
                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Checks if an account exists in the database by its ID.
     * @param id The unique ID of the account.
     * @return True if the account exists, false otherwise.
     */
    public Boolean findAccountById(int id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Account WHERE account_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
